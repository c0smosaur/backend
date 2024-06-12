package com.core.linkup.club.clubnotice.repository;

import com.core.linkup.club.club.entity.QClubMember;
import com.core.linkup.club.clubnotice.entity.QClubComment;
import com.core.linkup.member.entity.QMember;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ClubCommentCustomRepositoryImpl implements ClubCommentCustomRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Tuple> findAllCommentsAndWriters(Long noticeId) {
        QClubComment qClubComment = QClubComment.clubComment;
        QClubMember qClubMember = QClubMember.clubMember;
        QMember qMember = QMember.member;

        return queryFactory.select(qClubComment, qMember)
                .from(qClubComment)
                .join(qClubMember).on(qClubComment.clubMemberId.eq(qClubMember.id))
                .join(qMember).on(qMember.id.eq(qClubMember.memberId))
                .orderBy(qClubComment.createdAt.desc())
                .fetch();
    }


}
