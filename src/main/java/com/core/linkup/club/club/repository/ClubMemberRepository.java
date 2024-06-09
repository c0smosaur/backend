package com.core.linkup.club.club.repository;

import com.core.linkup.club.club.entity.ClubMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClubMemberRepository extends JpaRepository<ClubMember, Long> {
    List<ClubMember> findByMemberId(Long memberId);

    List<ClubMember> findByClubId(Long clubId);

    boolean existsByClubIdAndMemberId(Long clubId, Long memberId);

    Optional<ClubMember> findByClubIdAndMemberId(Long clubId, Long memberId);

    Optional<ClubMember> findByMemberIdAndClubId(Long memberId, Long clubId);
}
