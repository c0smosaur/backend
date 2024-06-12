package com.core.linkup.club.club.entity;

import com.core.linkup.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity(name = "club_member")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@SuperBuilder(toBuilder = true)
public class ClubMember extends BaseEntity {

    @Setter
    private Long clubId;
    @Setter
    private Long memberId;
    private String introduction;
    private Boolean approval;
}
