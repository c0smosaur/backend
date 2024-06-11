package com.core.linkup.club.club.service;

import com.core.linkup.club.club.converter.ClubConverter;
import com.core.linkup.club.club.entity.*;
import com.core.linkup.club.club.repository.*;
import com.core.linkup.club.club.request.*;
import com.core.linkup.club.club.response.ClubQuestionResponse;
import com.core.linkup.club.club.response.ClubSearchResponse;
import com.core.linkup.club.clubmeeting.entity.ClubMeeting;
import com.core.linkup.club.clubmeeting.repository.ClubMeetingRepository;
import com.core.linkup.common.exception.BaseException;
import com.core.linkup.common.response.BaseResponseStatus;
import com.core.linkup.member.entity.Member;
import com.core.linkup.member.repository.MemberRepository;
import com.core.linkup.security.MemberDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClubService {

    private final ClubRepository clubRepository;
    private final MemberRepository memberRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final ClubQuestionRepository clubQuestionRepository;
    private final ClubConverter clubConverter;
    private final ClubMeetingRepository clubMeetingRepository;
    private final ClubLikeRepository clubLikeRepository;

    //소모임 개별 조회
    public ClubSearchResponse findClub(Long clubId, Member member) {
        Club club = validateClub(clubId);
        // 소모임 창설자
        Member clubHost = validateMember(club.getMemberId());

        List<ClubMember> clubMembers = clubMemberRepository.findByClubId(clubId);
        List<Long> memberIds = clubMembers.stream()
                .map(ClubMember::getMemberId)
                .collect(Collectors.toList());

        List<Member> members = memberRepository.findAllById(memberIds);

        List<ClubMeeting> clubMeetings = clubMeetingRepository.findByClubId(clubId);

        Map<Long, Member> memberMap = members.stream()
                .collect(Collectors.toMap(Member::getId, m -> m));

        Boolean liked = clubLikeRepository.existsByClubIdAndMemberId(clubId, member.getId());

        return clubConverter.toClubResponse(club, clubHost, clubMembers, clubMeetings, memberMap, liked);
    }

    // 로그인 시 전체조회
    public Page<ClubSearchResponse> findClubs(Member member, Pageable pageable, String category) {
        Page<Club> clubs = clubRepository.findSearchClubs(category, pageable);
        List<Member> members = memberRepository.findAllById(clubs.stream()
                .map(Club::getMemberId)
                .collect(Collectors.toList()));
        Map<Long, Member> memberMap = members.stream()
                .collect(Collectors.toMap(Member::getId, Function.identity()));

        List<ClubLike> clubLikes = clubLikeRepository.findAllByMemberId(member.getId());
        List<Long> clubLikeIds = clubLikes.stream().map(ClubLike::getClubId).toList();

        return clubs.map(club ->
                clubConverter.toClubResponse(
                        club, memberMap.get(club.getMemberId()), clubLikeIds.contains(club.getId())));
    }

    // 비로그인 시 전체조회
        public Page<ClubSearchResponse> findClubs(Pageable pageable, String category){

            Page<Club> clubs = clubRepository.findSearchClubs(category, pageable);
            List<Member> members = memberRepository.findAllById(clubs.stream()
                    .map(Club::getMemberId)
                    .collect(Collectors.toList()));
            Map<Long, Member> memberMap = members.stream()
                    .collect(Collectors.toMap(Member::getId, Function.identity()));

            return clubs.map(club ->
                    clubConverter.toClubResponse(
                            club, memberMap.get(club.getMemberId())));
    }

    // 소모임 등록
    public ClubSearchResponse createClub(MemberDetails member, ClubCreateRequest request) {
        Long memberId = getMemberId(member);

        Member creator = memberRepository.findById(memberId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_CLUB_OWNER));

        // 멤버십 확인
//        if (!clubRepository.existsValidMembershipWithLocation(memberId)) {
//            throw new BaseException(BaseResponseStatus.INVALID_MEMBERSHIP);
//        }

        Club club = clubConverter.toClubEntity(request, member);
        Club savedClub = clubRepository.save(club);

        if (request.clubQuestions() != null && !request.clubQuestions().isEmpty()) {
            Club finalSavedClub = savedClub;
            List<ClubQuestion> questions = request.clubQuestions().stream()
                    .map(questionRequest -> clubConverter.toClubQuestionEntity(questionRequest, finalSavedClub))
                    .collect(Collectors.toList());
            clubQuestionRepository.saveAll(questions);
        }

        return clubConverter.toClubResponse(savedClub, creator);
    }

    //소모임 수정
    public ClubSearchResponse updateClub(MemberDetails member, Long clubId, ClubUpdateRequest updateRequest) {
        Long memberId = getMemberId(member);
        Club existingClub = validateClub(clubId);

        if (!existingClub.getMemberId().equals(memberId)) {
            throw new BaseException(BaseResponseStatus.INVALID_MEMBER);
        }

        Club updatedClub = clubConverter.updateClubEntity(existingClub, updateRequest, member);
        Club savedClub = clubRepository.save(updatedClub);
        Member creator = memberRepository.findById(memberId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_CLUB_OWNER));

        return clubConverter.toClubResponse(savedClub, creator);
    }

    private static Long getMemberId(MemberDetails member) {
        if (member == null) {
            throw new BaseException(BaseResponseStatus.UNREGISTERD_MEMBER);
        }
        return member.getId();
    }

    //소모임 삭제
    public void deleteClub(MemberDetails member, Long clubId) {
        clubRepository.deleteById(clubId);
    }

    private Member validateMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.UNREGISTERD_MEMBER));
    }

    private Club validateClub(Long clubId) {
        return clubRepository.findById(clubId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_CLUB_ID));
    }

    public String likeClub(Long memberId, Long clubId) {
        boolean duplicate = clubRepository.existsByMemberIdAndClubId(memberId, clubId);

        if (duplicate) {
            clubRepository.deleteByMemberIdAndClubId(memberId, clubId);
            return "deleted";
        } else {
            ClubLike clubLike = clubConverter.toLikeEntity(memberId, clubId);
            clubLikeRepository.save(clubLike);
            Club club = clubRepository.findById(clubId)
                    .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_CLUB_ID));
            return "liked";
        }
    }

    // 찜한 소모임 조회
    public Page<ClubSearchResponse> findLikeClub(MemberDetails member, Pageable pageable) {
        Long memberId = member.getId();

        Page<ClubLike> clubLikes = clubRepository.findClubLikes(memberId, pageable);

        return clubLikes.map(clubLike -> {
            Club club = clubRepository.findById(clubLike.getClubId()).orElseThrow((null));
            Member clubHost = memberRepository.findById(club.getMemberId()).orElseThrow(
                    () -> new BaseException(BaseResponseStatus.INVALID_CLUB_OWNER));
//            ClubMeeting clubMeeting = clubMeetingRepository.findFirstByClubIdOrderByDateDesc(club.getId()).orElse(null);
            return clubConverter.toClubResponse(club, clubHost, true);
        });
    }

    //소모임 질문 조회
    public ClubQuestionResponse findQuestion(MemberDetails memberDetails, Long clubId) {
        Long memberId = memberDetails.getId();

        List<ClubQuestion> questions = clubQuestionRepository.findAllByClubId(clubId);
        Optional<Club> clubOptional = clubRepository.findById(clubId);

        if (clubOptional.isPresent()) {
            Club club = clubOptional.get();

            return clubConverter.toQuestionResponse(questions, club);
        } else {
            throw new BaseException(BaseResponseStatus.INVALID_CLUB_ID);
        }

    }
}
