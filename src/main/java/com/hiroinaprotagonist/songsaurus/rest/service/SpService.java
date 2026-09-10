package com.hiroinaprotagonist.songsaurus.rest.service;

import com.hiroinaprotagonist.songsaurus.rest.model.pokemon.Pokemon;
import com.hiroinaprotagonist.songsaurus.rest.model.web.response.TracksDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClient;

import java.util.*;

@Service
@Slf4j
public class SpService {
    @Autowired
    private RestClient restClient;

    @Value("${sp.urls.search}")
    private String spotifyWebApiSearchUrl;

    @Value("${sp.clientId}")
    private String clientId;

    @Value("${sp.bearerToken}")
    private String bearerToken;

    private static String currentBearerToken;

    @Value("${sp.refreshToken}")
    private String refreshToken;

    @Cacheable(value = "songsByWord", key="#word")
    public TracksDTO getSongsByWord(@RequestParam String word,
                                    @RequestParam String limit,
                                    String token) {
        int limitValue = 10;

        try {
            limitValue = Integer.parseInt(limit);
        } catch (Exception e) {
            log.info("Invalid limit value, using default value {}", limitValue);
        }

        log.info("Search Spotify for {} songs related to {}", limit, word);

        String query = String.format("%s?q=%s&type=track&limit=%d",
                spotifyWebApiSearchUrl, word, limitValue);

        return callSpotifySearch(query, token);
    }

    @Cacheable(value = "songsByWord", key="#word")
    public TracksDTO getSongsByWord(@RequestParam String word,
                                    @RequestParam String limit) {
        int limitValue = 10;

        try {
            limitValue = Integer.parseInt(limit);
        } catch (Exception e) {
            log.info("Invalid limit value, using default value {}", limitValue);
        }

        log.info("Search Spotify for {} songs related to {}", limit, word);

//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", bearerToken);

        String query = String.format("%s?q=%s&type=track&limit=%d",
                spotifyWebApiSearchUrl, word, limitValue);

        //        HttpEntity<String> = new HttpEntity<String>(headers);

        return callSpotifySearch(query);
    }

    @Cacheable(value = "songsByWords", key="#words")
    public List<TracksDTO> getSongsByWords(@RequestParam String... words) {
        if(words.length < 1) {
            log.info("Input too short: {}", Arrays.toString(words));
            return new LinkedList<>();
        }




        //call api for each word
        return null;
    }

    private TracksDTO callSpotifySearch(String query) {
        ResponseEntity<TracksDTO> response = restClient.get()
                .uri(query)
                .headers(headers -> {
                    headers.setBearerAuth(bearerToken);
                })
                .retrieve()
                .toEntity(new ParameterizedTypeReference<TracksDTO>() {});

        log.info("Response: {}", response);

        return response.getBody();
    }

    private TracksDTO callSpotifySearch(String query, String token) {
        ResponseEntity<TracksDTO> response = restClient.get()
                .uri(query)
                .headers(headers -> {
                    headers.setBearerAuth(token);
                })
                .retrieve()
                .toEntity(new ParameterizedTypeReference<TracksDTO>() {});

        log.info("Response: {}", response);

        return response.getBody();
    }
}
