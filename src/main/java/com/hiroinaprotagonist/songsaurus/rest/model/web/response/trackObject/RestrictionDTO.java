package com.hiroinaprotagonist.songsaurus.rest.model.web.response.trackObject;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RestrictionDTO(
        String reason
) {}
