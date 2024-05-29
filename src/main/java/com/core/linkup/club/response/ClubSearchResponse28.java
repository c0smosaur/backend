package com.core.linkup.club.response;

import lombok.Builder;

@Builder
public record ClubSearchResponse28(
        Long id,
        Long memberId,
        String title,
        String introduction,
        String clubType,
        Integer recruitCount

        // clubmemeber,
        // clubmeeting
) {
}
