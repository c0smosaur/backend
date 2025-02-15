package com.core.linkup.club.club.controller;

import com.core.linkup.club.club.request.ClubApplicationRequest;
import com.core.linkup.club.club.request.ClubMemberApprovalRequest;
import com.core.linkup.club.club.response.ClubApplicationResponse;
import com.core.linkup.club.club.response.ClubSearchApplicationResponse;
import com.core.linkup.club.club.response.ClubSearchResponse;
import com.core.linkup.club.club.service.ClubMemberService;
import com.core.linkup.common.response.BaseResponse;
import com.core.linkup.common.response.BaseResponseStatus;
import com.core.linkup.security.MemberDetails;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/club")
@RequiredArgsConstructor
@Tag(name = "Club - Member", description = "소모임 인원 관리 관련 API")
public class ClubMemberController {

    private final ClubMemberService clubMemberService;

    //소모임 지원
    @PostMapping("/{club_id}/application")
    public BaseResponse<ClubApplicationResponse> joinClub(
            @AuthenticationPrincipal MemberDetails member,
            @PathVariable("club_id") Long clubId,
            @RequestBody ClubApplicationRequest request
    ) {
        Long memberId = member.getId();
        ClubApplicationResponse response = clubMemberService.joinClub(memberId, clubId, request);
        return BaseResponse.response(response);
    }

    //소모임에 지원한 멤버 확인 -> 생성자 + 지원자
    @GetMapping("/{club_id}/application")
    public BaseResponse<List<ClubApplicationResponse>> findClubApplications(
            @AuthenticationPrincipal MemberDetails member,
            @PathVariable("club_id") Long clubId
    ) {
        List<ClubApplicationResponse> response = clubMemberService.findClubApplications(member, clubId);
        return BaseResponse.response(response);
    }

    //내가 지원한 소모임 전체 조회
    @GetMapping("/application")
    public BaseResponse<List<ClubApplicationResponse>> findMyApplicationList(
            @AuthenticationPrincipal MemberDetails member
    ) {
        List<ClubApplicationResponse> response = clubMemberService.findMyClubApplicationList(member);
        return BaseResponse.response(response);
    }

    // 소모임 창설자가 소모임 가입 신청자 승인/거절
    @PostMapping("/{clubId}/{clubMemberId}")
    public BaseResponse<Void> manageClubMember(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @PathVariable("clubId") Long clubId,
            @PathVariable("clubMemberId") Long clubMemberId,
            @RequestBody ClubMemberApprovalRequest request) {

        if (clubMemberService.approveMyClubMemberApplication(
                memberDetails.getMember(), clubId, clubMemberId, request)){
            // 승인
            return BaseResponse.response(BaseResponseStatus.CLUB_ACCEPTED);
        } else {
            // 거절
            return BaseResponse.response(BaseResponseStatus.CLUB_REJECTED);
        }

    }

    // 내가 가입한 소모임 관리
    @GetMapping("/applicants")
    public BaseResponse<Page<ClubSearchResponse>> findManagingApplication(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @PageableDefault Pageable pageable
    ){

        Page<ClubSearchResponse> responses = clubMemberService.findManagingApplication(memberDetails, pageable);
        return BaseResponse.response(responses);
    }

    @GetMapping("/application/search")
    public BaseResponse<Page<ClubSearchApplicationResponse>> findSearchApplicationList(
            @AuthenticationPrincipal MemberDetails member,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<ClubSearchApplicationResponse> response = clubMemberService.findSearchClubApplicationList(member, PageRequest.of(page, size));
        return BaseResponse.response(response);
    }
}