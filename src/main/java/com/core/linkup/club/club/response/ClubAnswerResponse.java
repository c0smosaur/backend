package com.core.linkup.club.club.response;

import lombok.Builder;

@Builder
public record ClubAnswerResponse(
        Long id,
        String answer,
        Integer qorders
) {
}
