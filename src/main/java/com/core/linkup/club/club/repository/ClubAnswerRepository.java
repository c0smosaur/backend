package com.core.linkup.club.club.repository;

import com.core.linkup.club.club.entity.ClubAnswer;
import com.core.linkup.club.club.entity.ClubMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClubAnswerRepository extends JpaRepository<ClubAnswer, Long> {
    List<ClubAnswer> findByMemberId(Long memberId);
}
