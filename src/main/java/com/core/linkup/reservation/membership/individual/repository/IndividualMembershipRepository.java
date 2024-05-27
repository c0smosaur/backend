package com.core.linkup.reservation.membership.individual.repository;

import com.core.linkup.reservation.membership.individual.entity.IndividualMembership;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IndividualMembershipRepository extends JpaRepository<IndividualMembership, Long> {
}
