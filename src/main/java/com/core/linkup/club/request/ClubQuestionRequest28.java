package com.core.linkup.club.request;

import lombok.Builder;

@Builder
public record ClubQuestionRequest28(
        String question,
        Integer orders
) {
}
