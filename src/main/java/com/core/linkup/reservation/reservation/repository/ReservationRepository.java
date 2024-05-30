package com.core.linkup.reservation.reservation.repository;

import com.core.linkup.reservation.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long>
        , ReservationRepositoryCustom
{

    List<Reservation> findAllByCompanyMembershipIdOrderByCreatedAtDesc(Long cmId);
    List<Reservation> findAllByIndividualMembershipIdOrderByCreatedAtDesc(Long imId);
}
