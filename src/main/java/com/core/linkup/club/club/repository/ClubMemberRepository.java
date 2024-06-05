package com.core.linkup.club.club.repository;

import com.core.linkup.club.club.entity.Club;
import com.core.linkup.club.club.entity.ClubMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClubMemberRepository extends JpaRepository<ClubMember, Long> {
    Optional<ClubMember> findByClubAndMemberId(Club club, Long memberId);
    List<ClubMember> findByClub(Club club);
    List<ClubMember> findByMemberId(Long memberId);

}
