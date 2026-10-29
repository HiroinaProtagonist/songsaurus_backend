package com.hiroinaprotagonist.songsaurus.rest.controller;

import com.hiroinaprotagonist.songsaurus.rest.model.web.request.Cloud;
import com.hiroinaprotagonist.songsaurus.rest.model.web.request.CloudWord;
import com.hiroinaprotagonist.songsaurus.rest.model.web.response.Tracks;
import com.hiroinaprotagonist.songsaurus.rest.model.web.response.TracksDTO;
import com.hiroinaprotagonist.songsaurus.rest.service.SpService;
import com.hiroinaprotagonist.songsaurus.rest.service.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tools.jackson.databind.ObjectMapper;

import java.util.*;
import java.util.concurrent.*;

@RestController
@RequestMapping("/api/songsaurus")
@RequiredArgsConstructor
@Slf4j
public class SpController {
    private final SpService spService;
    private final TokenService tokenService;

    private static final String UNAUTHORIZED = "Unauthorized";
    private static String token = "";

    ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/single/{limit}/{cloudWord}/")
    public ResponseEntity<Tracks> getSongsByWord(@PathVariable String limit,
                                                    @PathVariable CloudWord cloudWord) {
        String word = cloudWord.getText();
        log.info("Get tracks from Spotify api for word: {}", word);
        TracksDTO tracks = null;
        Tracks output = new Tracks();

        try {
            tracks = spService.getSongsByWord(word, limit, token);
            output = new Tracks(tracks, word);
            if(Objects.nonNull(tracks)) { log.info(tracks.toString()); }
            if(Objects.nonNull(tracks.tracks())) { log.info(Arrays.toString(tracks.tracks().items())); }

        } catch (Exception e) {
            log.info("Error occurred: {}, {}", e.getMessage(), e.getClass().getSimpleName());

            if(UNAUTHORIZED.equals(e.getClass().getSimpleName())) {
                token = tokenService.getClientCredToken().access_token();                  //encrypt this

                log.info("Retrieving with new token");
//                log.info("Retrieving with new token, {}", token);
                tracks = spService.getSongsByWord(word, limit, token);   //decrypt token to use
                output = new Tracks(tracks, word);
            }
        }

        return ResponseEntity.ok(output);
    }

    @GetMapping("/multi/{limit}/{input}")
    public ResponseEntity<List<Tracks>> getSongsByWords(@PathVariable String limit,
                                                           @PathVariable String... input)
            throws ExecutionException, InterruptedException {
        log.info("Input: {}", (Object[]) input);

        Cloud cloud = new Cloud(
                Arrays.stream(input)
                        .sequential()
                        .map(w -> new CloudWord(w, 0))
                        .toArray(CloudWord[]::new));
        CloudWord[] cloudWords = cloud.getWords();

        String[] words = Arrays.stream(cloudWords)
                .sequential()
                .map(CloudWord::getText)
                .toArray(String[]::new);

        log.info("Get tracks from Spotify api for cloud");
        CloudWord word = cloudWords[0];
        List<Tracks> result = new LinkedList<>();

        int limitValue = 10;
        try {
            limitValue = Integer.parseInt(limit);
        } catch (Exception e) {
            log.info("Invalid limit value, using default value {}", limitValue);
        }

        log.info("Search Spotify for {} songs related to {}", limit, Arrays.toString(words));

        ExecutorService tokenExecutor = Executors.newSingleThreadExecutor();
        Callable<ResponseEntity<Tracks>> tokenTask = () -> getSongsByWord(limit, word);
        List<Callable<ResponseEntity<Tracks>>> tokenTaskSingleton
                = Collections.singletonList(tokenTask);
        Tracks firstRes = tokenExecutor.invokeAny(tokenTaskSingleton).getBody();

        result.add(firstRes);

        ExecutorService pool = Executors.newFixedThreadPool(limitValue);
        List<Callable<ResponseEntity<Tracks>>> wordsTasks = Arrays.stream(cloudWords)
                .sequential()
                .skip(1)
                .map(w -> (Callable<ResponseEntity<Tracks>>) () -> getSongsByWord(limit, w))
                .toList();

        List<Future<ResponseEntity<Tracks>>> wordsTasksResults = pool.invokeAll(wordsTasks);

        wordsTasksResults.forEach(r -> {
            try {

                //TODO check perf vs this & doing get after all have finished
                result.add(r.get().getBody());
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        });

        log.info("Result as Tracks: {}", objectMapper.writeValueAsString(result));
        return ResponseEntity.ok(result);
    }

}
