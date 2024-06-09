package com.core.linkup.club.clubmeeting.converter;

import com.core.linkup.club.club.entity.Club;
import com.core.linkup.club.clubmeeting.response.ClubMeetingResponse;
import com.core.linkup.club.clubmeeting.entity.ClubMeeting;
import com.core.linkup.club.clubmeeting.request.ClubMeetingRequest;
import com.core.linkup.common.annotation.Converter;
import com.core.linkup.member.entity.Member;

@Converter
public class ClubMeetingConverter {

    public ClubMeeting toMeetingEntity(ClubMeetingRequest request, Club club) {
        return ClubMeeting.builder()
                .title(request.title())
                .date(request.date()) //정기모임 시간
                .meetingLocation(request.meetingLocation())
                .maxCapacity(request.maxCapacity())
                .fee(request.fee())
                .clubId(club.getId())
                .build();
    }

    public ClubMeetingResponse toMeetingResponse(ClubMeeting clubMeeting, Member member) {
        return ClubMeetingResponse.builder()
                .id(clubMeeting.getId())
                .title(clubMeeting.getTitle())
                .date(clubMeeting.getDate())
                .meetingLocation(clubMeeting.getMeetingLocation())
                .maxCapacity(clubMeeting.getMaxCapacity())
                .fee(clubMeeting.getFee())
                .memberName(member.getName())
                .memberImage(member.getProfileImage())
                .build();
    }
    public ClubMeeting toUpdateMeetingEntity(ClubMeeting clubMeeting, ClubMeetingRequest request) {
        return ClubMeeting.builder()
                .title(request.title())
                .date(request.date()) //정기 모임 시간
                .meetingLocation(request.meetingLocation())
                .maxCapacity(request.maxCapacity())
                .fee(request.fee())
//                .clubId(clubMeeting.getClubId())
//                .memberId(clubMeeting.getMemberId())
                .build();
    }

}
