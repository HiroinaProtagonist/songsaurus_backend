package com.hiroinaprotagonist.songsaurus.rest.model.web.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tracks {
    TracksDTO tracks;
    String word;
}
