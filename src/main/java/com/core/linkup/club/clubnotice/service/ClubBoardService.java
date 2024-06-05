package com.core.linkup.club.clubnotice.service;

import com.core.linkup.club.clubnotice.converter.ClubBoardConverter;
import com.core.linkup.club.clubnotice.repository.ClubNoticeRepository;
import com.core.linkup.club.clubnotice.request.ClubBoardRequest;
import com.core.linkup.club.clubnotice.response.ClubBoardResponse;
import com.core.linkup.club.club.entity.Club;
import com.core.linkup.club.club.entity.ClubNotice;
import com.core.linkup.club.club.repository.ClubRepository;
import com.core.linkup.common.exception.BaseException;
import com.core.linkup.common.response.BaseResponseStatus;
import com.core.linkup.member.entity.Member;
import com.core.linkup.security.MemberDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClubBoardService {

    private final ClubRepository clubRepository;
    private final ClubNoticeRepository clubNoticeRepository;
    private final ClubBoardConverter clubBoardConverter;

    // 게시판 작성
    public ClubBoardResponse createBoard(MemberDetails memberDetails, Long clubId, ClubBoardRequest request) {
        Long memberId = memberDetails.getId();
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_CLUB_ID));

        Member member = memberDetails.getMember();

        ClubNotice clubNotice = clubBoardConverter.toClubBoardEntity(request, club, member);
        clubNoticeRepository.save(clubNotice);

        return clubBoardConverter.toClubBoardResponse(clubNotice, memberDetails);
    }

    //게시판 조회
    public List<ClubBoardResponse> findAllBoard(Long clubId, MemberDetails memberDetails) {
        List<ClubNotice> clubNoticeList = clubNoticeRepository.findByClubId(clubId);

        return clubNoticeList.stream()
                .map((ClubNotice clubNotice) -> clubBoardConverter.toClubBoardResponse(clubNotice, memberDetails))
                .collect(Collectors.toList());
    }

    public ClubBoardResponse findBoard(Long clubId, Long noticeId, MemberDetails memberDetails) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_CLUB_ID));
        ClubNotice clubNotice = clubNoticeRepository.findById(noticeId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_NOTICE));

        return clubBoardConverter.toClubBoardResponse(clubNotice, memberDetails);
    }

    //수정
    public ClubBoardResponse updateBoard(MemberDetails member, Long clubId, Long noticeId, ClubBoardRequest request) {
        Long memberId = member.getId();
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_CLUB_ID));

        // 공지사항 존재 여부 확인
        ClubNotice clubNotice = clubNoticeRepository.findById(noticeId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_NOTICE));

        ClubNotice updateNotice = clubBoardConverter.toUpdateBoardEntity(clubNotice, request, member);
        ClubNotice save =  clubNoticeRepository.save(updateNotice);

        return clubBoardConverter.toClubBoardResponse(save, member);
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
