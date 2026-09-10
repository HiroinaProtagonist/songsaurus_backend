package com.hiroinaprotagonist.songsaurus.rest.model.web.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TracksDTO (
        TracksContentDTO tracks
) {}
