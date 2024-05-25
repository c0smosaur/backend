package com.core.linkup.reservation.membership.company.repository;

import com.core.linkup.common.exception.BaseException;
import com.core.linkup.common.response.BaseResponseStatus;
import com.core.linkup.reservation.membership.company.entity.Company;
import com.core.linkup.reservation.membership.company.entity.CompanyMembership;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyMembershipRepository extends JpaRepository<CompanyMembership, Long> {

    Optional<CompanyMembership> findByCompanyId(Long companyId);

    Boolean existsByCompanyId(Long companyId);

    default CompanyMembership findFirstByCompanyId(Long companyId){
        return findByCompanyId(companyId).orElseThrow(
                () -> new BaseException(BaseResponseStatus.DOES_NOT_EXIST)
        );
    }
}
