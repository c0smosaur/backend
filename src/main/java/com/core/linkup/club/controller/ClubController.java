package com.core.linkup.club.controller;

import com.core.linkup.club.requset.ClubApplicationRequest;
import com.core.linkup.club.requset.ClubCreateRequest;
import com.core.linkup.club.requset.ClubSearchRequest;
import com.core.linkup.club.requset.ClubUpdateRequest;
import com.core.linkup.club.response.ClubApplicationResponse;
import com.core.linkup.club.response.ClubSearchResponse;
import com.core.linkup.club.service.ClubService;
import com.core.linkup.common.response.BaseResponse;
import com.core.linkup.security.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        ClubSearchResponse response = clubService.findClub(clubId);
        return BaseResponse.response(response);
    }

    //소모임 필터링으로 조회 (ClubType으로 조회 가능)
    //TODO : OfficeBuilding으로 조회 가능 하도록 할 예정
    @GetMapping("/search")
    public BaseResponse<Page<ClubSearchResponse>> findClubs(
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
            @ModelAttribute ClubSearchRequest request
    ) {
        Page<ClubSearchResponse> searchResponse = clubService.findClubs(pageable, request);
        return BaseResponse.response(searchResponse);
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
        clubService.delete(member, clubId);
        return BaseResponse.response("OK");
    }

    //소모임 지원
    @PostMapping("/{club_id}/application")
    public BaseResponse<ClubApplicationResponse> joinClub(
            @AuthenticationPrincipal MemberDetails member,
            @PathVariable("club_id") Long clubId,
            @RequestBody ClubApplicationRequest request
    ) {
        Long memberId = member.getId();
        ClubApplicationResponse response = clubService.joinClub(memberId, clubId, request);
        return BaseResponse.response(response);
    }

    @GetMapping("/{club_id}/application")
    public BaseResponse<List<ClubApplicationResponse>> findClubApplications(
            @AuthenticationPrincipal MemberDetails member,
            @PathVariable("club_id") Long clubId
    ) {
        List<ClubApplicationResponse> response = clubService.findClubApplications(member, clubId);
        return BaseResponse.response(response);
    }
}
