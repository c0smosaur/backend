package com.core.linkup.reservation.membership.individual.entity;

import com.core.linkup.common.entity.BaseMembershipEntity;
import com.core.linkup.reservation.membership.individual.entity.enums.MembershipType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "individual_membership")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class IndividualMembership extends BaseMembershipEntity {

//    private String location;
//    private Long price;
//    private Integer duration;
//    private LocalDateTime startDate;
//    private LocalDateTime endDate;
    @Enumerated(EnumType.STRING)
    private MembershipType type;

//    @ManyToOne
//    @JsonIgnore
//    private Member member;
    private Long memberId;

//    @OneToMany(mappedBy = "individualMembership")
//    private List<Reservation> reservations;
}
