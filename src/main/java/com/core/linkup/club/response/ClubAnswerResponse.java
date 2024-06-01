package com.core.linkup.club.response;

import lombok.Builder;

@Builder
public record ClubAnswerResponse(
        Long id,
        String answer,
        Integer qorders
) {
}
