package com.core.linkup.club.clubnotice.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NotificationType {
    NOTICE("공지"),
    BOARD("게시판");

    private final String notification;

    public static NotificationType fromNotification(String koreanName) {
        for (NotificationType notificationType : NotificationType.values()) {
            if (notificationType.getNotification().equals(koreanName)) {
                return notificationType;
            }
        }
        throw new IllegalArgumentException("No enum constant for Korean name: " + koreanName);
    }
}
