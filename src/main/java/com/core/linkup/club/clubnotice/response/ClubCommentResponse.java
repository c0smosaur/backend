package com.core.linkup.club.clubnotice.response;

import com.core.linkup.common.entity.enums.OccupationType;
import lombok.Builder;

@Builder
public record ClubCommentResponse(

        Long commentId,
        String comment,
        Long clubNoticeId,
        String clubMemberId,
        String clubMemberName,
        String clubMemberThumbnail,
        OccupationType clubMemberOccupation

) {
}