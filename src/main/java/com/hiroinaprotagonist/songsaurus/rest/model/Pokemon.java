package com.hiroinaprotagonist.songsaurus.rest.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Pokemon(
        int id,
        String name,
        int height
) {
}
