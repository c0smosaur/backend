package com.core.linkup.club.entity;

import com.core.linkup.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity(name = "club_answer")
@NoArgsConstructor
@AllArgsConstructor
public class ClubAnswer extends BaseEntity {

    @ManyToOne
    private Club clubId;

    @ManyToOne
    private ClubMember clubMemberId;

    private String answer;
    private Integer order;
}
