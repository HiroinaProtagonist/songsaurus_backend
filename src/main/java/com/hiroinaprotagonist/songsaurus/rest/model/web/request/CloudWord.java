package com.hiroinaprotagonist.songsaurus.rest.model.web.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CloudWord {
    private String text;
    private int value;
}
