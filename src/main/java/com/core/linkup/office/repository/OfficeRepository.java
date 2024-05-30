package com.core.linkup.office.repository;

import com.core.linkup.common.exception.BaseException;
import com.core.linkup.common.response.BaseResponseStatus;
import com.core.linkup.office.entity.OfficeBuilding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfficeRepository extends JpaRepository<OfficeBuilding, Long>, OfficeSearchRepository {

    default OfficeBuilding findFirstById(Long id) {
        return findById(id).orElseThrow(
                () -> new BaseException(BaseResponseStatus.DOES_NOT_EXIST));
    }
}
