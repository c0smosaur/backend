package com.core.linkup.club.clubmeeting.request;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ClubMeetingRequest(
        String title,
        LocalDateTime date,
        String meetingLocation,
        Integer maxCapacity,
        Integer fee
) {
}
