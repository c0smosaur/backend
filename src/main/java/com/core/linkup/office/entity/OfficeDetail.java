package com.core.linkup.office.entity;

import com.core.linkup.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "office_detail")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OfficeDetail extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "office_building_id")
    private OfficeBuilding officeBuilding;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private Boolean openDesk;

    @Column(nullable = false)
    private Boolean partied;

    @Column(nullable = false)
    private Boolean isolationRoom;

    @Column(nullable = false)
    private Boolean monitorDesk;

    @Column(nullable = false)
    private Boolean conf4;

    @Column(nullable = false)
    private Boolean conf8;

    @Column(nullable = false)
    private Boolean seminarRoom;

    @Column(nullable = false)
    private Boolean studio;

    @Column(nullable = false)
    private Boolean lounge;

    @Column(nullable = false)
    private Boolean phoneBooth;

    @Column(nullable = false)
    private Boolean relaxRoom;

    @Column(nullable = false)
    private Boolean oaRoom;

    @Column(nullable = false)
    private Boolean snackBar;

    @Column(nullable = false)
    private Boolean coffee;

    @Column(nullable = false)
    private Boolean postbox;

    @Column(nullable = false)
    private Boolean parking;
}
