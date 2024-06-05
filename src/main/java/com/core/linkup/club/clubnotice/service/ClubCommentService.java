package com.core.linkup.club.clubnotice.service;

import com.core.linkup.club.clubnotice.converter.ClubCommentConverter;
import com.core.linkup.club.clubnotice.repository.ClubCommentRepository;
import com.core.linkup.club.clubnotice.repository.ClubNoticeRepository;
import com.core.linkup.club.clubnotice.request.ClubCommentRequest;
import com.core.linkup.club.clubnotice.response.ClubCommentResponse;
import com.core.linkup.club.entity.Club;
import com.core.linkup.club.entity.ClubComment;
import com.core.linkup.club.repository.ClubRepository;
import com.core.linkup.common.exception.BaseException;
import com.core.linkup.common.response.BaseResponseStatus;
import com.core.linkup.member.entity.Member;
import com.core.linkup.member.repository.MemberRepository;
import com.core.linkup.security.MemberDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClubCommentService {

    private final ClubCommentRepository clubCommentRepository;
    private final ClubCommentConverter clubCommentConverter;
    private final ClubRepository clubRepository;
    private final ClubNoticeRepository clubNoticeRepository;
    private final MemberRepository memberRepository;

    public ClubCommentResponse createComment(MemberDetails memberDetails, Long clubId, Long noticeId, ClubCommentRequest request) {

        if (!clubNoticeRepository.existsById(noticeId)) {
            throw new IllegalArgumentException("Invalid club notice ID: " + noticeId);
        }

        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_CLUB_ID));
        Member member = memberRepository.findById(memberDetails.getId())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_MEMBER));
//        ClubNotice clubNotice = clubNoticeRepository.findById(noticeId)
//                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_NOTICE_ID));
//
//        if (clubNotice.getType() != NotificationType.BOARD) {
//            throw new BaseException(BaseResponseStatus.INVALID_NOTICE_ID);
//        }

        ClubComment clubComment = clubCommentConverter.toClubCommentEntity(request, noticeId, member.getId());
        ClubComment savedComment = clubCommentRepository.save(clubComment);

        return clubCommentConverter.toClubCommentResponse(savedComment, memberDetails);
    }

    public ClubCommentResponse findComment(MemberDetails memberDetails, Long clubId, Long noticeId, Long commentId) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_CLUB_ID));
        ClubComment comment = clubCommentRepository.findById(commentId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_COMMENT_ID));

        validateClubAndNoticeId(clubId, noticeId, comment);

        Member member = memberRepository.findById(memberDetails.getId())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_MEMBER));

        return clubCommentConverter.toClubCommentResponse(comment, memberDetails);
    }

    public ClubCommentResponse updateComment(MemberDetails memberDetails, Long clubId, Long noticeId, Long commentId, ClubCommentRequest request) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_CLUB_ID));
        ClubComment comment = clubCommentRepository.findById(commentId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_COMMENT_ID));

        validateClubAndNoticeId(clubId, noticeId, comment);

        Member member = memberRepository.findById(memberDetails.getId())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_MEMBER));

        if (!comment.getClubMemberId().equals(member.getId())) {
            throw new BaseException(BaseResponseStatus.INVALID_MEMBER);
        }

        ClubComment updatedComment = clubCommentConverter.toClubUpdateCommentEntity(request, noticeId, comment);
        ClubComment savedComment = clubCommentRepository.save(updatedComment);

        return clubCommentConverter.toClubCommentResponse(savedComment, memberDetails);
    }

    public void deleteComment(MemberDetails memberDetails, Long clubId, Long noticeId, Long commentId) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_CLUB_ID));
        ClubComment comment = clubCommentRepository.findById(commentId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_COMMENT_ID));

        validateClubAndNoticeId(clubId, noticeId, comment);

        Member member = memberRepository.findById(memberDetails.getId())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_MEMBER));

        if (!comment.getClubMemberId().equals(member.getId())) {
            throw new BaseException(BaseResponseStatus.INVALID_REQUEST);
        }

        clubCommentRepository.delete(comment);
    }

    private void validateClubAndNoticeId(Long clubId, Long noticeId, ClubComment comment) {
        if (!clubId.equals(comment.getId()) || !noticeId.equals(comment.getClubNoticeId())) {
            throw new BaseException(BaseResponseStatus.INVALID_COMMENT_ID);
        }
    }
}
