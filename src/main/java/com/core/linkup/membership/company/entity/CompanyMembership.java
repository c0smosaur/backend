package com.core.linkup.membership.company.entity;

import com.core.linkup.common.entity.BaseEntity;
import com.core.linkup.company.entity.Company;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity(name = "company_membership")
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

    @ManyToOne
    private Company company;
}
