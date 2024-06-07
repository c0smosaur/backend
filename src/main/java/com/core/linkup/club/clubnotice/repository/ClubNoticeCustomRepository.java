package com.core.linkup.club.clubnotice.repository;

import com.core.linkup.club.clubnotice.entity.ClubNotice;
import com.core.linkup.club.clubnotice.response.ClubNoticeResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ClubNoticeCustomRepository {
    Page<ClubNoticeResponse> findAllNotice(Long clubId, Pageable pageable);
    ClubNoticeResponse findNotice(Long clubId, Long noticeId);

    Page<ClubNotice> findAllBoard(Long clubId, Pageable pageable);

    Optional<ClubNotice> findBoard(Long clubId, Long noticeId);
}
