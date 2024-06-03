package com.core.linkup.club.clubnotice.response;

import com.core.linkup.club.entity.enums.NotificationType;
import lombok.Builder;

@Builder
public record ClubNoticeResponse(
        Long noticeId,
        String title,
        String content,
        NotificationType type,
        Long clubOwnerId,
        String clubOwnerName
) {
}
