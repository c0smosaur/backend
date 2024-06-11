package com.core.linkup.club.clubnotice.repository;

import com.core.linkup.club.clubnotice.converter.ClubNoticeConverter;
import com.core.linkup.club.clubnotice.entity.ClubNotice;
import com.core.linkup.club.clubnotice.entity.QClubNotice;
import com.core.linkup.club.clubnotice.entity.enums.NotificationType;
import com.core.linkup.common.exception.BaseException;
import com.core.linkup.common.response.BaseResponseStatus;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ClubNoticeCustomRepositoryImpl implements ClubNoticeCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<ClubNotice> findAllNotice(Long clubId, Pageable pageable) {
        QClubNotice clubNotice = QClubNotice.clubNotice;

        List<ClubNotice> clubNoticeList = queryFactory.selectFrom(clubNotice)
                .where(clubNotice.clubId.eq(clubId)
                        .and(clubNotice.type.eq(NotificationType.NOTICE)))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory.selectFrom(clubNotice)
                .where(clubNotice.clubId.eq(clubId)
                        .and(clubNotice.type.eq(NotificationType.NOTICE)))
                .fetchCount();

        return new PageImpl<>(clubNoticeList, pageable, total);
    }

    @Override
    public ClubNotice findNotice(Long clubId, Long noticeId) {
        QClubNotice clubNotice = QClubNotice.clubNotice;

        ClubNotice notice = queryFactory.selectFrom(clubNotice)
                .where(clubNotice.clubId.eq(clubId)
                        .and(clubNotice.id.eq(noticeId))
//                        .and(clubNotice.type.eq(NotificationType.NOTICE))
                )
                .fetchOne();

        if (notice == null) {
            throw new BaseException(BaseResponseStatus.INVALID_NOTICE);
        }

        return notice;
    }

    @Override
    public Page<ClubNotice> findAllBoard(Long clubId, Pageable pageable) {
        QClubNotice clubNotice = QClubNotice.clubNotice;

        List<ClubNotice> clubNoticeList = queryFactory.selectFrom(clubNotice)
                .where(clubNotice.clubId.eq(clubId)
                        .and(clubNotice.type.eq(NotificationType.BOARD)))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory.selectFrom(clubNotice)
                .where(clubNotice.clubId.eq(clubId)
                        .and(clubNotice.type.eq(NotificationType.BOARD)))
                .fetchCount();

        return new PageImpl<>(clubNoticeList, pageable, total);
    }

    // @Override
    // public Optional<ClubNotice> findBoard(Long clubId, Long noticeId) {
    //     QClubNotice clubNotice = QClubNotice.clubNotice;

    //     ClubNotice notice = queryFactory.selectFrom(clubNotice)
    //             .where(clubNotice.clubId.eq(clubId)
    //                     .and(clubNotice.id.eq(noticeId))
    //                     .and(clubNotice.type.eq(NotificationType.BOARD)))
    //             .fetchOne();

    //     return Optional.ofNullable(notice);
    // }
}
