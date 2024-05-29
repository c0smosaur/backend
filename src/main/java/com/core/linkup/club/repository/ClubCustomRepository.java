package com.core.linkup.club.repository;

import com.core.linkup.club.entity.Club;
import com.core.linkup.club.requset.ClubSearchRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ClubCustomRepository {

    Page<Club> findSearchClubs(ClubSearchRequest request, Pageable pageable);
}
