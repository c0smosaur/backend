package com.core.linkup.club.clubmeeting.controller;

import com.core.linkup.club.club.response.ClubMeetingResponse;
import com.core.linkup.club.clubmeeting.request.ClubMeetingRequest;
import com.core.linkup.club.clubmeeting.service.ClubMeetingService;
import com.core.linkup.common.response.BaseResponse;
import com.core.linkup.security.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/club")
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

}
