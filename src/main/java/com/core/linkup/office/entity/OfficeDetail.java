package com.core.linkup.office.entity;

import com.core.linkup.common.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class OfficeDetail extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "office_building_id", nullable = false)
    private OfficeBuilding officeBuilding;

    @NotNull
    private String location;

    @NotNull
    private Boolean openDesk;

    @NotNull
    private Boolean partition;

    @NotNull
    private Boolean isolationRoom;

    @NotNull
    private Boolean monitorDesk;

    @NotNull
    private Boolean conf4;

    @NotNull
    private Boolean conf8;

    @NotNull
    private Boolean seminarRoom;

    @NotNull
    private Boolean studio;

    @NotNull
    private Boolean lounge;

    @NotNull
    private Boolean phoneBooth;

    @NotNull
    private Boolean relaxRoom;

    @NotNull
    private Boolean oaRoom;

    @NotNull
    private Boolean snackBar;

    @NotNull
    private Boolean coffee;

    @NotNull
    private Boolean postbox;

    @NotNull
    private Boolean parking;
}
