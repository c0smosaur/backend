package com.core.linkup.club.club.response;

import lombok.Builder;

import java.util.List;

@Builder
public record ClubApplicationResponse (
        Long id, //clubMemberId
        Long clubId,
        Long memberId,
        String introduction,
        String clubThumbnail,
        Boolean approval,
        List<ClubAnswerResponse> clubAnswer

) {
}
