package com.core.linkup.club.clubnotice.converter;

import com.core.linkup.club.clubnotice.entity.ClubNotice;
import com.core.linkup.club.clubnotice.request.ClubBoardRequest;
import com.core.linkup.club.clubnotice.response.ClubBoardResponse;
import com.core.linkup.club.clubnotice.response.ClubCommentResponse;
import com.core.linkup.common.annotation.Converter;
import com.core.linkup.member.entity.Member;

import java.util.List;

@Converter
public class ClubBoardConverter {
    public ClubNotice toClubBoardEntity(ClubBoardRequest request, Long clubId, Long memberId) {
        return ClubNotice.builder()
                .clubId(clubId)
                .memberId(memberId)
                .title(request.title())
                .content(request.content())
                .type(request.type())
                .build();
    }

    public ClubBoardResponse toClubBoardResponse(ClubNotice clubNotice, Member member) {
        return ClubBoardResponse.builder()
                .id(clubNotice.getId())
                .title(clubNotice.getTitle())
                .content(clubNotice.getContent())
                .type(clubNotice.getType())
                .clubMemberId(member.getId())
                .clubMemberName(member.getName())
                .clubUsername(member.getUsername())
                .clubMemberThumbnail(member.getProfileImage())
                .clubMemberOccupation(member.getOccupation())
                .date(clubNotice.getCreatedAt())
                .build();
    }

    public ClubBoardResponse toClubBoardResponse(
            ClubNotice clubNotice, List<ClubCommentResponse> comments, Member member) {
        return ClubBoardResponse.builder()
                .id(clubNotice.getId())
                .title(clubNotice.getTitle())
                .content(clubNotice.getContent())
                .type(clubNotice.getType())
                .clubMemberId(member.getId())
                .clubMemberName(member.getName())
                .clubUsername(member.getUsername())
                .clubMemberThumbnail(member.getProfileImage())
                .clubMemberOccupation(member.getOccupation())
                .date(clubNotice.getCreatedAt())
                .comments(comments)
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
