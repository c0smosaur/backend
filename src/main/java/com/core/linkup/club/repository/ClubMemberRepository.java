package com.core.linkup.club.repository;

import com.core.linkup.club.entity.ClubMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubMemberRepository extends JpaRepository<ClubMember, Long> {
}
