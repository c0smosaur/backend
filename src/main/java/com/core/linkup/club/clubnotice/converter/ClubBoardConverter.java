package com.core.linkup.club.clubnotice.converter;

import com.core.linkup.club.clubnotice.request.ClubBoardRequest;
import com.core.linkup.club.clubnotice.response.ClubBoardResponse;
import com.core.linkup.club.club.entity.Club;
import com.core.linkup.club.clubnotice.entity.ClubNotice;
import com.core.linkup.common.annotation.Converter;
import com.core.linkup.member.entity.Member;
import com.core.linkup.security.MemberDetails;

@Converter
public class ClubBoardConverter {
    public ClubNotice toClubBoardEntity(ClubBoardRequest request, Long clubId, Long memberId) { //Member member) {
        return ClubNotice.builder()
                .clubId(clubId)
                .memberId(memberId)
                .title(request.title())
                .content(request.content())
                .type(request.type())
                .build();
    }

    public ClubBoardResponse toClubBoardResponse(ClubNotice clubNotice, MemberDetails memberDetails) {
        Member member = memberDetails.getMember();

        return ClubBoardResponse.builder()
                .id(clubNotice.getId())
                .title(clubNotice.getTitle())
                .content(clubNotice.getContent())
                .type(clubNotice.getType())
                .noticeId(clubNotice.getId())
                .clubMemberId(member.getId())
                .clubMemberName(member.getName())
                .clubMemberThumbnail(member.getProfileImage())
                .clubMemberOccupation(member.getOccupation())
                .date(clubNotice.getCreatedAt())
                .build();
    }

    public ClubNotice toUpdateBoardEntity(ClubNotice clubNotice, ClubBoardRequest request, Long memberId, Long clubId) {
        return ClubNotice.builder()
                .id(clubNotice.getId())
                .clubId(clubId)
                .memberId(memberId)
                .title(request.title())
                .content(request.content())
                .type(request.type())
                .build();
    }

}
