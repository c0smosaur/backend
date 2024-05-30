package com.core.linkup.reservation.membership.individual.repository;

import com.core.linkup.reservation.membership.individual.entity.IndividualMembership;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface IndividualMembershipRepository extends JpaRepository<IndividualMembership, Long>,
        IndividualMembershipRepositoryCustom {

    List<IndividualMembership> findAllByMemberIdOrderByCreatedAtDesc(Long id);
}
