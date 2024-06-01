package com.core.linkup.club.service;

import com.core.linkup.club.converter.ClubConverter;
import com.core.linkup.club.entity.Club;
import com.core.linkup.club.entity.ClubAnswer;
import com.core.linkup.club.entity.ClubMember;
import com.core.linkup.club.entity.ClubQuestion;
import com.core.linkup.club.repository.ClubAnswerRepository;
import com.core.linkup.club.repository.ClubMemberRepository;
import com.core.linkup.club.repository.ClubQuestionRepository;
import com.core.linkup.club.repository.ClubRepository;
import com.core.linkup.club.requset.ClubApplicationRequest;
import com.core.linkup.club.requset.ClubCreateRequest;
import com.core.linkup.club.requset.ClubSearchRequest;
import com.core.linkup.club.requset.ClubUpdateRequest;
import com.core.linkup.club.response.ClubApplicationResponse;
import com.core.linkup.club.response.ClubSearchResponse;
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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClubService {

    private final ClubRepository clubRepository;
    //    private final ClubCustomRepository clubCustomRepository;
    private final MemberRepository memberRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final ClubQuestionRepository clubQuestionRepository;
    private final ClubConverter clubConverter;
    private final ClubAnswerRepository clubAnswerRepository;

    public ClubSearchResponse findClub(Long clubId) {
        return clubRepository.findById(clubId).map(clubConverter::toClubResponse)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_CLUB_ID));
    }

    public Page<ClubSearchResponse> findClubs(Pageable pageable, ClubSearchRequest request) {
        return clubRepository.findSearchClubs(request, pageable)
                .map(clubConverter::toClubResponse);
    }

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

        return clubConverter.toClubResponse(savedClub);
    }

    public ClubSearchResponse updateClub(MemberDetails member, Long clubId, ClubUpdateRequest updateRequest) {
        Long memberId = getMemberId(member);
        Club existingClub = clubRepository.findById(clubId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_CLUB_ID));

        if (!existingClub.getMember().getId().equals(memberId)) {
            throw new BaseException(BaseResponseStatus.INVALID_MEMBER);
        }

        Club updatedClub = clubConverter.updateClubEntity(existingClub, updateRequest, member);
        Club savedClub = clubRepository.save(updatedClub);
        return clubConverter.toClubResponse(savedClub);
    }

    private static Long getMemberId(MemberDetails member) {
        if (member == null) {
            throw new BaseException(BaseResponseStatus.UNREGISTERD_MEMBER);
        }

        Long memberId = member.getId();
        return memberId;
    }

    public void delete(MemberDetails member, Long clubId) {
        clubRepository.deleteById(clubId);
    }

    //소모임 가입
    public ClubApplicationResponse joinClub(Long memberId, Long clubId, ClubApplicationRequest request) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_CLUB_ID));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.UNREGISTERD_MEMBER));

        ClubMember clubMember = clubConverter.toClubMember(club, member, request);
        clubMemberRepository.save(clubMember);

        List<ClubAnswer> answers = new ArrayList<>();
        if (request.getClubAnswers() != null && !request.getClubAnswers().isEmpty()) {
            answers = request.getClubAnswers().stream()
                    .map(answerRequest -> clubConverter.toClubAnswerEntity(answerRequest, club, clubMember))
                    .collect(Collectors.toList());
            clubAnswerRepository.saveAll(answers);
        }

        return clubConverter.toClubApplicationResponse(clubMember, answers);
    }

    public List<ClubApplicationResponse> findClubApplications(MemberDetails member, Long clubId) {
        Long memberId = member != null ? member.getMember().getId() : null;
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_CLUB_ID));

        // 소모임을 생성한 사람인 경우 + 가입한 사람이 소모임 조회
        if (club.getMember().getId().equals(memberId)) {
            return clubMemberRepository.findByClub(club).stream()
                    .map(clubMember -> {
                        List<ClubAnswer> answers = clubAnswerRepository.findByClubMember(clubMember);
                        return clubConverter.toClubApplicationResponse(clubMember, answers);
                    })
                    .collect(Collectors.toList());
        } else {
            return clubMemberRepository.findByClubAndMemberId(club, memberId)
                    .map(clubMember -> {
                        List<ClubAnswer> answers = clubAnswerRepository.findByClubMember(clubMember);
                        List<ClubApplicationResponse> responseList = new ArrayList<>();
                        responseList.add(clubConverter.toClubApplicationResponse(clubMember, answers));
                        return responseList;
                    })
                    .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_CLUB_ID));
        }
    }

    public List<ClubApplicationResponse> findMyApplicationList(MemberDetails member) {
        Long memberId = member.getMember().getId();
        List<ClubMember> clubMembers = clubMemberRepository.findByMemberId(memberId);

        return clubMembers.stream()
                .map(clubMember -> {
                    List<ClubAnswer> clubAnswers = clubAnswerRepository.findByClubMemberId(clubMember.getId());
                    return clubConverter.toClubApplicationResponse(clubMember, clubAnswers);
                })
                .collect(Collectors.toList());
    }
}
