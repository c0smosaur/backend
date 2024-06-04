package com.core.linkup.club.clubnotice.controller;

import com.core.linkup.club.clubnotice.request.ClubBoardRequest;
import com.core.linkup.club.clubnotice.request.ClubNoticeRequest;
import com.core.linkup.club.clubnotice.response.ClubBoardResponse;
import com.core.linkup.club.clubnotice.response.ClubNoticeResponse;
import com.core.linkup.club.clubnotice.service.ClubBoardService;
import com.core.linkup.common.response.BaseResponse;
import com.core.linkup.common.response.BaseResponseStatus;
import com.core.linkup.security.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/club")
public class ClubBoardController {

    private final ClubBoardService clubBoardService;

    //게시판 등록
    @PostMapping("/{club_id}/board")
    public BaseResponse<ClubBoardResponse> createBoard(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @PathVariable("club_id") Long clubId,
            @RequestBody ClubBoardRequest request
    ) {
        ClubBoardResponse response = clubBoardService.createBoard(memberDetails, clubId, request);
        return BaseResponse.response(response);
    }

    //게시판 조회
    @GetMapping("/{club_id}/board")
    public BaseResponse<List<ClubBoardResponse>> findAllNotice(
            @AuthenticationPrincipal MemberDetails member,
            @PathVariable("club_id") Long clubId
    ) {
        List<ClubBoardResponse> response = clubBoardService.findAllBoard(clubId, member);
        return BaseResponse.response(response);
    }
    @GetMapping("/{club_id}/board/{notice_id}")
    public BaseResponse<ClubBoardResponse> findNotice(
            @AuthenticationPrincipal MemberDetails member,
            @PathVariable("club_id") Long clubId,
            @PathVariable("notice_id") Long noticeId
    ) {
        ClubBoardResponse response = clubBoardService.findBoard(clubId, noticeId, member);
        return BaseResponse.response(response);
    }

    //api/v1/club/{club_id}/notice/{notice_id}
    //공지사항 수정
    @PutMapping("/{club_id}/board/{notice_id}")
    public BaseResponse<ClubBoardResponse> updateNotice(
            @AuthenticationPrincipal MemberDetails member,
            @PathVariable("club_id") Long clubId,
            @PathVariable("notice_id") Long noticeId,
            @RequestBody ClubBoardRequest request
    ) {
        ClubBoardResponse updateNotice = clubBoardService.updateBoard(member, clubId, noticeId, request);
        return BaseResponse.response(updateNotice);
    }

    //공지사항 삭제
    @DeleteMapping("/{club_id}/board/{notice_id}")
    public BaseResponse<String> updateNotice(
            @AuthenticationPrincipal MemberDetails member,
            @PathVariable("club_id") Long clubId,
            @PathVariable("notice_id") Long noticeId
    ) {
        clubBoardService.deleteBoard(member, clubId, noticeId);
        return BaseResponse.response(BaseResponseStatus.DELETE_SUCCESS);
    }
}
