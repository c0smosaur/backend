package com.core.linkup.club.clubnotice.response;

import com.core.linkup.club.clubnotice.entity.enums.NotificationType;
import com.core.linkup.common.entity.enums.OccupationType;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ClubBoardResponse (
        Long id,
        Long noticeId,
        String title,
        String content,
        NotificationType type,
        Long clubMemberId,
        String clubMemberName,
        String clubMemberThumbnail,
        OccupationType clubMemberOccupation,
        LocalDateTime date
        //List<ClubComment> comment
){
}
