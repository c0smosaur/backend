package com.core.linkup.club.clubnotice.service;

import com.core.linkup.club.clubnotice.converter.ClubNoticeConverter;
import com.core.linkup.club.clubnotice.repository.ClubNoticeRepository;
import com.core.linkup.club.clubnotice.request.ClubNoticeRequest;
import com.core.linkup.club.clubnotice.response.ClubNoticeResponse;
import com.core.linkup.club.entity.Club;
import com.core.linkup.club.entity.ClubNotice;
import com.core.linkup.club.repository.ClubRepository;
import com.core.linkup.common.exception.BaseException;
import com.core.linkup.common.response.BaseResponseStatus;
import com.core.linkup.security.MemberDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClubNoticeService {

    private final ClubRepository clubRepository;
    private final ClubNoticeRepository clubNoticeRepository;
    private final ClubNoticeConverter clubNoticeConverter;


    // 소모임 공지 작성
    public ClubNoticeResponse createMeeting(MemberDetails member, Long clubId, ClubNoticeRequest request) {
        Long memberId = member.getId();
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_CLUB_ID));

        // 소모임 생성자인지 확인
        if (!club.getMember().getId().equals(memberId)) {
            throw new BaseException(BaseResponseStatus.INVALID_CLUB_OWNER);
        }

        ClubNotice clubNotice = clubNoticeConverter.toClubNoticeEntity(request, club);
        clubNoticeRepository.save(clubNotice);

        return clubNoticeConverter.toClubNoticeResponse(clubNotice);
    }

    //소모임 전체 조회
    public List<ClubNoticeResponse> findAllNotice(Long clubId) {
//        Long memberId = member.getId();
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_CLUB_ID));

        List<ClubNotice> clubNoticeList = clubNoticeRepository.findByClubId(clubId);
        System.out.println("조회된 공지사항 수: " + clubNoticeList.size());

        return clubNoticeList.stream()
                .map(clubNoticeConverter::toClubNoticeResponse)
                .collect(Collectors.toList());
    }

    //소모임 개별 조회
    public ClubNoticeResponse findNotice(Long clubId, Long noticeId) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_CLUB_ID));
        ClubNotice clubNotice = clubNoticeRepository.findById(noticeId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_NOTICE));

        if (!club.getId().equals(clubNotice.getClub().getId())) {
            throw new BaseException(BaseResponseStatus.INVALID_CLUB_NOTICE);
        }

        return clubNoticeConverter.toClubNoticeResponse(clubNotice);
    }

    //소모임 수정
    public ClubNoticeResponse updateNotice(MemberDetails member, Long clubId, Long noticeId, ClubNoticeRequest request) {
        Long memberId = member.getId();
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_CLUB_ID));

        // 공지사항 존재 여부 확인
        ClubNotice clubNotice = clubNoticeRepository.findById(noticeId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_NOTICE));

        // 작성자가 맞는지 확인
        if (!club.getMember().getId().equals(memberId)) {
            throw new BaseException(BaseResponseStatus.INVALID_CLUB_OWNER);
        }

        ClubNotice updateNotice = clubNoticeConverter.toUpdateNoticeEntity(clubNotice, request, member);
        ClubNotice save =  clubNoticeRepository.save(updateNotice);

        return clubNoticeConverter.toClubNoticeResponse(save);
    }

    //삭제
    public void deleteNotice(MemberDetails member, Long clubId, Long noticeId) {
        Long memberId = member.getId();
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_CLUB_ID));

        // 공지사항 존재 여부 확인
        ClubNotice clubNotice = clubNoticeRepository.findById(noticeId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_NOTICE));

        // 작성자가 맞는지 확인
        if (!club.getMember().getId().equals(memberId)) {
            throw new BaseException(BaseResponseStatus.INVALID_CLUB_OWNER);
        }

        clubNoticeRepository.delete(clubNotice);
    }
}
