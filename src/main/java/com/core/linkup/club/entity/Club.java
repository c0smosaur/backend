package com.core.linkup.club.entity;

import com.core.linkup.common.entity.BaseEntity;
import com.core.linkup.member.entity.Member;
import com.core.linkup.office.entity.OfficeBuilding;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "club")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Club extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "office_building_id")
    private OfficeBuilding locationId;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member memberId;

    private Boolean clubAccessibility;
    private String category;
    private Integer recruitCount;
    private String title;
    private String introduction;
    private String detailedIntroduction;
    private String clubThumbnail;
    private String applicationIntroduction;

}
