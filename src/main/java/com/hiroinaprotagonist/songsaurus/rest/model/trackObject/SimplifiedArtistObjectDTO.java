package com.hiroinaprotagonist.songsaurus.rest.model.trackObject;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SimplifiedArtistObjectDTO(
        ExternalUrlsDTO external_urls,
        String href,
        String id,
        String name,
        String type,
        String uri
) {}
