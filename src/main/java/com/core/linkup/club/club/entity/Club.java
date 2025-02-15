package com.core.linkup.club.club.entity;

import com.core.linkup.common.entity.BaseEntity;
import com.core.linkup.common.entity.enums.ClubType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity(name = "club")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@SuperBuilder
public class Club extends BaseEntity {

    @Setter
    private Long officeBuildingId;
    private String officeBuildingLocation;
    private Long memberId;
    private Boolean clubAccessibility;
    @Enumerated(EnumType.STRING)
    private ClubType category;
    private Integer recruitCount;
    private String title;
    private String introduction;
    private String detailedIntroduction;
    private String clubThumbnail;
    private String applicationIntroduction;

}
