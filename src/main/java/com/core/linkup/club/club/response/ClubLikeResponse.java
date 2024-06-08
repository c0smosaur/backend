package com.core.linkup.club.club.response;

import lombok.Builder;

@Builder
public record ClubLikeResponse (
    Long id,
    Long clubId,
    Long memberId
) {
}
