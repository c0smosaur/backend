package com.core.linkup.club.requset;

import com.core.linkup.club.entity.ClubQuestion;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;

@Builder
public record ClubCreateRequest28(
         String title,
         String introduction,
         @JsonProperty("category")
         String clubType,
         Integer recruitCount,
         Boolean clubAccessibility,
         String detailedIntroduction,
         String clubThumbnail,
         String applicationIntroduction,
         List<ClubQuestion> clubQuestions
) {
}
