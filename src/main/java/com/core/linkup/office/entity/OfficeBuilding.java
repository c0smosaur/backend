package com.core.linkup.office.entity;

import com.core.linkup.common.entity.BaseEntity;
import com.core.linkup.office.response.OfficeDetailSearchResponse;
import com.core.linkup.office.response.OfficeResponse;
import com.core.linkup.office.response.OfficeSearchResponse;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity(name = "office_building")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OfficeBuilding extends BaseEntity {

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private String region;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private Integer openHours;

    @Column(nullable = false)
    private Integer capacity;

    @Column(nullable = false)
    private Integer officeCount;

    @Column(nullable = false)
    private String trafficInfo;

    @Column(nullable = false)
    private String rules;

    @Column(nullable = false)
    private String officePhone; //공유오피스전화번호

    @Column(nullable = false)
    private Double latitude; //위도

    @Column(nullable = false)
    private Double longitude; //경도

    @Column(nullable = false)
    private String images;

    @OneToOne(mappedBy = "officeBuilding")
    private OfficeDetail officeDetail;

    @OneToMany
    private List<SeatSpace> seatSpaces;

    public OfficeResponse toDto() {
        return new OfficeResponse(
                id,
                location,
                region,
                city,
                street,
                address,
                capacity,
                trafficInfo,
                latitude,
                longitude,
                images
        );
    }

    public OfficeSearchResponse toDetailDto() {
        OfficeDetailSearchResponse officeDetailDto = officeDetail != null ? officeDetail.toDetailSearchDto() : null;
        return new OfficeSearchResponse(
                id,
                location,
                region,
                city,
                street,
                address,
                capacity,
                trafficInfo,
                latitude,
                longitude,
                images,
                officeDetailDto

        );
    }


}
