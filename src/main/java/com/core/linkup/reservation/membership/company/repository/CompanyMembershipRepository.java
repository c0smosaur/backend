package com.core.linkup.reservation.membership.company.repository;

import com.core.linkup.common.exception.BaseException;
import com.core.linkup.common.response.BaseResponseStatus;
import com.core.linkup.reservation.membership.company.entity.CompanyMembership;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyMembershipRepository extends JpaRepository<CompanyMembership, Long> {

    Boolean existsByCompanyId(Long companyId);

    default CompanyMembership findFirstByCompanyId(Long companyId){
        return findById(companyId).orElseThrow(
                () -> new BaseException(BaseResponseStatus.DOES_NOT_EXIST)
        );
    }
}
