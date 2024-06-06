package com.core.linkup.club.clubmeeting.service;

import com.core.linkup.club.club.entity.Club;
import com.core.linkup.club.club.entity.ClubMember;
import com.core.linkup.club.club.repository.ClubMemberRepository;
import com.core.linkup.club.club.repository.ClubRepository;
import com.core.linkup.club.clubmeeting.response.ClubMeetingResponse;
import com.core.linkup.club.clubmeeting.converter.ClubMeetingConverter;
import com.core.linkup.club.clubmeeting.entity.ClubMeeting;
import com.core.linkup.club.clubmeeting.repository.ClubMeetingRepository;
import com.core.linkup.club.clubmeeting.request.ClubMeetingRequest;
import com.core.linkup.common.exception.BaseException;
import com.core.linkup.common.response.BaseResponseStatus;
import com.core.linkup.member.entity.Member;
import com.core.linkup.member.repository.MemberRepository;
import com.core.linkup.security.MemberDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

        invalidClubMember(clubId, memberId);

        Club club = invalidClub(clubId);

        ClubMeeting clubMeeting = clubMeetingConverter.toMeetingEntity(request, club);
        clubMeeting.setMemberId(memberId);
        ClubMeeting savedMeeting = clubMeetingRepository.save(clubMeeting);

        Member member = invalidMember(memberId);

        return clubMeetingConverter.toMeetingResponse(savedMeeting, member);
    }

    public List<ClubMeetingResponse> findAllMeetings(MemberDetails memberDetails, Long clubId) {
        Long memberId = memberDetails.getId();

        invalidClubMember(clubId, memberId);

        invalidClub(clubId);

        List<ClubMeeting> clubMeetings = clubMeetingRepository.findByClubId(clubId);

        Member member = invalidMember(memberId);

        List<ClubMeetingResponse> responses = clubMeetings.stream()
                .map(meeting -> clubMeetingConverter.toMeetingResponse(meeting, member))
                .collect(Collectors.toList());

        return responses;
    }

    public ClubMeetingResponse findMeeting(MemberDetails memberDetails, Long clubId, Long meetingId) {
        Long memberId = memberDetails.getId();

        invalidClubMember(clubId, memberId);

        invalidClub(clubId);

        List<ClubMeeting> clubMeetings = clubMeetingRepository.findByClubId(clubId);

        ClubMeeting clubMeeting = clubMeetings.stream()
                .filter(meeting -> meeting.getId().equals(meetingId))
                .findFirst()
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_CLUB_MEETING));

        Member member = invalidMember(memberId);

        return clubMeetingConverter.toMeetingResponse(clubMeeting, member);
    }

    private Member invalidMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_MEMBER));
        return member;
    }

    private Club invalidClub(Long clubId) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_CLUB_ID));
        return club;
    }

    private void invalidClubMember(Long clubId, Long memberId) {
        ClubMember clubMember = clubMemberRepository.findByMemberIdAndClubId(memberId, clubId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_CLUB_MEMBER));
    }
}
