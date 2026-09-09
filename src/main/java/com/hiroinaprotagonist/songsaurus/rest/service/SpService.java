package com.hiroinaprotagonist.songsaurus.rest.service;

import com.hiroinaprotagonist.songsaurus.rest.model.Pokemon;
import com.hiroinaprotagonist.songsaurus.rest.model.TracksDTO;
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

    @Value("${sp.refreshToken}")
    private String refreshToken;

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

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", bearerToken);

        String query = String.format("%s?q=%s&type=track&limit=%d",
                spotifyWebApiSearchUrl, word, limitValue);

        //        HttpEntity<String> = new HttpEntity<String>(headers);

        return callSpotifySearch(query);
    }



    @Cacheable(value = "songsByWords", key="#words")
    public List<TracksDTO> getSongsByWords(@RequestParam String... words) {
        //separate thread for each call

        //call api for each word
        return null;
    }

//    private List<TracksDTO> callSpotifySearch(HttpEntity<String> entity,
//                                              String query) {
//        try {
//            ResponseEntity<List<TracksDTO>> response = RestTemplate.exchange(
//                    spotifyWebApiSearchUrl,
//                    HttpMethod.GET,
//                    entity,
//                    new ParameterizedTypeReference<List<TracksDTO>>() {}
//            );
//
//            if (Objects.nonNull(response.getBody())) {
//                log.info(response.getBody().toString());
//            }
//
//            return response.getBody();
//
//        } catch (RestClientException e) {
//            log.info("Exception in search: {}", query);
//            return new ArrayList<>();
//        }
//    }

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

    @Cacheable(value = "songsByWord", key="#word")
    public Pokemon getPokemon(@RequestParam String pokemon) {

        log.info("Search Pokemon api for {}", pokemon);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", bearerToken);

        String query = "https://pokeapi.co/api/v2/pokemon/ditto";

        return callPokemon(query);
    }

    private Pokemon callPokemon(String query) {
        ResponseEntity<Pokemon> response = restClient.get()
                .uri(query)
//                .headers(headers -> {
//                    headers.setBearerAuth(bearerToken);
//                })
                .retrieve()
                .toEntity(new ParameterizedTypeReference<Pokemon>() {});

        log.info("Response: {}", response);

        return response.getBody();
    }

}
