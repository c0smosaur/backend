package com.core.linkup.club.clubmeeting.controller;

import com.core.linkup.club.clubmeeting.request.ClubMeetingRequest;
import com.core.linkup.club.clubmeeting.response.ClubMeetingResponse;
import com.core.linkup.club.clubmeeting.service.ClubMeetingService;
import com.core.linkup.common.response.BaseResponse;
import com.core.linkup.common.response.BaseResponseStatus;
import com.core.linkup.security.MemberDetails;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/club")
@Tag(name = "Club", description = "소모임 정모 관련 API")
public class ClubMeetingController {

    private final ClubMeetingService clubMeetingService;

    // 정기모임 등록
    @PostMapping("/{club_id}/meeting")
    public BaseResponse<ClubMeetingResponse> createMeeting(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @PathVariable("club_id") Long clubId,
            @RequestBody ClubMeetingRequest request
    ) {
        ClubMeetingResponse response = clubMeetingService.createMeeting(memberDetails, clubId, request);
        return BaseResponse.response(response);
    }

    // 정기모임 조회
    @GetMapping("/{club_id}/meeting")
    public BaseResponse<List<ClubMeetingResponse>> findAllMeetings(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @PathVariable("club_id") Long clubId
    ) {
        List<ClubMeetingResponse> response = clubMeetingService.findAllMeetings(memberDetails, clubId);
        return BaseResponse.response(response);
    }

    @GetMapping("/{club_id}/meeting/{meeting_id}")
    public BaseResponse<ClubMeetingResponse> findMeeting(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @PathVariable("club_id") Long clubId,
            @PathVariable("meeting_id") Long meetingId
    ) {
        ClubMeetingResponse response = clubMeetingService.findMeeting(memberDetails, clubId, meetingId);
        return BaseResponse.response(response);
    }

    //정기모임 수정
    @PutMapping("/{club_id}/meeting/{meeting_id}")
    public BaseResponse<ClubMeetingResponse> updateMeeting(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @PathVariable("club_id") Long clubId,
            @PathVariable("meeting_id") Long meetingId,
            @RequestBody ClubMeetingRequest request
    ) {
        ClubMeetingResponse response = clubMeetingService.updateMeeting(memberDetails, clubId, meetingId, request);
        return BaseResponse.response(response);
    }

    @DeleteMapping("/{club_id}/meeting/{meeting_id}")
    public BaseResponse<Void> deleteMeeting(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @PathVariable("club_id") Long clubId,
            @PathVariable("meeting_id") Long meetingId
    ) {
        clubMeetingService.deleteMeeting(memberDetails, clubId, meetingId);
        return BaseResponse.response(BaseResponseStatus.DELETE_SUCCESS);
    }
}
