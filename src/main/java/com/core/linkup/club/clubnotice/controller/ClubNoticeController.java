package com.core.linkup.club.clubnotice.controller;

import com.core.linkup.club.clubnotice.request.ClubNoticeRequest;
import com.core.linkup.club.clubnotice.response.ClubNoticeResponse;
import com.core.linkup.club.clubnotice.service.ClubNoticeService;
import com.core.linkup.common.response.BaseResponse;
import com.core.linkup.security.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/club")
public class ClubNoticeController {

    private final ClubNoticeService clubNoticeService;

    //소모임 정모 글 등록
    @PostMapping("/{club_id}/notice")
    public BaseResponse<ClubNoticeResponse> createMeeting(
            @AuthenticationPrincipal MemberDetails member,
            @PathVariable("club_id") Long clubId,
            @RequestBody ClubNoticeRequest request
    ){
        ClubNoticeResponse response = clubNoticeService.createMeeting(member, clubId, request);
        return BaseResponse.response(response);

    }
}
