package com.core.linkup.club.clubmeeting.repository;

import com.core.linkup.club.clubmeeting.entity.ClubMeeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClubMeetingRepository extends JpaRepository<ClubMeeting, Long> {

    List<ClubMeeting> findByClubId(Long clubId);
    Optional<ClubMeeting> findByIdAndClubId(Long id, Long clubId);

    Optional<ClubMeeting> findFirstByClubIdOrderByDateDesc(Long id);
}
