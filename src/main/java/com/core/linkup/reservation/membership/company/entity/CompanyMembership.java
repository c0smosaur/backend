package com.core.linkup.reservation.membership.company.entity;

import com.core.linkup.common.entity.BaseEntity;
import com.core.linkup.member.entity.Member;
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
@Table(name = "company_membership")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class CompanyMembership extends BaseEntity {

    private String location;
    private Long price;
    private Integer duration;
    private Integer credit;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer staffCount;

    @ManyToOne
    @JoinColumn(name="company_id")
    @JsonIgnore
    private Company company;

    @OneToMany(mappedBy = "companyMembership")
    private List<Member> member;
}
