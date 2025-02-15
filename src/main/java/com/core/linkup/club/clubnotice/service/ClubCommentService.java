package com.core.linkup.club.clubnotice.service;

import com.core.linkup.club.club.entity.ClubMember;
import com.core.linkup.club.club.repository.ClubMemberRepository;
import com.core.linkup.club.clubnotice.converter.ClubCommentConverter;
import com.core.linkup.club.clubnotice.repository.ClubCommentRepository;
import com.core.linkup.club.clubnotice.repository.ClubNoticeRepository;
import com.core.linkup.club.clubnotice.request.ClubCommentRequest;
import com.core.linkup.club.clubnotice.response.ClubCommentResponse;
import com.core.linkup.club.club.entity.Club;
import com.core.linkup.club.clubnotice.entity.ClubComment;
import com.core.linkup.club.club.repository.ClubRepository;
import com.core.linkup.common.exception.BaseException;
import com.core.linkup.common.response.BaseResponseStatus;
import com.core.linkup.member.entity.Member;
import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClubCommentService {

    private final ClubCommentRepository clubCommentRepository;
    private final ClubCommentConverter clubCommentConverter;
    private final ClubRepository clubRepository;
    private final ClubNoticeRepository clubNoticeRepository;
    private final ClubMemberRepository clubMemberRepository;

    public ClubCommentResponse createComment(Member member, Long clubId, Long noticeId, ClubCommentRequest request) {

        if (!clubNoticeRepository.existsById(noticeId)) {
            throw new IllegalArgumentException("Invalid club notice ID: " + noticeId);
        }

        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_CLUB_ID));

        ClubComment clubComment = clubCommentConverter.toClubCommentEntity(request, noticeId, member.getId());

        ClubMember clubMember = clubMemberRepository.findByClubIdAndMemberId(clubId, member.getId())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_CLUB_MEMBER));
        ClubComment savedComment = clubCommentRepository.save(clubComment);

        return clubCommentConverter.toClubCommentResponse(savedComment, clubMember, member);
    }

    public List<ClubCommentResponse> findComments(Member member, Long clubId, Long noticeId) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_CLUB_ID));
        List<Tuple> tuples = clubCommentRepository.findAllCommentsAndWriters(noticeId);

        return getCommentsAndWritersFromTuples(tuples);
    }

    private List<ClubCommentResponse> getCommentsAndWritersFromTuples(List<Tuple> tuples){
        if (tuples == null || tuples.size() == 0) {
            return new ArrayList<>();
        }
        return tuples.stream().map(
                tuple -> {
                    ClubComment comment = tuple.get(0, ClubComment.class);
                    ClubMember commenter = tuple.get(1, ClubMember.class);
                    Member member = tuple.get(2, Member.class);
                    return clubCommentConverter.toClubCommentResponse(comment, commenter ,member);
                }).collect(Collectors.toList());
    }

    public void deleteComment(Member member, Long clubId, Long noticeId, Long commentId) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_CLUB_ID));

        ClubComment comment = clubCommentRepository.findById(commentId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_COMMENT_ID));

        if (!comment.getClubMemberId().equals(member.getId())) {
            throw new BaseException(BaseResponseStatus.INVALID_REQUEST);
        }

        clubCommentRepository.delete(comment);
    }
}
