package com.core.linkup.reservation.membership.company.repository;

import com.core.linkup.reservation.membership.company.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface CompanyRepository extends JpaRepository<Company, Long>,
        QuerydslPredicateExecutor<Company> {
}
