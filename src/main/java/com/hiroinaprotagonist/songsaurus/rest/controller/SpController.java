package com.hiroinaprotagonist.songsaurus.rest.controller;

import com.hiroinaprotagonist.songsaurus.rest.model.web.response.TracksDTO;
import com.hiroinaprotagonist.songsaurus.rest.service.SpService;
import com.hiroinaprotagonist.songsaurus.rest.service.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/songsaurus")
@RequiredArgsConstructor
@Slf4j
public class SpController {
    private final SpService spService;
    private final TokenService tokenService;

    private static final String UNAUTHORIZED = "Unauthorized";
    private static String token = "";

    @GetMapping("/{limit}/{word}/")
    public ResponseEntity<TracksDTO> getSongsByWord(@PathVariable String limit,
                                                    @PathVariable String word) {
        log.info("Get tracks from Spotify api for word: {}", word);

        TracksDTO tracks = null;
//        String token = null;

        try {
            tracks = spService.getSongsByWord(word, limit);
            if(Objects.nonNull(tracks)) { log.info(tracks.toString()); }
            if(Objects.nonNull(tracks.tracks())) { log.info(Arrays.toString(tracks.tracks().items())); }

        } catch (Exception e) {
            log.info("Error occurred: {}, {}", e.getMessage(), e.getClass().getSimpleName());

            if(UNAUTHORIZED.equals(e.getClass().getSimpleName())) {
                token = tokenService.getClientCredToken().access_token();                  //encrypt this

                log.info("Retrieving with new token");
//                log.info("Retrieving with new token, {}", token);
                tracks = spService.getSongsByWord(word, limit, token);   //decrypt token to use
            }
        }

        return ResponseEntity.ok(tracks);
    }

    @GetMapping("/{limit}/{words}")
    public ResponseEntity<List<TracksDTO>> getSongsByWords(@PathVariable String limit,
                                                           @PathVariable String... words)
            throws ExecutionException, InterruptedException {
        log.info("Get tracks from Spotify api for words: {}", Arrays.toString(words));

        String word = words[0];
        int limitValue = 10;
        List<TracksDTO> result = new LinkedList<>();

        try {
            limitValue = Integer.parseInt(limit);
        } catch (Exception e) {
            log.info("Invalid limit value, using default value {}", limitValue);
        }

        log.info("Search Spotify for {} songs related to {}", limit, Arrays.toString(words));

        ExecutorService tokenExecutor = Executors.newSingleThreadExecutor();
        Callable<ResponseEntity<TracksDTO>> tokenTask = () -> getSongsByWord(limit, word);
        List<Callable<ResponseEntity<TracksDTO>>> tokenTaskSingleton
                = Collections.singletonList(tokenTask);
        TracksDTO firstRes = tokenExecutor.invokeAny(tokenTaskSingleton).getBody();

        result.add(firstRes);

        ExecutorService pool = Executors.newFixedThreadPool(limitValue);
        List<Callable<ResponseEntity<TracksDTO>>> wordsTasks = Arrays.stream(words).skip(1)
                .map(w -> (Callable<ResponseEntity<TracksDTO>>) () -> getSongsByWord(limit, w))
                .toList();

        List<Future<ResponseEntity<TracksDTO>>> wordsTasksResults = pool.invokeAll(wordsTasks);

        wordsTasksResults.stream().forEach(r -> {
            try {
                result.add(r.get().getBody());
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        });

        log.info("Result-0: {}", result.get(0).tracks());
        log.info("Result-1: {}", result.get(1).tracks());
        log.info("Result-2: {}", result.get(2).tracks());
        return ResponseEntity.ok(result);
    }

}
