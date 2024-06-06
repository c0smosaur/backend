package com.core.linkup.club.club.repository;

import com.core.linkup.club.club.entity.Club;
import com.core.linkup.club.club.entity.QClub;
import com.core.linkup.club.club.request.ClubSearchRequest;
import com.core.linkup.common.entity.enums.ClubType;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ClubCustomRepositoryImpl implements ClubCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Club> findSearchClubs(ClubSearchRequest request, Pageable pageable) {
        QClub club = QClub.club;

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if (request.getClubType() != null && !request.getClubType().isEmpty()) {
            ClubType clubType = ClubType.fromKor(request.getClubType());
            booleanBuilder.and(club.category.eq(String.valueOf(clubType)));
        }

        List<Club> clubs = queryFactory.selectFrom(club)
                .where(booleanBuilder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
//        List<Club> clubs = queryFactory.selectFrom(club)
//                .leftJoin(club.member, member)
//                .fetchJoin()
//                .where(booleanBuilder)
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize())
//                .fetch();

        long total = queryFactory.selectFrom(club)
                .where(booleanBuilder)
                .fetchCount();

        return new PageImpl<>(clubs, pageable, total);
    }
}
