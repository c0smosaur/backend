package com.core.linkup.office.entity;

import com.core.linkup.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity(name = "seat_space")
@AllArgsConstructor
@NoArgsConstructor
public class SeatSpace extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "office_building_id", nullable = false)
    private OfficeBuilding officeBuilding;

    private String type;
    private String code;

    public String getLocation() {
        return officeBuilding != null ? officeBuilding.getLocation() : null;
    }
}
