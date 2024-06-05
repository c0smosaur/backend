package com.core.linkup.club.club.entity;

import com.core.linkup.common.entity.BaseEntity;
import com.core.linkup.member.entity.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "club_member")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ClubMember extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "club_id")
    private Club club;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private String introduction;
    private Boolean approval;
}
