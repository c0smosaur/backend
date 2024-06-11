package com.core.linkup.club.club.controller;

import com.core.linkup.club.club.request.*;
import com.core.linkup.club.club.response.ClubLikeResponse;
import com.core.linkup.club.club.response.ClubQuestionResponse;
import com.core.linkup.club.club.response.ClubSearchResponse;
import com.core.linkup.club.club.service.ClubService;
import com.core.linkup.common.response.BaseResponse;
import com.core.linkup.common.response.BaseResponseStatus;
import com.core.linkup.security.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/club")
public class ClubController {

    private final ClubService clubService;

    //소모임 개별조회
    @GetMapping("/{clubId}")
    public BaseResponse<ClubSearchResponse> findClub(
            @AuthenticationPrincipal MemberDetails member,
            @PathVariable Long clubId
    ) {
        ClubSearchResponse response = clubService.findClub(clubId, member.getMember());
        return BaseResponse.response(response);
    }

    //소모임 필터링으로 조회 (ClubType으로 조회 가능)
    //TODO : OfficeBuilding으로 조회 가능 하도록 할 예정
    @GetMapping("/search")
    public BaseResponse<Page<ClubSearchResponse>> findClubs(
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
//           @ModelAttribute ClubSearchRequest request
            @RequestParam(name = "category", required = false) String category
    ) {

        // 비로그인
        if (category != null) {
            Page<ClubSearchResponse> searchResponse =
                    clubService.findClubs(pageable, category);
            return BaseResponse.response(searchResponse);
        } else {
            // TODO: category 들어온 게 없을 때
            Page<ClubSearchResponse> searchResponse =
                    clubService.findClubs(pageable, null);
            return BaseResponse.response(searchResponse);
        }
    }

    @GetMapping("/authenticated/search")
    public BaseResponse<Page<ClubSearchResponse>> findClubsAuthenticated(
            @AuthenticationPrincipal MemberDetails member,
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
            @RequestParam(name = "category", required = false) String category) {
        // 로그인
        if (category != null) {
            Page<ClubSearchResponse> searchResponse =
                    clubService.findClubs(member.getMember(), pageable, category);
            return BaseResponse.response(searchResponse);
        } else {
            Page<ClubSearchResponse> searchResponse =
                    clubService.findClubs(member.getMember(), pageable, null);
            return BaseResponse.response(searchResponse);
        }
    }

    //소모임 등록
    @PostMapping
    public BaseResponse<ClubSearchResponse> create(
            @AuthenticationPrincipal MemberDetails member,
            @RequestBody ClubCreateRequest request
    ) {
        ClubSearchResponse response = clubService.createClub(member, request);
        return BaseResponse.response(response);
    }

    //소모임 수정
    @PutMapping("/{club_id}")
    public BaseResponse<ClubSearchResponse> update(
            @AuthenticationPrincipal MemberDetails member,
            @PathVariable("club_id") Long clubId,
            @RequestBody ClubUpdateRequest updateRequest
    ) {
        ClubSearchResponse updatedClub = clubService.updateClub(member, clubId, updateRequest);
        return BaseResponse.response(updatedClub);
    }

    //소모임 삭제
    @DeleteMapping("/{club_id}")
    public BaseResponse<String> delete(
            @AuthenticationPrincipal MemberDetails member,
            @PathVariable("club_id") Long clubId
    ) {
        clubService.deleteClub(member, clubId);
        return BaseResponse.response(BaseResponseStatus.DELETE_SUCCESS);
    }

    @GetMapping("/{club_id}/question")
    public BaseResponse<ClubQuestionResponse> findQuestion(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @PathVariable("club_id") Long clubId
    ) {
        ClubQuestionResponse response = clubService.findQuestion(memberDetails, clubId);
        return BaseResponse.response(response);
    }

    //소모임 좋아요
    @PostMapping("/{club_id}/like")
    public BaseResponse<Void> likeClub(
            @AuthenticationPrincipal MemberDetails member,
            @PathVariable("club_id") Long clubId
    ) {

        if (clubService.likeClub(member.getId(), clubId).equals("deleted")) {
            return BaseResponse.response(BaseResponseStatus.LIKE_DELETED);
        } else {
            return BaseResponse.response(BaseResponseStatus.LIKE_SUCCESS);
        }
    }

    // 좋아요 조회
    @GetMapping("/like")
    public BaseResponse<Page<ClubSearchResponse>> findClub(
            @AuthenticationPrincipal MemberDetails member,
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        Page<ClubSearchResponse> response = clubService.findLikeClub(member, pageable);
        return BaseResponse.response(response);
    }
}
