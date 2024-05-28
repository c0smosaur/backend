package com.core.linkup.reservation.membership.individual.entity;

import com.core.linkup.common.entity.BaseEntity;
import com.core.linkup.member.entity.Member;
import com.core.linkup.reservation.membership.individual.entity.enums.MembershipType;
import com.core.linkup.reservation.reservation.entity.Reservation;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "individual_membership")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class IndividualMembership extends BaseEntity {

    private String location;
    @Enumerated(EnumType.STRING)
    private MembershipType type;
    private Integer duration;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Long price;

//    @ManyToOne
//    @JsonIgnore
//    private Member member;
    private Long memberId;

//    @OneToMany(mappedBy = "individualMembership")
//    private List<Reservation> reservations;
}
