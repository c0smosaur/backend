package com.core.linkup.office.entity;

import com.core.linkup.common.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.TypeDef;
import org.locationtech.jts.geom.Point;

import java.util.List;

@TypeDef(
        name = "point",
        defaultForType = org.hibernate.spatial.GeolatteGeometryType.class,
        typeClass = org.hibernate.spatial.JTSGeometryType.class
)

@Entity(name = "OfficeBuilding")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OfficeBuilding extends BaseEntity {

    @NotNull
    private String location;

    @NotNull
    private String region;

    @NotNull
    private String city;

    @NotNull
    private String street;

    @NotNull
    private String address;

    @NotNull
    private Integer openHours;

    @NotNull
    private Integer capacity;

    @NotNull
    private Integer officeCount;

    @NotNull
    private String trafficInfo;

    @NotNull
    private String rules;

    @NotNull
    private String officePhone; //공유오피스전화번호

    @NotNull
    private Double latitude; //위도

    @NotNull
    private Double longitude; //경도

//    @Type(type="point")
    @Column(name = "geom", columnDefinition = "POINT")
    private Point geom;

    @NotNull
    private String images;

    @OneToOne(mappedBy = "officeBuilding")
    private OfficeDetail officeDetail;

    @OneToMany(mappedBy = "officeBuilding")
    private List<Room> rooms;

}
