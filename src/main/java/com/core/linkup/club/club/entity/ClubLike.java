package com.core.linkup.club.club.entity;

import com.core.linkup.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity(name = "club_like")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@SuperBuilder
public class ClubLike extends BaseEntity {

    private Long clubId;
    private Long memberId;
    private Boolean liked;
    private String message;
}
