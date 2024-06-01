package com.core.linkup.club.repository;

import com.core.linkup.club.entity.ClubAnswer;
import com.core.linkup.club.entity.ClubMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClubAnswerRepository extends JpaRepository<ClubAnswer, Long> {
    List<ClubAnswer> findByClubMember(ClubMember clubMember);

    List<ClubAnswer> findByClubMemberId(Long id);
}
