package com.core.linkup.club.clubnotice.converter;

import com.core.linkup.club.clubnotice.request.ClubBoardRequest;
import com.core.linkup.club.clubnotice.response.ClubBoardResponse;
import com.core.linkup.club.club.entity.Club;
import com.core.linkup.club.club.entity.ClubNotice;
import com.core.linkup.common.annotation.Converter;
import com.core.linkup.member.entity.Member;
import com.core.linkup.security.MemberDetails;

@Converter
public class ClubBoardConverter {
    public ClubNotice toClubBoardEntity(ClubBoardRequest request, Club club, Member member) {
        return ClubNotice.builder()
                .club(club)
                .title(request.title())
                .content(request.content())
                .type(request.type())
                .build();
    }

    public ClubBoardResponse toClubBoardResponse(ClubNotice clubNotice, MemberDetails memberDetails) {
        Member member = memberDetails.getMember();

        return ClubBoardResponse.builder()
                .noticeId(clubNotice.getId())
                .title(clubNotice.getTitle())
                .content(clubNotice.getContent())
                .type(clubNotice.getType())
                .clubMemberId(member.getId())
                .clubMemberName(member.getName())
                .clubMemberThumbnail(member.getProfileImage())
                .clubMemberOccupation(member.getOccupation())
                .date(clubNotice.getCreatedAt())
                .build();
    }

    public ClubNotice toUpdateBoardEntity(ClubNotice clubNotice, ClubBoardRequest request, MemberDetails member) {
        return ClubNotice.builder()
                .id(clubNotice.getId())
                .club(clubNotice.getClub())
                .title(request.title())
                .content(request.content())
                .type(request.type())
                .build();
    }
}
