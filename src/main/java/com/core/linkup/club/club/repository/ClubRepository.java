package com.core.linkup.club.club.repository;

import com.core.linkup.club.club.entity.Club;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClubRepository extends JpaRepository<Club, Long>, ClubCustomRepository {
    Page<Club> findByMemberId(Long memberId, Pageable pageable);
    List<Club> findByMemberId(Long memberId);
}
