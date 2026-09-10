package com.hiroinaprotagonist.songsaurus.rest.model.web.response.trackObject;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TrackObjectDTO(
        AlbumDTO album,
        SimplifiedArtistObjectDTO[] artists,
        Integer disc_number,
        Integer duration_ms,
        boolean explicit,
        ExternalIdsDTO external_ids,
        ExternalUrlsDTO external_urls,
        String href,
        String id,
        boolean is_playable,
        RestrictionDTO restrictions,
        String name,
        Integer track_number,
        String type,
        String uri
) {}
