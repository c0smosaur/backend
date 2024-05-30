package com.core.linkup.reservation.reservation.entity;

import com.core.linkup.common.entity.BaseEntity;
import com.core.linkup.member.entity.Member;
import com.core.linkup.office.entity.SeatSpace;
import com.core.linkup.reservation.membership.company.entity.CompanyMembership;
import com.core.linkup.reservation.membership.individual.entity.IndividualMembership;
import com.core.linkup.reservation.reservation.entity.enums.ReservationStatus;
import com.core.linkup.reservation.reservation.entity.enums.ReservationType;
import jakarta.persistence.*;
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

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Long price;
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;
    @Enumerated(EnumType.STRING)
    private ReservationType type;

//    @ManyToOne
//    private CompanyMembership companyMembership;
    private Long companyMembershipId;

//    @ManyToOne
//    private IndividualMembership individualMembership;
    private Long individualMembershipId;

//    @ManyToOne
//    private SeatSpace seatSpace;
    private Long seatId;
}
