package com.core.linkup.club.club.repository;

import com.core.linkup.club.club.entity.ClubLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubLikeRepository extends JpaRepository<ClubLike, Long> {

}
