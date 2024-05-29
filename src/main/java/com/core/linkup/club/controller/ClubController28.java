package com.core.linkup.club.controller;

import com.core.linkup.club.requset.ClubCreateRequest28;
import com.core.linkup.club.requset.ClubUpdateRequest;
import com.core.linkup.club.response.ClubSearchResponse28;
import com.core.linkup.club.service.ClubService28;
import com.core.linkup.common.response.BaseResponse;
import com.core.linkup.security.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/club")
public class ClubController28 {

    private final ClubService28 clubService;

    //소모임 개별조회
    @GetMapping("/{clubId}")
    public BaseResponse<ClubSearchResponse28> findClub(
            @AuthenticationPrincipal MemberDetails member,
            @PathVariable Long clubId
    ){
        ClubSearchResponse28 response = clubService.findClub(clubId);
        return BaseResponse.response(response);
    }

    //소모임 등록
    @PostMapping
    public BaseResponse<ClubSearchResponse28> create(
            @AuthenticationPrincipal MemberDetails member,
            @RequestBody ClubCreateRequest28 request
    ){
        ClubSearchResponse28 response = clubService.createClub(member, request);
        return BaseResponse.response(response);
    }

    //소모임 수정
    @PutMapping("/{club_id}")
    public BaseResponse<ClubSearchResponse28> update(
            @AuthenticationPrincipal MemberDetails member,
            @PathVariable("club_id") Long clubId,
            @RequestBody ClubUpdateRequest updateRequest
    ){
        ClubSearchResponse28 updatedClub = clubService.updateClub(member, clubId, updateRequest);
        return BaseResponse.response(updatedClub);
    }

    @DeleteMapping("/{club_id}")
    public BaseResponse<String> delete(
            @AuthenticationPrincipal MemberDetails member,
            @PathVariable("club_id") Long clubId
    ){
        clubService.delete(member, clubId);
        return BaseResponse.response("OK");
    }

}
