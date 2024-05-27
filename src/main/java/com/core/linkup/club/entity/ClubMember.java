package com.core.linkup.club.entity;

import com.core.linkup.common.entity.BaseEntity;
import com.core.linkup.member.entity.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity(name = "club_member")
@NoArgsConstructor
@AllArgsConstructor
public class ClubMember extends BaseEntity {

    @ManyToOne
    private Club clubId;

    @ManyToOne
    private Member memberId;

    private String introduction;
    private Boolean approval;
}
