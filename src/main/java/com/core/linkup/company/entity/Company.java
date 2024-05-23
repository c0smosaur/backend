package com.core.linkup.company.entity;

import com.core.linkup.common.entity.BaseEntity;
import com.core.linkup.membership.company.entity.CompanyMembership;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity(name = "company")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Company extends BaseEntity {
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String managerPhone;
    @Column(nullable = false)
    private String managerEmail;
    private boolean consentContact;
    private boolean consentPromotion;

    @OneToMany
    private List<CompanyMembership> companyMemberships;
}
