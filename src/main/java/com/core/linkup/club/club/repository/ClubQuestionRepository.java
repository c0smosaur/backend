package com.core.linkup.club.club.repository;

import com.core.linkup.club.club.entity.ClubQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClubQuestionRepository extends JpaRepository<ClubQuestion, Long> {
    List<ClubQuestion> findAllByClubId(Long clubId);
}
