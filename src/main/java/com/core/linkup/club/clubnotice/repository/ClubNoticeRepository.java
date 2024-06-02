package com.core.linkup.club.clubnotice.repository;

import com.core.linkup.club.entity.ClubNotice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClubNoticeRepository extends JpaRepository<ClubNotice, Long> {
    List<ClubNotice> findByClubId(Long clubId);
}
