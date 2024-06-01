package com.core.linkup.reservation.membership.individual.repository;

import com.core.linkup.common.exception.BaseException;
import com.core.linkup.common.response.BaseResponseStatus;
import com.core.linkup.reservation.membership.individual.entity.IndividualMembership;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Collections;
import java.util.List;
import java.util.Optional;

public interface IndividualMembershipRepository extends JpaRepository<IndividualMembership, Long>{

    Optional<List<IndividualMembership>> findAllByMemberIdOrderByCreatedAtDesc(Long id);

    default IndividualMembership findFirstById(Long id){
        return findById(id).orElseThrow(() -> {
            return new BaseException(BaseResponseStatus.DOES_NOT_EXIST);
        });
    };

    default List<IndividualMembership> findAllByMemberIdOrderByCreatedDesc(Long id){
        Optional<List<IndividualMembership>> result = findAllByMemberIdOrderByCreatedAtDesc(id);

        return result.orElse(Collections.emptyList());
    }
}
