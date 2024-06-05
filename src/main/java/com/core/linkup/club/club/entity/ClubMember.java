package com.core.linkup.club.club.entity;

import com.core.linkup.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "club_member")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ClubMember extends BaseEntity {

    private Long clubId;
    private Long memberId;
    private String introduction;
    private Boolean approval;
}
