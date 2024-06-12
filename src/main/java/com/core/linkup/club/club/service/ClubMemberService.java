package com.core.linkup.club.club.service;

import com.core.linkup.club.club.converter.ClubConverter;
import com.core.linkup.club.club.entity.*;
import com.core.linkup.club.club.repository.*;
import com.core.linkup.club.club.request.ClubApplicationRequest;
import com.core.linkup.club.club.request.ClubMemberApprovalRequest;
import com.core.linkup.club.club.response.ClubApplicationResponse;
import com.core.linkup.club.club.response.ClubSearchResponse;
import com.core.linkup.common.entity.BaseEntity;
import com.core.linkup.common.exception.BaseException;
import com.core.linkup.common.response.BaseResponseStatus;
import com.core.linkup.member.entity.Member;
import com.core.linkup.member.repository.MemberRepository;
import com.core.linkup.security.MemberDetails;
import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClubMemberService {

    private final ClubRepository clubRepository;
    private final MemberRepository memberRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final ClubConverter clubConverter;
    private final ClubAnswerRepository clubAnswerRepository;
    private final ClubLikeRepository likeRepository;

    //소모임 가입
    public ClubApplicationResponse joinClub(Long memberId, Long clubId, ClubApplicationRequest request) {
        Club club = validateClub(clubId);
        Member member = validateMember(memberId);

        if (club.getMemberId().equals(memberId)) {
            throw new BaseException(BaseResponseStatus.OWNER_CANNOT_JOIN_CLUB);
        }

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
                        List<ClubAnswer> answers = clubAnswerRepository.findByMemberIdAndClubId(clubMember.getMemberId(), clubId);
                        return clubConverter.toClubApplicationResponse(clubMember, answers, club);
                    })
                    .collect(Collectors.toList());
        } else {
            ClubMember clubMember = clubMemberRepository.findByClubIdAndMemberId(clubId, memberId)
                    .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_CLUB_MEMBER));

            List<ClubAnswer> answers = clubAnswerRepository.findByMemberIdAndClubId(memberId, clubId);
            return Collections.singletonList(clubConverter.toClubApplicationResponse(clubMember, answers, club));
        }
    }

    public List<ClubApplicationResponse> findMyClubApplicationList(MemberDetails member) {
        Long memberId = member.getMember().getId();
        List<ClubMember> clubMembers = clubMemberRepository.findByMemberId(memberId);

        List<Long> memberLikes = likeRepository.findAllByMemberId(memberId).stream().map(
                ClubLike::getClubId
        ).collect(Collectors.toList());

        return clubMembers.stream()
                .map(clubMember -> {
                    List<ClubAnswer> clubAnswers = clubAnswerRepository.findByMemberId(clubMember.getId());
                    Club club = validateClub(clubMember.getClubId());

                    boolean isLiked = memberLikes.contains(club.getId());

                    return clubConverter.toClubApplicationResponse(clubMember, clubAnswers, club, isLiked);
                })
                .collect(Collectors.toList());
    }

    // 소모임 지원 승인/거절
    public boolean approveMyClubMemberApplication(
            Member member, Long clubId, Long clubMemberId, ClubMemberApprovalRequest request) {
        Club club = validateClub(clubId);
        if (club.getMemberId().equals(member.getId()) // 소모임 창설자인지
                && club.getClubAccessibility()) {  // 소모임이 가입 시 승인 필수인지
            if (request.approval()) {
                // 승인 시
                ClubMember clubApplicant = clubMemberRepository.findByClubIdAndMemberId(clubId, clubMemberId)
                        .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_CLUB_MEMBER));
                clubApplicant = clubConverter.updateClubMember(clubApplicant);
                clubMemberRepository.save(clubApplicant);
                return true;
            } else {
                // 거절 시
                clubMemberRepository.deleteById(clubMemberId);
                return false;
            }
        } else {
            throw new BaseException(BaseResponseStatus.INVALID_CLUB_OWNER);
        }
    }

    private Member validateMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.UNREGISTERD_MEMBER));
    }

    private Club validateClub(Long clubId) {
        return clubRepository.findById(clubId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_CLUB_ID));
    }

    public Page<ClubSearchResponse> findManagingApplication(MemberDetails memberDetails, Pageable pageable) {
        Long memberId = memberDetails.getId();
        Page<Club> clubs = clubRepository.findByMemberId(memberId, pageable);
        return clubs.map(club -> {
            Member member = memberRepository.findById(club.getMemberId())
                    .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_CLUB_MEMBER));
            return clubConverter.toManagingApplication(club, member);
        });
    }
}
