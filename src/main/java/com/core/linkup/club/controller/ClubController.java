package com.core.linkup.club.controller;

import com.core.linkup.club.request.ClubRequest;
import com.core.linkup.club.response.ClubsResponse;
import com.core.linkup.club.service.ClubService;
import com.core.linkup.common.response.BaseResponse;
import com.core.linkup.security.MemberDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/club")
public class ClubController {

    private final ClubService clubService;

    @PostMapping
    public BaseResponse<List<ClubsResponse>> clubRegister(
            @AuthenticationPrincipal MemberDetails member,
            @Valid @RequestBody ClubRequest clubRequest
    ) {
        List<ClubsResponse> clubsResponses = clubService.clubRegister(member, clubRequest);
        return BaseResponse.response(clubsResponses);
    }

}
