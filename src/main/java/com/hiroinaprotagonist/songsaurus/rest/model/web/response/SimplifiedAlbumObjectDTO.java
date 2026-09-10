package com.hiroinaprotagonist.songsaurus.rest.model.web.response;

import com.hiroinaprotagonist.songsaurus.rest.model.web.response.trackObject.ImageObjectDTO;
import com.hiroinaprotagonist.songsaurus.rest.model.web.response.trackObject.SimplifiedArtistObjectDTO;

public record SimplifiedAlbumObjectDTO(
        String album_type,
        Integer total_tracks,
        String[] available_markets,
        String href,
        String id,
        ImageObjectDTO[] images,
        String name,
        String release_date,
        String release_date_precision,
        SimplifiedArtistObjectDTO[] artists
) {}
