package com.core.linkup.club.clubnotice.repository;

import com.core.linkup.club.clubnotice.entity.ClubNotice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClubNoticeCustomRepository {
    Page<ClubNotice> findAllNotice(Long clubId, Pageable pageable);
    ClubNotice findNotice(Long clubId, Long noticeId);

    Page<ClubNotice> findAllBoard(Long clubId, Pageable pageable);

    // Optional<ClubNotice> findBoard(Long clubId, Long noticeId);
}
