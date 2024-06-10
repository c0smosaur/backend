package com.core.linkup.club.clubnotice.converter;

import com.core.linkup.club.clubnotice.entity.ClubNotice;
import com.core.linkup.club.clubnotice.request.ClubNoticeRequest;
import com.core.linkup.club.clubnotice.response.ClubCommentResponse;
import com.core.linkup.club.clubnotice.response.ClubNoticeResponse;
import com.core.linkup.common.annotation.Converter;
import com.core.linkup.member.entity.Member;

import java.util.List;

@Converter
public class ClubNoticeConverter {

    public ClubNotice toClubNoticeEntity(ClubNoticeRequest request, Long clubId, Long memberId) {
        return ClubNotice.builder()
                .clubId(clubId)
                .memberId(memberId)
                .title(request.title())
                .content(request.content())
                .type(request.type())
                .build();
    }

    public ClubNoticeResponse toClubNoticeResponse(ClubNotice clubNotice, Member member) {
        return ClubNoticeResponse.builder()
                .id(clubNotice.getId())
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

    public ClubNoticeResponse toClubNoticeResponse(ClubNotice clubNotice, List<ClubCommentResponse> comments, Member member) {
        return ClubNoticeResponse.builder()
                .id(clubNotice.getId())
                .title(clubNotice.getTitle())
                .content(clubNotice.getContent())
                .type(clubNotice.getType())
                .clubOwnerId(member.getId())
                .clubOwnerName(member.getName())
                .clubOwnerThumbnail(member.getProfileImage())
                .clubOwnerOccupation(member.getOccupation())
                .date(clubNotice.getCreatedAt())
                .comments(comments)
                .build();
    }

    public ClubNotice toUpdateNoticeEntity(ClubNotice clubNotice, ClubNoticeRequest request, Long memberId) {
        return ClubNotice.builder()
                .id(clubNotice.getId())
                .clubId(clubNotice.getClubId())
                .memberId(memberId)
                .title(request.title())
                .content(request.content())
                .type(request.type())
                .build();
    }
}
