package com.core.linkup.club.club.service;

import com.core.linkup.club.club.converter.ClubConverter;
import com.core.linkup.club.club.entity.Club;
import com.core.linkup.club.club.entity.ClubAnswer;
import com.core.linkup.club.club.entity.ClubMember;
import com.core.linkup.club.club.entity.ClubQuestion;
import com.core.linkup.club.club.repository.ClubAnswerRepository;
import com.core.linkup.club.club.repository.ClubMemberRepository;
import com.core.linkup.club.club.repository.ClubQuestionRepository;
import com.core.linkup.club.club.repository.ClubRepository;
import com.core.linkup.club.club.request.ClubApplicationRequest;
import com.core.linkup.club.club.request.ClubCreateRequest;
import com.core.linkup.club.club.request.ClubSearchRequest;
import com.core.linkup.club.club.request.ClubUpdateRequest;
import com.core.linkup.club.club.response.ClubApplicationResponse;
import com.core.linkup.club.club.response.ClubSearchResponse;
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
    private final ClubAnswerRepository clubAnswerRepository;

    //소모임 조회
    public ClubSearchResponse findClub(Long clubId) {
        Club club = validateClub(clubId);
        Member member = validateMember(club.getMemberId());

        List<ClubMember> clubMembers = clubMemberRepository.findByClubId(clubId);
        List<Long> memberIds = clubMembers.stream()
                .map(ClubMember::getMemberId)
                .collect(Collectors.toList());

        List<Member> members = memberRepository.findAllById(memberIds);

        Map<Long, Member> memberMap = members.stream()
                .collect(Collectors.toMap(Member::getId, m -> m));

        return clubConverter.toClubResponse(club, member, clubMembers, memberMap);
    }

    public Page<ClubSearchResponse> findClubs(Pageable pageable, ClubSearchRequest request) {
        Page<Club> clubs = clubRepository.findSearchClubs(request, pageable);
        List<Member> members = memberRepository.findAllById(clubs.stream()
                .map(Club::getMemberId)
                .collect(Collectors.toList()));
        Map<Long, Member> memberMap = members.stream()
                .collect(Collectors.toMap(Member::getId, Function.identity()));
        return clubs.map(club -> clubConverter.toClubResponses(club, memberMap.get(club.getMemberId())));
    }

    // 소모임 등록
    public ClubSearchResponse createClub(MemberDetails member, ClubCreateRequest request) {
        Long memberId = getMemberId(member);
        Club club = clubConverter.toClubEntity(request, member);
        Club savedClub = clubRepository.save(club);

        if (request.clubQuestions() != null && !request.clubQuestions().isEmpty()) {
            List<ClubQuestion> questions = request.clubQuestions().stream()
                    .map(questionRequest -> clubConverter.toClubQuestionEntity(questionRequest, savedClub))
                    .collect(Collectors.toList());
            clubQuestionRepository.saveAll(questions);
        }

        Member creator = memberRepository.findById(memberId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_CLUB_OWNER));

        return clubConverter.toClubResponses(savedClub, creator);
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

        return clubConverter.toClubResponses(savedClub, creator);
    }

    private static Long getMemberId(MemberDetails member) {
        if (member == null) {
            throw new BaseException(BaseResponseStatus.UNREGISTERD_MEMBER);
        }
        return member.getId();
    }

    //소모임 삭제
    public void delete(MemberDetails member, Long clubId) {
        clubRepository.deleteById(clubId);
    }

    //소모임 가입
    public ClubApplicationResponse joinClub(Long memberId, Long clubId, ClubApplicationRequest request) {
        Club club = validateClub(clubId);
        Member member = validateMember(memberId);

        Optional<ClubMember> existingClubMember = clubMemberRepository.findByClubIdAndMemberId(clubId, memberId);
        ClubMember clubMember;
        if (existingClubMember.isPresent()) {
            clubMember = existingClubMember.get();
        } else {
            clubMember = clubConverter.toClubMember(club, member, request);
            clubMemberRepository.save(clubMember);
        }

        List<ClubAnswer> answers = new ArrayList<>();
        if (request.getClubAnswers() != null && !request.getClubAnswers().isEmpty()) {
            answers = request.getClubAnswers().stream()
                    .map(answerRequest -> clubConverter.toClubAnswerEntity(answerRequest, memberId, clubId, clubMember.getId()))
                    .collect(Collectors.toList());
            clubAnswerRepository.saveAll(answers);
        }
        return clubConverter.toClubApplicationResponse(clubMember, answers, club);
    }

    // 소모임 가입 조회
    public List<ClubApplicationResponse> findClubApplications(MemberDetails member, Long clubId) {
        Long memberId = member != null ? member.getMember().getId() : null;
        Club club = validateClub(clubId);

        // 소모임을 생성한 사람인 경우 + 가입한 사람이 소모임 조회
        if (club.getMemberId().equals(memberId)) {
            return clubMemberRepository.findByClubId(clubId).stream()
                    .map(clubMember -> {
                        List<ClubAnswer> answers = clubAnswerRepository.findByMemberId(clubMember.getMemberId());
                        return clubConverter.toClubApplicationResponse(clubMember, answers, club);
                    })
                    .collect(Collectors.toList());
        } else {
            ClubMember clubMember = clubMemberRepository.findByClubIdAndMemberId(clubId, memberId)
                    .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_CLUB_ID));

            List<ClubAnswer> answers = clubAnswerRepository.findByMemberId(memberId);
            return Collections.singletonList(clubConverter.toClubApplicationResponse(clubMember, answers, club));
        }
    }

    public List<ClubApplicationResponse> findMyClubApplicationList(MemberDetails member) {
        Long memberId = member.getMember().getId();
        List<ClubMember> clubMembers = clubMemberRepository.findByMemberId(memberId);

        return clubMembers.stream()
                .map(clubMember -> {
                    List<ClubAnswer> clubAnswers = clubAnswerRepository.findByMemberId(clubMember.getId());
                    Club club = validateClub(clubMember.getClubId());
                    return clubConverter.toClubApplicationResponse(clubMember, clubAnswers, club);
                })
                .collect(Collectors.toList());
    }

    private Member validateMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.UNREGISTERD_MEMBER));
        return member;
    }

    private Club validateClub(Long clubId) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_CLUB_ID));
        return club;
    }
}
