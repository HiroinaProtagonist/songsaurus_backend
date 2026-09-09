package com.hiroinaprotagonist.songsaurus.rest.model.trackObject;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ExternalUrlsDTO(
        String spotify      //id
) {}
