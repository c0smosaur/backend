package com.core.linkup.club.clubnotice.repository;

import com.querydsl.core.Tuple;

import java.util.List;

public interface ClubCommentCustomRepository {

    List<Tuple> findAllCommentsAndWriters(Long noticeId);
}
