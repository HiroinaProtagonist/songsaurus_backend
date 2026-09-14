package com.hiroinaprotagonist.songsaurus.rest.model.web.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Cloud {
    private CloudWord[] words;
}
