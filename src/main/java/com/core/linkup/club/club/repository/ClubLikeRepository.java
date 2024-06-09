package com.core.linkup.club.club.repository;

import com.core.linkup.club.club.entity.ClubLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClubLikeRepository extends JpaRepository<ClubLike, Long> {

    void deleteByMemberIdAndClubId(Long memberId, Long clubId);

    List<ClubLike> findAllByMemberId(Long memberId);

    Boolean existsByClubIdAndMemberId(Long clubId, Long memberId);
}
