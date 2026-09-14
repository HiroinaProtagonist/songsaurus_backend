package com.hiroinaprotagonist.songsaurus.rest.model.web.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hiroinaprotagonist.songsaurus.rest.model.web.response.trackObject.TrackObjectDTO;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TracksContentDTO(String href,
                               Integer limit,
                               String next,
                               Integer offset,
                               String previous,
                               Integer total,
                               TrackObjectDTO[] items
) {}
