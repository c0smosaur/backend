package com.core.linkup.office.repository;

import com.core.linkup.common.exception.BaseException;
import com.core.linkup.common.response.BaseResponseStatus;
import com.core.linkup.office.entity.SeatSpace;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatSpaceRepository extends JpaRepository<SeatSpace, Long> {

    default SeatSpace findFirstById(Long id) {
        return findById(id).orElseThrow(
                () -> new BaseException(BaseResponseStatus.DOES_NOT_EXIST));
    }
}
