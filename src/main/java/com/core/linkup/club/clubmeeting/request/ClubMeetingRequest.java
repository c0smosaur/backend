package com.core.linkup.club.clubmeeting.request;

import java.time.LocalDateTime;

public record ClubMeetingRequest(
        String title,
        LocalDateTime date,
        String meetingLocation,
        Integer maxCapacity,
        Integer fee
) {
}
