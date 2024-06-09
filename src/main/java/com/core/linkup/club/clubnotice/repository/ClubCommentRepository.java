package com.core.linkup.club.clubnotice.repository;

import com.core.linkup.club.clubnotice.entity.ClubComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClubCommentRepository extends JpaRepository<ClubComment, Long> {

    List<ClubComment> findAllByClubNoticeId(Long clubNoticeId);
}
