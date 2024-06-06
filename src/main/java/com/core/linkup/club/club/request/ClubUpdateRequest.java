package com.core.linkup.club.club.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record ClubUpdateRequest (
        String title,
        String introduction,
        @JsonProperty("category")
        String clubType,
        Integer recruitCount,
        Boolean clubAccessibility,
        String detailedIntroduction,
        String clubThumbnail,
        String applicationIntroduction
) {
}
