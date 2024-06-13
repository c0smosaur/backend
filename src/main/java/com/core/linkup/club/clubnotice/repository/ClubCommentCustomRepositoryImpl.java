package com.core.linkup.club.clubnotice.repository;

import com.core.linkup.club.club.entity.QClubMember;
import com.core.linkup.club.clubnotice.entity.ClubComment;
import com.core.linkup.club.clubnotice.entity.QClubComment;
import com.core.linkup.club.clubnotice.entity.QClubNotice;
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
        QClubNotice qClubNotice = QClubNotice.clubNotice;
        QClubComment qClubComment = QClubComment.clubComment;
        QClubMember qClubMember = QClubMember.clubMember;
        QMember qMember = QMember.member;

        return queryFactory.select(qClubComment, qClubMember, qMember)
                .from(qClubComment)
//                .join(qClubNotice).on(qClubNotice.id.eq(noticeId))
                .join(qClubMember).on(qClubComment.clubMemberId.eq(qClubMember.id))
                .join(qMember).on(qClubMember.memberId.eq(qMember.id))
                .where(qClubComment.clubNoticeId.eq(noticeId)
//                        .and(qMember.id.eq(qClubMember.memberId))
                        )
                .orderBy(qClubComment.createdAt.asc())
                .fetch();
    }

    //    return queryFactory.select(qClubComment, qClubMember, qMember)
    //            .from(qClubComment)
    //            .join(qClubMember).on(qClubComment.clubMemberId.eq(qClubMember.id))
    //            .join(qMember).on(qClubMember.memberId.eq(qMember.id))
    //            .where(qClubComment.clubNoticeId.eq(noticeId))
    //            .orderBy(qClubComment.createdAt.asc())
    //            .fetch();

}
