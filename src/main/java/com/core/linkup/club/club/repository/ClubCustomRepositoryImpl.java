package com.core.linkup.club.club.repository;

import com.core.linkup.club.club.entity.Club;
import com.core.linkup.club.club.entity.ClubLike;
import com.core.linkup.club.club.entity.QClub;
import com.core.linkup.club.club.entity.QClubLike;
import com.core.linkup.club.club.request.ClubSearchRequest;
import com.core.linkup.common.entity.enums.ClubType;
import com.core.linkup.office.entity.QOfficeBuilding;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.core.linkup.office.entity.QOfficeBuilding.officeBuilding;
import static com.core.linkup.reservation.membership.company.entity.QCompanyMembership.companyMembership;
import static com.core.linkup.reservation.membership.individual.entity.QIndividualMembership.individualMembership;

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
            booleanBuilder.and(club.category.eq(clubType.getClubCategoryName()));
        }

        List<Club> clubs = queryFactory.selectFrom(club)
                .where(booleanBuilder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory.selectFrom(club)
                .where(booleanBuilder)
                .fetchCount();

        return new PageImpl<>(clubs, pageable, total);
    }

    @Override
    public boolean existsValidMembershipWithLocation(Long memberId) {
        return queryFactory
                .selectFrom(individualMembership)
                .leftJoin(companyMembership).on(individualMembership.memberId.eq(memberId))
                .leftJoin(officeBuilding).on(
                        individualMembership.location.eq(officeBuilding.location)
                                .or(companyMembership.location.eq(officeBuilding.location))
                )
                .where(
                        individualMembership.memberId.isNull()// .eq(memberId)
                                .or(companyMembership.companyId.isNull()) //.eq(memberId))
                )
                .fetchFirst() != null;
    }

    @Override
    public Long findOfficeBuildingIdByLocation(String location) {
        QOfficeBuilding officeBuilding = QOfficeBuilding.officeBuilding;

        return queryFactory.select(officeBuilding.id)
                .from(officeBuilding)
                .where(officeBuilding.location.eq(location))
                        //String.valueOf(officeBuilding.location.isNull()))
                .fetchFirst();
    }

    @Override
    public Page<ClubLike> findClubLikes(Long memberId, Pageable pageable) {
        QClubLike clubLike = QClubLike.clubLike;

        List<ClubLike> results = queryFactory.selectFrom(clubLike)
                .where(clubLike.memberId.eq(memberId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(clubLike.id.asc())
                .fetch();

        long total = queryFactory.selectFrom(clubLike)
                .where(clubLike.memberId.eq(memberId))
                .fetchCount();

        return new PageImpl<>(results, pageable, total);
    }
}
