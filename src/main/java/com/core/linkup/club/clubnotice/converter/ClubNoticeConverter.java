package com.core.linkup.club.clubnotice.converter;

import com.core.linkup.club.clubnotice.request.ClubNoticeRequest;
import com.core.linkup.club.clubnotice.response.ClubNoticeResponse;
import com.core.linkup.club.club.entity.Club;
import com.core.linkup.club.club.entity.ClubNotice;
import com.core.linkup.common.annotation.Converter;
import com.core.linkup.member.entity.Member;
import com.core.linkup.security.MemberDetails;

@Converter
public class ClubNoticeConverter {

    public ClubNotice toClubNoticeEntity(ClubNoticeRequest request, Club club) {
        return ClubNotice.builder()
                .club(club)
                .title(request.title())
                .content(request.content())
                .type(request.type())
                .build();
    }

    public ClubNoticeResponse toClubNoticeResponse(ClubNotice clubNotice) {
        Club club = clubNotice.getClub();
        Member member = club.getMember();

        return ClubNoticeResponse.builder()
                .noticeId(clubNotice.getId())
                .title(clubNotice.getTitle())
                .content(clubNotice.getContent())
                .type(clubNotice.getType())
                .clubOwnerId(member.getId())
                .clubOwnerName(member.getName())
                .clubOwnerThumbnail(member.getProfileImage())
                .clubOwnerOccupation(member.getOccupation())
                .date(clubNotice.getCreatedAt())
                .build();
    }

    public ClubNotice toUpdateNoticeEntity(ClubNotice clubNotice, ClubNoticeRequest request, MemberDetails member) {
        return ClubNotice.builder()
                .id(clubNotice.getId())
                .club(clubNotice.getClub())
                .title(request.title())
                .content(request.content())
                .type(request.type())
                .build();
    }
}
