package com.core.linkup.club.clubnotice.controller;

import com.core.linkup.club.clubnotice.request.ClubCommentRequest;
import com.core.linkup.club.clubnotice.response.ClubCommentResponse;
import com.core.linkup.club.clubnotice.service.ClubCommentService;
import com.core.linkup.common.response.BaseResponse;
import com.core.linkup.security.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/club")
public class ClubCommentController {

    private final ClubCommentService clubCommentService;

    //게시판 댓글 등록
    @PostMapping("/{club_id}/board/{notice_id}/comment")
    public BaseResponse<ClubCommentResponse> createBoard(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @PathVariable("club_id") Long clubId,
            @PathVariable("notice_id") Long noticeId,
            @RequestBody ClubCommentRequest request
    ) {
        ClubCommentResponse response = clubCommentService.createComment(memberDetails, clubId, noticeId, request);
        return BaseResponse.response(response);
    }

}
