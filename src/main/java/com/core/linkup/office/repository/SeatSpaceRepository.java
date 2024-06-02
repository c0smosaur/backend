package com.core.linkup.office.repository;

import com.core.linkup.common.exception.BaseException;
import com.core.linkup.common.response.BaseResponseStatus;
import com.core.linkup.office.entity.SeatSpace;
import com.core.linkup.office.entity.enums.SeatSpaceType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SeatSpaceRepository extends JpaRepository<SeatSpace, Long> {

    Optional<List<SeatSpace>> findAllByOfficeBuildingIdAndType(Long officeBuildingId, SeatSpaceType type);

    default SeatSpace findFirstById(Long id) {
        return findById(id).orElseThrow(
                () -> new BaseException(BaseResponseStatus.DOES_NOT_EXIST));
    }

    default List<SeatSpace> findAllByOfficeIdAndType(Long officeId, SeatSpaceType type) {
        return findAllByOfficeBuildingIdAndType(officeId, type).orElseThrow(
                () -> new BaseException(BaseResponseStatus.INVALID_OFFICE_LOCATION));
    }
}
