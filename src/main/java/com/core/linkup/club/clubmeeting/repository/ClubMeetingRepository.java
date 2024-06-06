package com.core.linkup.club.clubmeeting.repository;

import com.core.linkup.club.clubmeeting.entity.ClubMeeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubMeetingRepository extends JpaRepository<ClubMeeting, Long> {

}
