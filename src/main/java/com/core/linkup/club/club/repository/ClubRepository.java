package com.core.linkup.club.club.repository;

import com.core.linkup.club.club.entity.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubRepository extends JpaRepository<Club, Long>, ClubCustomRepository {

}
