package com.hiroinaprotagonist.songsaurus.rest.controller;

import com.hiroinaprotagonist.songsaurus.rest.model.Pokemon;
import com.hiroinaprotagonist.songsaurus.rest.model.TracksDTO;
import com.hiroinaprotagonist.songsaurus.rest.service.SpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/songsaurus")
@RequiredArgsConstructor
@Slf4j
public class SpController {
    private final SpService spService;

    @GetMapping("/{word}/{limit}")
    public ResponseEntity<TracksDTO> getSongsByWord(@PathVariable String word,
                                                    @PathVariable String limit) {
        log.info("Get tracks from Spotify api for word: {}", word);

        TracksDTO tracks = spService.getSongsByWord(word, limit);
        if(Objects.nonNull(tracks)) { log.info(tracks.toString()); }
        if(Objects.nonNull(tracks.tracks())) { log.info(Arrays.toString(tracks.tracks().items())); }

        Pokemon pokemon = spService.getPokemon("ditto");
        log.info(pokemon.toString());

        return ResponseEntity.ok(tracks);
    }

    @GetMapping("/{words}")
    public ResponseEntity<List<TracksDTO>> getSongsByWords(@PathVariable String[] words) {
        log.info("Get tracks from Spotify api for words: {}", Arrays.toString(words));

        List<TracksDTO> tracks = spService.getSongsByWords(words);
        if(!tracks.isEmpty()) { log.info(tracks.toString()); }

        return ResponseEntity.ok(tracks);
    }

}
