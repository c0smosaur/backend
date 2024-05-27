package com.core.linkup.office.repository;

import com.core.linkup.common.entity.enums.CityType;
import com.core.linkup.member.entity.QMember;
import com.core.linkup.office.entity.OfficeBuilding;
import com.core.linkup.office.entity.QOfficeBuilding;
import com.core.linkup.office.request.OfficeSearchRequest;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

@Repository
public class OfficeSearchRepositoryImpl implements OfficeSearchRepository {

    private final JPAQueryFactory queryFactory;

    public OfficeSearchRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public Page<OfficeBuilding> searchPage(OfficeSearchRequest request, Pageable pageable) {

        QOfficeBuilding officeBuilding = QOfficeBuilding.officeBuilding;
        QMember member = QMember.member;

        JPAQuery<OfficeBuilding> query =
                queryFactory
                        .selectFrom(officeBuilding)
                        .where(setBooleanBuilder(request, officeBuilding, member))
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize());

        for (Sort.Order order : pageable.getSort()) {
            query.orderBy(
                    new OrderSpecifier(order.isAscending() ? Order.ASC : Order.DESC,
                            //정렬을 위한 QueryDSL API
                            new PathBuilder<>(
                                    officeBuilding.getType(), officeBuilding.getMetadata()
                            ).get(order.getProperty())));
        }

        QueryResults<OfficeBuilding> queryResult = query.fetchResults();
        return new PageImpl<>(queryResult.getResults(), pageable, queryResult.getTotal());
    }

    private BooleanBuilder setBooleanBuilder(OfficeSearchRequest request, QOfficeBuilding officeBuilding, QMember member) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if (request.getRegion() != null) {
            booleanBuilder.and(officeBuilding.region.eq(request.getRegion()));
        }
        CityType cityType = request.getCity();
        if (cityType != null) {
            booleanBuilder.and(officeBuilding.city.eq(cityType));
        }
        if (request.getStreet() != null) {
            booleanBuilder.and(officeBuilding.street.eq(request.getStreet()));
        }
//        if (request.getIndustry() != null) {
//            booleanBuilder.and(member.industry.eq(request.getIndustry()));
//        }
//        if (request.getOccupation() != null) {
//            booleanBuilder.and(member.occupation.eq(request.getOccupation()));
//        }

        return booleanBuilder;
    }
}
