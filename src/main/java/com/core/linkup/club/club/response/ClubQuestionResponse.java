package com.core.linkup.club.club.response;

import lombok.Builder;

import java.util.List;

@Builder
public record ClubQuestionResponse(
        Long clubId,
        String clubTitle,
        String clubIntroduction,
        String clubDetailIntroduction,
        List<String> question
) {
}
