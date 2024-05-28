package com.core.linkup.club.controller;

import com.core.linkup.club.entity.Club;
import com.core.linkup.club.request.ClubRequest;
import com.core.linkup.club.response.ClubsResponse;
import com.core.linkup.club.service.ClubServiceTest;
import com.core.linkup.common.response.BaseResponse;
import com.core.linkup.security.MemberDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/club")
public class ClubControllerTest {

    private final ClubServiceTest clubService;

    @PostMapping("/a")
    public BaseResponse<List<ClubsResponse>> createClub(
            @AuthenticationPrincipal MemberDetails member,
            @Valid @RequestBody ClubRequest clubRequest
    ) {
        List<ClubsResponse> clubsResponses = clubService.createClub(member, clubRequest);
        return BaseResponse.response(clubsResponses);
    }
}
