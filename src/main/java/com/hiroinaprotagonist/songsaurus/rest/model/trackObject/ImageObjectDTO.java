package com.hiroinaprotagonist.songsaurus.rest.model.trackObject;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ImageObjectDTO(
        String url,
        Integer height,
        Integer width
) {}
