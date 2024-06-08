package com.core.linkup.reservation.reservation.entity;

import com.core.linkup.common.entity.BaseEntity;
import com.core.linkup.reservation.reservation.entity.enums.ReservationStatus;
import com.core.linkup.reservation.reservation.entity.enums.ReservationType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Table(name = "reservation")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
public class Reservation extends BaseEntity {

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Long price;
    @Setter
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;
    @Enumerated(EnumType.STRING)
    private ReservationType type;

    private Long companyMembershipId;
    private Long individualMembershipId;
    private Long seatId;

    @Version
    private int version;
}
