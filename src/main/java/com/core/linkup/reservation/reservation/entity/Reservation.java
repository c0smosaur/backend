package com.core.linkup.reservation.reservation.entity;

import com.core.linkup.common.entity.BaseEntity;
import com.core.linkup.reservation.reservation.entity.enums.ReservationStatus;
import com.core.linkup.reservation.reservation.entity.enums.ReservationType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Table(name = "reservation")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Reservation extends BaseEntity {

    private Long memberId;
    private Long indMembershipId;
    private Long comMembershipId;
    private Long seatId;

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Long price;
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;
    @Enumerated(EnumType.STRING)
    private ReservationType type;
}
