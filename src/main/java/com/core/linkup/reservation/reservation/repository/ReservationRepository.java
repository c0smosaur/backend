package com.core.linkup.reservation.reservation.repository;

import com.core.linkup.reservation.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
