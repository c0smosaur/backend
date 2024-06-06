package com.core.linkup.club.club.converter;

import com.core.linkup.club.club.entity.Club;
import com.core.linkup.club.club.entity.ClubAnswer;
import com.core.linkup.club.club.entity.ClubMember;
import com.core.linkup.club.club.entity.ClubQuestion;
import com.core.linkup.club.club.request.*;
import com.core.linkup.club.club.response.ClubAnswerResponse;
import com.core.linkup.club.club.response.ClubApplicationResponse;
import com.core.linkup.club.club.response.ClubMemberResponse;
import com.core.linkup.club.club.response.ClubSearchResponse;
import com.core.linkup.common.annotation.Converter;
import com.core.linkup.common.entity.enums.ClubType;
import com.core.linkup.common.exception.BaseException;
import com.core.linkup.common.response.BaseResponseStatus;
import com.core.linkup.member.entity.Member;
import com.core.linkup.security.MemberDetails;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Converter
public class ClubConverter {

    public ClubSearchResponse toClubResponses(Club club, Member member) {
        return ClubSearchResponse.builder()
                .id(club.getId())
                .title(club.getTitle())
                .introduction(club.getIntroduction())
                .clubType(club.getCategory())
                .recruitCount(club.getRecruitCount())
                .memberId(member.getId()) //소모임을 생성함 멤버의 아이디
                .memberName(member.getName())
                .profileImage(member.getProfileImage())
                .build();
    }

    public ClubSearchResponse toClubResponse(Club club, Member member, List<ClubMember> clubMembers, Map<Long, Member> memberMap) {
        List<ClubMemberResponse> clubMemberResponses = clubMembers.stream()
                .map(clubMember -> {
                    Member memberDetail = memberMap.get(clubMember.getMemberId());
                    return ClubMemberResponse.builder()
                            .clubId(clubMember.getClubId())
                            .memberId(clubMember.getMemberId())
                            .memberName(memberDetail != null ? memberDetail.getName() : null)
                            .profileImage(memberDetail != null ? memberDetail.getProfileImage() : null)
                            .introduction(clubMember.getIntroduction())
                            .build();
                })
                .collect(Collectors.toList());

        return ClubSearchResponse.builder()
                .id(club.getId())
                .title(club.getTitle())
                .introduction(club.getIntroduction())
                .clubType(club.getCategory())
                .recruitCount(club.getRecruitCount())
                .memberId(member.getId())
                .memberName(member.getName())
                .profileImage(member.getProfileImage())
                .clubMembers(clubMemberResponses)
                .build();
    }

    public Club toClubEntity(ClubCreateRequest request, MemberDetails member) {
        ClubType category;
        try {
            category = ClubType.fromKor(String.valueOf(request.clubType()));
        } catch (IllegalArgumentException e) {
            throw new BaseException(BaseResponseStatus.INVALID_REQUEST);
        }
        return Club.builder()
                .category(String.valueOf(category))
                .clubAccessibility(request.clubAccessibility())
                .title(request.title())
                .introduction(request.introduction())
                .recruitCount(request.recruitCount())
                .detailedIntroduction(request.detailedIntroduction())
                .applicationIntroduction(request.applicationIntroduction())
                .clubThumbnail(request.clubThumbnail())
                .memberId(member.getMember().getId())
                .build();
    }

    public ClubQuestion toClubQuestionEntity(ClubQuestionRequest request, Club club) {
        return ClubQuestion.builder()
                .clubId(club.getId())
                .question(request.getQuestion())
                .qorders(request.getQorders())
                .build();
    }


    public Club updateClubEntity(Club updateClub, ClubUpdateRequest updateRequest, MemberDetails member) {
        ClubType category = ClubType.fromKor(String.valueOf(updateRequest.clubType()));
        return Club.builder()
                .id(updateClub.getId())
                .category(String.valueOf(category))
                .title(updateRequest.title())
                .introduction(updateRequest.introduction())
                .recruitCount(updateRequest.recruitCount())
                .detailedIntroduction(updateRequest.detailedIntroduction())
                .applicationIntroduction(updateRequest.applicationIntroduction())
                .clubThumbnail(updateRequest.clubThumbnail())
                .memberId(updateClub.getMemberId())
                .build();
    }

    public ClubMember toClubMember(Club club, Member member, ClubApplicationRequest request) {
        return new ClubMember(club.getId(), member.getId(), request.getIntroduction(), false);
    }

    public ClubApplicationResponse toClubApplicationResponse(ClubMember clubMember, List<ClubAnswer> clubAnswers) {
        List<ClubAnswerResponse> answerResponses = clubAnswers.stream()
                .map(this::toClubAnswerResponse)
                .collect(Collectors.toList());

        return ClubApplicationResponse.builder()
                .id(clubMember.getId())  // clubMemberId
                .clubId(clubMember.getClubId())
                .memberId(clubMember.getMemberId())
                .introduction(clubMember.getIntroduction())
                .approval(clubMember.getApproval())
                .clubAnswer(answerResponses)
                .build();
    }

    public ClubAnswer toClubAnswerEntity(ClubAnswerRequest request, ClubMember clubMember) {
        return new ClubAnswer(clubMember.getClubId(), clubMember.getId(), request.getAnswer(), request.getQorders());
    }

    private ClubAnswerResponse toClubAnswerResponse(ClubAnswer clubAnswer) {
        return ClubAnswerResponse.builder()
                .id(clubAnswer.getId())
                .answer(clubAnswer.getAnswer())
                .qorders(clubAnswer.getQorders())
                .build();
    }
}
