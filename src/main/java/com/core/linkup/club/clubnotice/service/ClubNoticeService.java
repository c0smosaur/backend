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

}
