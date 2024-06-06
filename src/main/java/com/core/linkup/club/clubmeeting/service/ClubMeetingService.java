package com.core.linkup.club.clubmeeting.service;

import com.core.linkup.club.club.entity.Club;
import com.core.linkup.club.club.entity.ClubMember;
import com.core.linkup.club.club.repository.ClubMemberRepository;
import com.core.linkup.club.club.repository.ClubRepository;
import com.core.linkup.club.club.response.ClubMeetingResponse;
import com.core.linkup.club.clubmeeting.converter.ClubMeetingConverter;
import com.core.linkup.club.clubmeeting.entity.ClubMeeting;
import com.core.linkup.club.clubmeeting.repository.ClubMeetingRepository;
import com.core.linkup.club.clubmeeting.request.ClubMeetingRequest;
import com.core.linkup.member.entity.Member;
import com.core.linkup.member.repository.MemberRepository;
import com.core.linkup.security.MemberDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClubMeetingService {

    private final ClubMeetingRepository clubMeetingRepository;
    private final ClubRepository clubRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final MemberRepository memberRepository;
    private final ClubMeetingConverter clubMeetingConverter;

    //정기모임 등록
    public ClubMeetingResponse createMeeting(MemberDetails memberDetails, Long clubId, ClubMeetingRequest request) {
        Long memberId = memberDetails.getId();

        ClubMember clubMember = clubMemberRepository.findByMemberIdAndClubId(memberId, clubId)
                .orElseThrow(() -> new IllegalArgumentException("Not a valid club member"));

        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new IllegalArgumentException("Club not found"));

        ClubMeeting clubMeeting = clubMeetingConverter.toMeetingEntity(request, club);
        clubMeeting.setMemberId(memberId);
        ClubMeeting savedMeeting = clubMeetingRepository.save(clubMeeting);

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        return clubMeetingConverter.toMeetingResponse(savedMeeting, member);
    }

}
