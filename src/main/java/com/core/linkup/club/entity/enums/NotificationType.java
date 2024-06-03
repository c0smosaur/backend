package com.core.linkup.club.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NotificationType {
    NOTICE("공지"),
    MEETING("정기모임");

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
