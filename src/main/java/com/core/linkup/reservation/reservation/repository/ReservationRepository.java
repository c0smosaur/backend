package com.core.linkup.reservation.reservation.repository;

import com.core.linkup.common.exception.BaseException;
import com.core.linkup.common.response.BaseResponseStatus;
import com.core.linkup.reservation.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long>
        , ReservationRepositoryCustom
{
    default Reservation findFirstById(Long id){
        return findById(id).orElseThrow(() -> new BaseException(BaseResponseStatus.DOES_NOT_EXIST));
    }
}
