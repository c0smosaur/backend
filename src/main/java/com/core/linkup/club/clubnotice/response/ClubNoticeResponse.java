package com.core.linkup.club.clubnotice.response;

import com.core.linkup.club.club.entity.enums.NotificationType;
import com.core.linkup.common.entity.enums.OccupationType;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ClubNoticeResponse(
        Long noticeId,
        String title,
        String content,
        NotificationType type,
        Long clubOwnerId,
        String clubOwnerName,
        String clubOwnerThumbnail,
        OccupationType clubOwnerOccupation,
        LocalDateTime date

) {
}
