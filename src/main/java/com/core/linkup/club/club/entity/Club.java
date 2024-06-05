package com.core.linkup.club.club.entity;

import com.core.linkup.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity(name = "club")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@SuperBuilder(toBuilder = true)
public class Club extends BaseEntity {

    private Long memberId;
    private Boolean clubAccessibility;
    private String category;
    private Integer recruitCount;
    private String title;
    private String introduction;
    private String detailedIntroduction;
    private String clubThumbnail;
    private String applicationIntroduction;

}
