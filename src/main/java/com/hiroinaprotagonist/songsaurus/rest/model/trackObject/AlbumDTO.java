package com.hiroinaprotagonist.songsaurus.rest.model.trackObject;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AlbumDTO(
        String album_type,
        Integer total_tracks,
        String[] available_markets,
        ExternalUrlsDTO external_urls,

        String href,
        String id,
        ImageObjectDTO[] images,
        String name,
        String release_date,
        String release_date_precision,
        RestrictionDTO restrictions,
        String type,
        String uri,
        SimplifiedArtistObjectDTO[] artists
) {}
