package com.core.linkup.office.repository;

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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

        JPAQuery<OfficeBuilding> query =
                queryFactory
                        .selectFrom(officeBuilding)
                        .where(setBooleanBuilder(request, officeBuilding))
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

    private BooleanBuilder setBooleanBuilder(OfficeSearchRequest request, QOfficeBuilding officeBuilding) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if (request.getCity() != null) {
            booleanBuilder.and(officeBuilding.city.eq(request.getCity()));
        }
        if (request.getIndustry() != null) {
            booleanBuilder.and(officeBuilding.city.eq(request.getCity()));
        }

        return booleanBuilder;
    }
}
