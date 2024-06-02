package com.core.linkup.club.clubnotice.controller;

import com.core.linkup.club.clubnotice.request.ClubNoticeRequest;
import com.core.linkup.club.clubnotice.response.ClubNoticeResponse;
import com.core.linkup.club.clubnotice.service.ClubNoticeService;
import com.core.linkup.common.response.BaseResponse;
import com.core.linkup.security.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    ) {
        ClubNoticeResponse response = clubNoticeService.createMeeting(member, clubId, request);
        return BaseResponse.response(response);

    }

    //api/v1/club/{club_id}/notice
    @GetMapping("/{club_id}/notice")
    public BaseResponse<List<ClubNoticeResponse>> findAllNotice(
//            @AuthenticationPrincipal MemberDetails member,
            @PathVariable("club_id") Long clubId
    ) {
        List<ClubNoticeResponse> response = clubNoticeService.findAllNotice(clubId);
        return BaseResponse.response(response);
    }

    //api/v1/club/{club_id}/notice/{notice_id}
    //공지사항 수정
    @PutMapping("/{club_id}/notice/{notice_id}")
    public BaseResponse<ClubNoticeResponse> updateNotice(
            @AuthenticationPrincipal MemberDetails member,
            @PathVariable("club_id") Long clubId,
            @PathVariable("notice_id") Long noticeId,
            @RequestBody ClubNoticeRequest request
    ) {
        ClubNoticeResponse updateNotice = clubNoticeService.updateNotice(member, clubId, noticeId, request);
        return BaseResponse.response(updateNotice);
    }

    //공지사항 삭제
    @DeleteMapping("/{club_id}/notice/{notice_id}")
    public BaseResponse<String> updateNotice(
            @AuthenticationPrincipal MemberDetails member,
            @PathVariable("club_id") Long clubId,
            @PathVariable("notice_id") Long noticeId
    ) {
        clubNoticeService.deleteNotice(member, clubId, noticeId);
        return BaseResponse.response("Delete Notice");
    }

}
