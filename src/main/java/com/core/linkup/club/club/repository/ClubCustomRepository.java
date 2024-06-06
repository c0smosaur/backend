package com.core.linkup.club.club.repository;

import com.core.linkup.club.club.entity.Club;
import com.core.linkup.club.club.request.ClubSearchRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClubCustomRepository {

    Page<Club> findSearchClubs(ClubSearchRequest request, Pageable pageable);
}
