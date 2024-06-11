package com.core.linkup.club.clubmeeting.service;

import com.core.linkup.club.club.entity.Club;
import com.core.linkup.club.club.entity.ClubMember;
import com.core.linkup.club.club.repository.ClubMemberRepository;
import com.core.linkup.club.club.repository.ClubRepository;
import com.core.linkup.club.clubmeeting.converter.ClubMeetingConverter;
import com.core.linkup.club.clubmeeting.entity.ClubMeeting;
import com.core.linkup.club.clubmeeting.repository.ClubMeetingRepository;
import com.core.linkup.club.clubmeeting.request.ClubMeetingRequest;
import com.core.linkup.club.clubmeeting.response.ClubMeetingResponse;
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

//        validateClubMember(clubId, memberId);
        if (!isClubCreatorOrMember(clubId, memberId)) {
            throw new BaseException(BaseResponseStatus.INVALID_CLUB_ID);
        }
        Club club = validateClub(clubId);

        ClubMeeting clubMeeting = clubMeetingConverter.toMeetingEntity(request, club);
        clubMeeting.setMemberId(memberId);
        ClubMeeting savedMeeting = clubMeetingRepository.save(clubMeeting);

        Member member = validateMember(memberId);

        return clubMeetingConverter.toMeetingResponse(savedMeeting, member);
    }

    //정기모임 조회
    public List<ClubMeetingResponse> findAllMeetings(MemberDetails memberDetails, Long clubId) {
        Long memberId = memberDetails.getId();
        validateClub(clubId);

        if (!isClubCreatorOrMember(clubId, memberId)) {
            throw new BaseException(BaseResponseStatus.INVALID_CLUB_ID);
        }

        List<ClubMeeting> clubMeetings = clubMeetingRepository.findByClubId(clubId);

        Member member = validateMember(memberId);

        List<ClubMeetingResponse> responses = clubMeetings.stream()
                .map(meeting -> clubMeetingConverter.toMeetingResponse(meeting, member))
                .collect(Collectors.toList());

        return responses;
    }

    private boolean isClubCreatorOrMember(Long clubId, Long memberId) {
        // 클럽 생성자 확인
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_CLUB_ID));

        if (club.getMemberId().equals(memberId)) {
            return true;
        }

        // 클럽 멤버 확인
        return clubMemberRepository.existsByClubIdAndMemberId(clubId, memberId);
    }

    public ClubMeetingResponse findMeeting(MemberDetails memberDetails, Long clubId, Long meetingId) {
        Long memberId = memberDetails.getId();

        validateClub(clubId);
        if (!isClubCreatorOrMember(clubId, memberId)) {
            throw new BaseException(BaseResponseStatus.INVALID_CLUB_ID);
        }

        List<ClubMeeting> clubMeetings = clubMeetingRepository.findByClubId(clubId);

        ClubMeeting clubMeeting = clubMeetings.stream()
                .filter(meeting -> meeting.getId().equals(meetingId))
                .findFirst()
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_CLUB_MEETING));

        Member member = validateMember(memberId);

        return clubMeetingConverter.toMeetingResponse(clubMeeting, member);
    }

    //정기모임 수정
    public ClubMeetingResponse updateMeeting(MemberDetails memberDetails, Long clubId, Long meetingId, ClubMeetingRequest request) {
        Long memberId = memberDetails.getId();

        ClubMeeting clubMeeting = validateMeeting(clubId, meetingId, memberId);

        if (!clubMeeting.getClubId().equals(clubId) || !clubMeeting.getId().equals(meetingId)) {
            throw new BaseException(BaseResponseStatus.INVALID_CLUB_ID);
        }

        if (!isClubCreatorOrMember(clubId, memberId)) {
            throw new BaseException(BaseResponseStatus.INVALID_CLUB_ID);
        }

        clubMeeting = clubMeetingConverter.toUpdateMeetingEntity(clubMeeting, request);
        Member member = validateMember(memberId);
        ClubMeeting updatedMeeting = clubMeetingRepository.save(clubMeeting);

        return clubMeetingConverter.toMeetingResponse(updatedMeeting, member);
    }

    // 정기모임 삭제
    public void deleteMeeting(MemberDetails memberDetails, Long clubId, Long meetingId) {
        Long memberId = memberDetails.getId();

        validateClub(clubId);
        if (!isClubCreatorOrMember(clubId, memberId)) {
            throw new BaseException(BaseResponseStatus.INVALID_CLUB_ID);
        }

        ClubMeeting clubMeeting = validateMeeting(clubId, meetingId, memberId);

        clubMeetingRepository.delete(clubMeeting);
    }

    private Member validateMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_MEMBER));
        return member;
    }

    private Club validateClub(Long clubId) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_CLUB_ID));
        return club;
    }


    private ClubMeeting validateMeeting(Long clubId, Long meetingId, Long memberId) {
        ClubMeeting clubMeeting = clubMeetingRepository.findByIdAndClubId(meetingId, clubId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_CLUB_MEETING));

        // 현재 로그인한 사용자와 정기모임 생성자가 동일한지 확인
        if (!clubMeeting.getMemberId().equals(memberId)) {
            throw new BaseException(BaseResponseStatus.INVALID_WRITER);
        }
        return clubMeeting;
    }
}
