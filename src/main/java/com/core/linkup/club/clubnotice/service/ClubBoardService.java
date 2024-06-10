package com.core.linkup.club.clubnotice.service;

import com.core.linkup.club.club.entity.Club;
import com.core.linkup.club.club.repository.ClubMemberRepository;
import com.core.linkup.club.club.repository.ClubRepository;
import com.core.linkup.club.clubnotice.converter.ClubBoardConverter;
import com.core.linkup.club.clubnotice.entity.ClubNotice;
import com.core.linkup.club.clubnotice.repository.ClubNoticeRepository;
import com.core.linkup.club.clubnotice.request.ClubBoardRequest;
import com.core.linkup.club.clubnotice.response.ClubBoardResponse;
import com.core.linkup.club.clubnotice.response.ClubCommentResponse;
import com.core.linkup.common.exception.BaseException;
import com.core.linkup.common.response.BaseResponseStatus;
import com.core.linkup.member.entity.Member;
import com.core.linkup.member.repository.MemberRepository;
import com.core.linkup.security.MemberDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClubBoardService {

    private final ClubRepository clubRepository;
    private final ClubNoticeRepository clubNoticeRepository;
    private final ClubBoardConverter clubBoardConverter;
    private final ClubCommentService clubCommentService;
    private final ClubMemberRepository clubMemberRepository;
    private final MemberRepository memberRepository;

    // 게시판 작성
    public ClubBoardResponse createBoard(Member member, Long clubId, ClubBoardRequest request) {
        Long memberId = member.getId();

        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_CLUB_ID));

        boolean isMember = clubMemberRepository.existsByClubIdAndMemberId(clubId, memberId);
        if (!isMember && !club.getMemberId().equals(memberId)) {
            throw new BaseException(BaseResponseStatus.INVALID_CLUB_MEMBER);
        }

        ClubNotice clubNotice = clubBoardConverter.toClubBoardEntity(request, clubId, memberId);
        clubNoticeRepository.save(clubNotice);

        return clubBoardConverter.toClubBoardResponse(clubNotice, member);
    }

    //게시판 조회
    public Page<ClubBoardResponse> findAllBoard(Long clubId, MemberDetails memberDetails, Pageable pageable) {

        Page<ClubNotice> clubNotices = clubNoticeRepository.findAllNotice(clubId, pageable);
        List<Member> members = memberRepository.findAllById(clubNotices.stream()
                .map(ClubNotice::getMemberId)
                .collect(Collectors.toList()));

        Map<Long, Member> memberMap = members.stream()
                .collect(Collectors.toMap(Member::getId, Function.identity()));

        List<ClubBoardResponse> clubBoardResponseList = clubNotices.stream()
                .map(clubNotice -> {
                    Member member = memberMap.get(clubNotice.getMemberId());
                    return clubBoardConverter.toClubBoardResponse(clubNotice, member);
                })
                .collect(Collectors.toList());

        return new PageImpl<>(clubBoardResponseList, pageable, clubNotices.getTotalElements());
    }

    // 게시글 개별 조회
    public ClubBoardResponse findBoard(Long clubId, Long noticeId, Member member) {
        checkClubId(clubId);
        ClubNotice notice = clubNoticeRepository.findNotice(clubId, noticeId);

        List<ClubCommentResponse> comments = clubCommentService.findComments(member, clubId, noticeId);

        return clubBoardConverter.toClubBoardResponse(notice, comments, member);
    }

    private Club checkClubId(Long clubId) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_CLUB_ID));
        return club;
    }

    //수정
    public ClubBoardResponse updateBoard(Member member, Long clubId, Long noticeId, ClubBoardRequest request) {
        Long memberId = member.getId();
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_CLUB_ID));

        ClubNotice clubNotice = clubNoticeRepository.findById(noticeId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_NOTICE));

        clubBoardConverter.toUpdateBoardEntity(clubNotice, request, memberId, clubId);
        ClubNotice savedNotice = clubNoticeRepository.save(clubNotice);

        return clubBoardConverter.toClubBoardResponse(savedNotice, member);
    }

    //삭제
    public void deleteBoard(MemberDetails member, Long clubId, Long noticeId) {
        Long memberId = member.getId();
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_CLUB_ID));

        ClubNotice clubNotice = clubNoticeRepository.findById(noticeId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_NOTICE));

        clubNoticeRepository.delete(clubNotice);
    }
}
