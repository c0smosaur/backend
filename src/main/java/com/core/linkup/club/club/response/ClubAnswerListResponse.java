package com.core.linkup.club.club.response;

import lombok.Builder;

import java.util.List;

@Builder
public record ClubAnswerListResponse(
        Long clubId,
        String clubTitle,
        String clubIntroduction,
        String clubDetailIntroduction,
        List<String> answer,
        Integer qorders
) {
}
