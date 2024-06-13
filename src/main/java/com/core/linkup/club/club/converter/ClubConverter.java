package com.core.linkup.club.club.converter;

import com.core.linkup.club.club.entity.*;
import com.core.linkup.club.club.request.*;
import com.core.linkup.club.club.response.*;
import com.core.linkup.club.clubmeeting.entity.ClubMeeting;
import com.core.linkup.club.clubmeeting.response.ClubMeetingResponse;
import com.core.linkup.common.annotation.Converter;
import com.core.linkup.common.entity.enums.ClubType;
import com.core.linkup.common.exception.BaseException;
import com.core.linkup.common.response.BaseResponseStatus;
import com.core.linkup.member.entity.Member;
import com.core.linkup.security.MemberDetails;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Converter
public class ClubConverter {

    public ClubSearchResponse toClubResponse(Club club, Member member) {
        return ClubSearchResponse.builder()
                .id(club.getId())
                .title(club.getTitle())
                .detailIntroduction(club.getDetailedIntroduction())
                .clubType(club.getCategory().getClubCategoryName())
                .recruitCount(club.getRecruitCount())
                .memberId(member.getId()) //소모임을 생성함 멤버의 아이디
                .memberName(member.getName())
                .memberUsername(member.getUsername())
                .profileImage(member.getProfileImage())
                .clubThumbnail(club.getClubThumbnail())
                .clubAccessibility(club.getClubAccessibility())
                .build();
    }

    public ClubSearchResponse toClubResponse(Club club, Member clubHost, boolean liked) {
        return ClubSearchResponse.builder()
                .id(club.getId())
                .title(club.getTitle())
                .introduction(club.getIntroduction())
                .detailIntroduction(club.getDetailedIntroduction())
                .clubType(club.getCategory().getClubCategoryName())
                .recruitCount(club.getRecruitCount())
                .memberId(club.getMemberId()) //소모임을 생성함 멤버의 아이디
                .memberName(clubHost.getName())
                .memberUsername(clubHost.getUsername())
                .profileImage(clubHost.getProfileImage())
                .clubAccessibility(club.getClubAccessibility())
                .clubThumbnail(club.getClubThumbnail())
                .liked(liked)
                .build();
    }

    public ClubSearchResponse toClubResponse(Club club, Member member, List<ClubMember> clubMembers,
                                             List<ClubMeeting> clubMeetings, Map<Long, Member> memberMap,
                                             Boolean liked) {
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

        List<ClubMeetingResponse> clubMeetingResponses = clubMeetings.stream()
                .map(clubMeeting -> ClubMeetingResponse.builder()
                        .id(clubMeeting.getId())
                        .title(clubMeeting.getTitle())
                        .date(clubMeeting.getDate())
                        .maxCapacity(clubMeeting.getMaxCapacity())
                        .fee(clubMeeting.getFee())
                        .build())
                .collect(Collectors.toList());

        return ClubSearchResponse.builder()
                .id(club.getId())
                .title(club.getTitle())
                .introduction(club.getIntroduction())
                .detailIntroduction(club.getDetailedIntroduction())
                .clubType(club.getCategory().getClubCategoryName())
                .recruitCount(club.getRecruitCount())
                .memberId(member.getId())
                .memberName(member.getName())
                .memberUsername(member.getUsername())
                .profileImage(member.getProfileImage())
                .clubAccessibility(club.getClubAccessibility())
                .clubThumbnail(club.getClubThumbnail())
                .clubMembers(clubMemberResponses)
                .clubMeetings(clubMeetingResponses)
                .liked(liked)
                .build();
    }

    public Club toClubEntity(ClubCreateRequest request, MemberDetails member) {
        ClubType category;
        try {
            category = ClubType.fromKor(request.clubType());
        } catch (IllegalArgumentException e) {
            throw new BaseException(BaseResponseStatus.INVALID_REQUEST);
        }
        return Club.builder()
                .category(category)
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
        ClubType category = ClubType.fromKor(updateRequest.clubType());
        return Club.builder()
                .id(updateClub.getId())
                .category(category)
                .title(updateRequest.title())
                .introduction(updateRequest.introduction())
                .recruitCount(updateRequest.recruitCount())
                .detailedIntroduction(updateRequest.detailedIntroduction())
                .applicationIntroduction(updateRequest.applicationIntroduction())
                .clubAccessibility(updateRequest.clubAccessibility())
                .clubThumbnail(updateRequest.clubThumbnail())
                .memberId(updateClub.getMemberId())
                .build();
    }

    public ClubMember toClubMember(Club club, Member member, ClubApplicationRequest request) {
        return ClubMember.builder()
                .clubId(club.getId())
                .memberId(member.getId())
                .introduction(request.getIntroduction())
                .approval(false)
                .build();
    }

    public ClubMember updateClubMember(ClubMember clubMember){
        return clubMember.toBuilder()
                .approval(true)
                .build();
    }

