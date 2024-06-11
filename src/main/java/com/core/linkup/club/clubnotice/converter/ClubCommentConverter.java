package com.core.linkup.club.clubnotice.converter;

import com.core.linkup.club.clubnotice.request.ClubCommentRequest;
import com.core.linkup.club.clubnotice.response.ClubCommentResponse;
import com.core.linkup.club.clubnotice.entity.ClubComment;
import com.core.linkup.common.annotation.Converter;
import com.core.linkup.member.entity.Member;
import com.core.linkup.security.MemberDetails;

@Converter
public class ClubCommentConverter {
    public ClubComment toClubCommentEntity(ClubCommentRequest request, Long noticeId, Long memberId) {
        return ClubComment.builder()
                .clubNoticeId(noticeId)
                .clubMemberId(memberId)
                .comment(request.comment())
                .build();
    }

    public ClubCommentResponse toClubCommentResponse(ClubComment clubComment, Member member) {
        return ClubCommentResponse.builder()
                .commentId(clubComment.getId())
                .clubNoticeId(clubComment.getClubNoticeId())
                .clubNoticeId(clubComment.getClubNoticeId())
                .comment(clubComment.getComment())
                .clubMemberId(member.getId())
                .clubMemberUsername(member.getUsername())
                .clubMemberThumbnail(member.getProfileImage())
                .clubMemberOccupation(member.getOccupation())
                .build();
    }

}
