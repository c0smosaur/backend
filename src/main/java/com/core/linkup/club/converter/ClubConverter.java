package com.core.linkup.club.converter;

import com.core.linkup.club.entity.Club;
import com.core.linkup.club.entity.ClubMember;
import com.core.linkup.club.entity.ClubQuestion;
import com.core.linkup.club.requset.ClubApplicationRequest;
import com.core.linkup.club.requset.ClubCreateRequest;
import com.core.linkup.club.requset.ClubQuestionRequest;
import com.core.linkup.club.requset.ClubUpdateRequest;
import com.core.linkup.club.response.ClubApplicationResponse;
import com.core.linkup.club.response.ClubSearchResponse;
import com.core.linkup.common.annotation.Converter;
import com.core.linkup.common.entity.enums.ClubType;
import com.core.linkup.common.exception.BaseException;
import com.core.linkup.common.response.BaseResponseStatus;
import com.core.linkup.member.entity.Member;
import com.core.linkup.security.MemberDetails;

@Converter
public class ClubConverter {

    public ClubSearchResponse toClubResponse(Club club) {
        Member member = club.getMember();

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


    public Club toClubEntity(ClubCreateRequest request, MemberDetails member) {
        ClubType category;
        try {
            category = ClubType.fromKor(String.valueOf(request.clubType()));
        } catch (IllegalArgumentException e) {
            throw new BaseException(BaseResponseStatus.INVALID_REQUEST);
        }
        Club club = Club.builder()
                .category(String.valueOf(category))
                .clubAccessibility(request.clubAccessibility())
                .title(request.title())
                .introduction(request.introduction())
                .recruitCount(request.recruitCount())
                .detailedIntroduction(request.detailedIntroduction())
                .applicationIntroduction(request.applicationIntroduction())
                .clubThumbnail(request.clubThumbnail())
                .member(member.getMember())
                .build();

        return club;
    }

    public ClubQuestion toClubQuestionEntity(ClubQuestionRequest request, Club club) {
        return ClubQuestion.builder()
                .club(club)
                .question(request.getQuestion())
                .qorders(request.getQorders())
                .build();
    }


    public Club updateClubEntity(Club updateClub, ClubUpdateRequest updateRequest,MemberDetails member) {
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
                .member(member.getMember())
                .build();
    }

    //소모임 가입
    public ClubMember toClubMember(Club club, Member member, ClubApplicationRequest request) {
        return new ClubMember(club, member, request.getIntroduction(), false);
    }
    public ClubApplicationResponse toClubApplicationResponse(ClubMember clubMember){
        return ClubApplicationResponse.builder()
                .id(clubMember.getId())  // clubMemberId
                .clubId(clubMember.getClub().getId())
                .memberId(clubMember.getMember().getId())
                .introduction(clubMember.getIntroduction())
                .approval(clubMember.getApproval())
                .build();
    }
}
