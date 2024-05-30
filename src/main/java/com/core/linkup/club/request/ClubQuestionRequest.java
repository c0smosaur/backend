package com.core.linkup.club.request;

import lombok.Builder;

@Builder
public record ClubQuestionRequest(
        String question,
        Integer orders
) {
}
