package com.core.linkup.club.clubnotice.repository;

import com.core.linkup.club.clubnotice.converter.ClubNoticeConverter;
import com.core.linkup.club.clubnotice.entity.ClubNotice;
import com.core.linkup.club.clubnotice.entity.QClubNotice;
import com.core.linkup.club.clubnotice.entity.enums.NotificationType;
import com.core.linkup.club.clubnotice.response.ClubNoticeResponse;
import com.core.linkup.common.exception.BaseException;
import com.core.linkup.common.response.BaseResponseStatus;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ClubNoticeCustomRepositoryImpl implements ClubNoticeCustomRepository {

    private final JPAQueryFactory queryFactory;
    private final ClubNoticeConverter clubNoticeConverter;

    @Override
    public Page<ClubNoticeResponse> findAllNotice(Long clubId, Pageable pageable) {
        QClubNotice clubNotice = QClubNotice.clubNotice;

        List<ClubNotice> clubNoticeList = queryFactory.selectFrom(clubNotice)
                .where(clubNotice.clubId.eq(clubId)
                        .and(clubNotice.type.eq(NotificationType.valueOf("NOTICE"))))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        List<ClubNoticeResponse> clubNoticeResponseList = clubNoticeList.stream()
                .map(clubNoticeConverter::toClubNoticeResponse)
                .collect(Collectors.toList());

        long total = queryFactory.selectFrom(clubNotice)
                .where(clubNotice.clubId.eq(clubId))
                .fetchCount();

        return new PageImpl<>(clubNoticeResponseList, pageable, total);
    }

    @Override
    public ClubNoticeResponse findNotice(Long clubId, Long noticeId) {
        QClubNotice clubNotice = QClubNotice.clubNotice;

        ClubNotice notice = queryFactory.selectFrom(clubNotice)
                .where(clubNotice.clubId.eq(clubId)
                        .and(clubNotice.id.eq(noticeId))
                        .and(clubNotice.type.eq(NotificationType.valueOf("notice"))))
                .fetchOne();

        if (notice == null) {
            throw new BaseException(BaseResponseStatus.INVALID_NOTICE);
        }

        return clubNoticeConverter.toClubNoticeResponse(notice);
    }
}
