package com.core.linkup.club.club.response;

import com.core.linkup.club.clubmeeting.response.ClubMeetingResponse;
import lombok.Builder;

import java.util.List;

@Builder
public record ClubSearchResponse(
        Long id,
        Long memberId,
        String memberName,
        String profileImage,
        String title,
        String introduction,
        String detailIntroduction,
        Boolean clubAccessibility,
        String clubThumbnail,
        String clubType,
        Integer recruitCount,
        List<ClubMemberResponse> clubMembers,
        List<ClubMeetingResponse> clubMeetings,
        Boolean liked

) {
}
