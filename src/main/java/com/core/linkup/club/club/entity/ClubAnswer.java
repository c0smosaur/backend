package com.core.linkup.club.club.entity;

import com.core.linkup.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity(name = "club_answer")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@SuperBuilder
public class ClubAnswer extends BaseEntity {

    private Long clubId;
    private Long memberId;
    @Setter
    private Long clubMemberId; //clubMember entity id
    private String answer;
    private Integer qorders;
}
