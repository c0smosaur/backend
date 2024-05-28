package com.core.linkup.club.service;

import com.core.linkup.club.repository.ClubRepository;
import com.core.linkup.club.request.ClubRequest;
import com.core.linkup.club.response.ClubsResponse;
import com.core.linkup.security.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ClubServiceTest {
    private final ClubRepository clubRepository;

    public List<ClubsResponse> createClub(MemberDetails member, ClubRequest clubRequest) {
        return clubRepository.clubRegister(member, clubRequest);
    }
}