    public ClubApplicationResponse toClubApplicationResponse(ClubMember clubMember, List<ClubAnswer> clubAnswers,
                                                              Club club, boolean isLiked) {
        ClubType clubType = club.getCategory();
        List<ClubAnswerResponse> answerResponses = clubAnswers.stream()
                .map(this::toClubAnswerResponse)
                .collect(Collectors.toList());

        return ClubApplicationResponse.builder()
                .id(clubMember.getId())  // clubMemberId
                .clubId(clubMember.getClubId())
                .memberId(clubMember.getMemberId())
                .approval(clubMember.getApproval())
                .id(club.getId())
                .clubCategory(clubType.getClubCategoryName())
                .clubThumbnail(club.getClubThumbnail())
                .clubTitle(club.getTitle())
                .clubIntroduction(club.getIntroduction())
                .clubRecruitCount(club.getRecruitCount())
                .clubAnswer(answerResponses)
                .liked(isLiked)
                .build();
    }

    public ClubApplicationResponse toClubApplicationResponse(ClubMember clubMember, List<ClubAnswer> clubAnswers, Club club) {
        ClubType clubType = club.getCategory();
        List<ClubAnswerResponse> answerResponses = clubAnswers.stream()
                .map(this::toClubAnswerResponse)
                .collect(Collectors.toList());

        return ClubApplicationResponse.builder()
                .id(clubMember.getId())  // clubMemberId
                .clubId(clubMember.getClubId())
                .memberId(clubMember.getMemberId())
                .approval(clubMember.getApproval())
                .id(club.getId())
                .clubCategory(clubType.getClubCategoryName())
                .clubThumbnail(club.getClubThumbnail())
                .clubTitle(club.getTitle())
                .clubIntroduction(club.getIntroduction())
                .clubRecruitCount(club.getRecruitCount())
                .clubAnswer(answerResponses)
                .build();
    }

    public ClubAnswer toClubAnswerEntity(ClubAnswerRequest request, Long memberId, Long clubId, Long clubMemberId) {
        return ClubAnswer.builder()
                .clubId(clubId)
                .memberId(memberId)
                .clubMemberId(clubMemberId)
                .answer(request.getAnswer())
                .qorders(request.getQorders())
                .build();
    }

    private ClubAnswerResponse toClubAnswerResponse(ClubAnswer clubAnswer) {
        return ClubAnswerResponse.builder()
                .id(clubAnswer.getId())
                .answer(clubAnswer.getAnswer())
                .qorders(clubAnswer.getQorders())
                .build();
    }

    public ClubLike toLikeEntity(Long memberId, Long clubId) {
        return ClubLike.builder()
                .memberId(memberId)
                .clubId(clubId)
                .build();
    }

    public ClubLikeResponse toLikeResponse(ClubLike clubLike, Club club, ClubMeeting clubMeeting) {
        return ClubLikeResponse.builder()
                .id(clubLike.getId())
                .memberId(clubLike.getMemberId())
                .clubId(clubLike.getClubId())
                .liked(true)
                .clubThumbnail(club.getClubThumbnail())
                .clubName(club.getTitle())
                .clubIntroduction(club.getIntroduction())
                .clubMemberCount(club.getRecruitCount())
                .clubMeetingDate(clubMeeting != null ? LocalDate.from(clubMeeting.getDate()) : null)
                .build();
    }

    public ClubSearchResponse toManagingApplication(Club club, Member member) {
        return ClubSearchResponse.builder()
                .id(club.getId())
                .title(club.getTitle())
                .detailIntroduction(club.getDetailedIntroduction())
                .clubType(club.getCategory().getClubCategoryName())
                .recruitCount(club.getRecruitCount())
                .memberId(member.getId()) //소모임을 생성함 멤버의 아이디
                .memberName(member.getName())
                .profileImage(member.getProfileImage())
                .clubThumbnail(club.getClubThumbnail())
                .build();
    }

    public ClubQuestionResponse toQuestionResponse(List<ClubQuestion> questions, Club club) {
        List<String> questionList = questions.stream()
                .map(ClubQuestion::getQuestion)
                .toList();

        return ClubQuestionResponse.builder()
                .clubId(club.getId())
                .clubTitle(club.getTitle())
                .clubIntroduction(club.getIntroduction())
                .clubDetailIntroduction(club.getDetailedIntroduction())
                .question(questionList)
                .build();
    }

    public ClubAnswerListResponse toAnswerResponse(List<ClubAnswer> answers, Club club) {
        List<String> answerList = answers.stream()
                .map(ClubAnswer::getAnswer)
                .toList();

        return ClubAnswerListResponse.builder()
                .clubId(club.getId())
                .clubTitle(club.getTitle())
                .clubIntroduction(club.getIntroduction())
                .clubDetailIntroduction(club.getDetailedIntroduction())
                .answer(answerList)
                .qorders(answers.size())
                .build();
    }

    public ClubSearchApplicationResponse toClubSearchApplicationResponse(
            ClubMember clubMember,Member member, Club club, boolean isLiked) {
        ClubType clubType = club.getCategory();

        return ClubSearchApplicationResponse.builder()
                .clubId(club.getId())
                .clubId(clubMember.getClubId())
                .clubTitle(club.getTitle())
                .clubCategory(clubType.getClubCategoryName())
                .clubIntroduction(club.getIntroduction())
                .clubDetailIntroduction(club.getDetailedIntroduction())
                .clubRecruitCount(club.getRecruitCount())
                .clubThumbnail(club.getClubThumbnail())
                .memberId(clubMember.getMemberId())
                .memberName(member.getName())
                .memberProfileImage(member.getProfileImage())
                .approval(clubMember.getApproval())
                .liked(isLiked)
                .build();
    }
}
