package com.core.linkup.club.clubmeeting.repository;

import com.core.linkup.club.clubmeeting.entity.ClubMeeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClubMeetingRepository extends JpaRepository<ClubMeeting, Long> {

    List<ClubMeeting> findByClubId(Long clubId);
}
