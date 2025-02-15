package com.core.linkup.club.clubnotice.controller;

import com.core.linkup.club.clubnotice.request.ClubCommentRequest;
import com.core.linkup.club.clubnotice.response.ClubCommentResponse;
import com.core.linkup.club.clubnotice.service.ClubCommentService;
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
@Tag(name = "Club - Comment", description = "소모임 댓글 관련 API")
public class ClubCommentController {

    private final ClubCommentService clubCommentService;

    //게시판 댓글 등록
    @PostMapping("/{club_id}/post/{notice_id}/comment")
    public BaseResponse<ClubCommentResponse> createComment(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @PathVariable("club_id") Long clubId,
            @PathVariable("notice_id") Long noticeId,
            @RequestBody ClubCommentRequest request
    ) {
        ClubCommentResponse response =
                clubCommentService.createComment(memberDetails.getMember(), clubId, noticeId, request);
        return BaseResponse.response(response);
    }

    //조회
    @GetMapping("/{club_id}/post/{notice_id}/comment")
    public BaseResponse<List<ClubCommentResponse>> findComment(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @PathVariable("club_id") Long clubId,
            @PathVariable("notice_id") Long noticeId
    ) {
        List<ClubCommentResponse> response =
                clubCommentService.findComments(memberDetails.getMember(), clubId, noticeId);
        return BaseResponse.response(response);
    }

    //삭제
    @DeleteMapping("/{club_id}/board/{notice_id}/comment/{comment_id}")
    public BaseResponse<Void> deleteComment(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @PathVariable("club_id") Long clubId,
            @PathVariable("notice_id") Long noticeId,
            @PathVariable("comment_id") Long commentId
    ) {
        clubCommentService.deleteComment(memberDetails.getMember(), clubId, noticeId, commentId);
        return BaseResponse.response(BaseResponseStatus.DELETE_SUCCESS);
    }
}
