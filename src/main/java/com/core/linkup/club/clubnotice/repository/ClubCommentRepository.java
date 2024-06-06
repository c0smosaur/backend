package com.core.linkup.club.clubnotice.repository;

import com.core.linkup.club.clubnotice.entity.ClubComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubCommentRepository extends JpaRepository<ClubComment, Long> {
}
