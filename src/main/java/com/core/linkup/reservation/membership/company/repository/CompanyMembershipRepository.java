package com.core.linkup.reservation.membership.company.repository;

import com.core.linkup.reservation.membership.company.entity.CompanyMembership;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyMembershipRepository extends JpaRepository<CompanyMembership, Long> {
}
