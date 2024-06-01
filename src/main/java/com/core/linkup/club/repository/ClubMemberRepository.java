package com.core.linkup.club.repository;

import com.core.linkup.club.entity.Club;
import com.core.linkup.club.entity.ClubMember;
import com.core.linkup.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClubMemberRepository extends JpaRepository<ClubMember, Long> {
    List<ClubMember> findByMember(Member member);

    Optional<ClubMember> findByClubAndMemberId(Club club, Long memberId);
    List<ClubMember> findByClub(Club club);
    List<ClubMember> findByMemberId(Long memberId);

}
