package com.hiroinaprotagonist.songsaurus.rest.service;

import com.hiroinaprotagonist.songsaurus.rest.model.auth.response.ClientFlowResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.nio.charset.StandardCharsets;
import java.util.Base64;


@Service
@Slf4j
public class TokenService {
    @Autowired
    private RestClient restClient;

    @Value("${sp.urls.clientCred}")
    private String spotifyClientCredentialUrl;

    @Value("${sp.clientId}")
    private String clientId;

    @Value("${sp.client}")
    private String client;

    //@Cacheable(value = "clientCredToken", key="#clientId")
    public ClientFlowResponse getClientCredToken() {
        MultiValueMap<String, String> bodyForm = new LinkedMultiValueMap<>();
        bodyForm.add("grant_type", "client_credentials");

        ResponseEntity<ClientFlowResponse> response = restClient.post()
                .uri(spotifyClientCredentialUrl)
                .headers(headers -> {
                    headers.set("Authorization", this.getClientCredAuth());
                })
                .body(bodyForm)
                .retrieve()
                .toEntity(new ParameterizedTypeReference<ClientFlowResponse>() {});

        log.info("Request sent, received response code: {}", response);

        //update saved data location with the token from the response

        return response.getBody();
    }

    private String getClientCredAuth() {
        String auth = "Basic " + Base64.getEncoder()
                .encodeToString((clientId + ":" + client).getBytes(StandardCharsets.UTF_8));

        log.info("Got auth: {}",auth);
        return auth;
    }

}
