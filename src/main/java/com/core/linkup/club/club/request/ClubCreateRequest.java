package com.core.linkup.club.club.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;

@Builder
public record ClubCreateRequest(
        String title,
        String introduction,
        @JsonProperty("category")
        String clubType,
        Integer recruitCount,
        Boolean clubAccessibility,
        String detailedIntroduction,
        String clubThumbnail,
        String applicationIntroduction,
        List<ClubQuestionRequest> clubQuestions
) {
}
