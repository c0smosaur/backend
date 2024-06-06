package com.core.linkup.club.club.response;

import lombok.Builder;

@Builder
public record ClubMemberResponse (
        Long clubId,
        Long memberId,
        String memberName,
        String profileImage,
        String introduction
) {
}
