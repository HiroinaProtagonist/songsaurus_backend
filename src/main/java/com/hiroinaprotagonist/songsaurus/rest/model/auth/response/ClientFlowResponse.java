package com.hiroinaprotagonist.songsaurus.rest.model.auth.response;

public record ClientFlowResponse(
        String access_token,
        String token_type,
        Long expires_in
) {}
