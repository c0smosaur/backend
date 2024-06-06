package com.core.linkup.club.club.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ClubMeetingResponse (
    Long id,
    String title,
    LocalDateTime date,
    String meetingLocation,
    Integer maxCapacity,
    Integer fee,
    String memberName,
    String memberImage

) {
}
