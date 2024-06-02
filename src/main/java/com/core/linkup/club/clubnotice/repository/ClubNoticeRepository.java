package com.core.linkup.club.clubnotice.repository;

import com.core.linkup.club.entity.ClubMeeting;
import com.core.linkup.club.entity.ClubNotice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubNoticeRepository extends JpaRepository<ClubNotice, Long> {
}
