package com.core.linkup.club.repository;

import com.core.linkup.club.request.ClubRequest;
import com.core.linkup.club.response.ClubsResponse;
import com.core.linkup.security.MemberDetails;

import java.util.List;

public interface ClubCustomRepository {

    List<ClubsResponse> clubRegister(MemberDetails member, ClubRequest clubRequest);
}
