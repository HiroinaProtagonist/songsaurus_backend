package com.hiroinaprotagonist.songsaurus.rest.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hiroinaprotagonist.songsaurus.rest.model.trackObject.TrackObjectDTO;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TracksDTO (
        TracksContentDTO tracks
) {}
