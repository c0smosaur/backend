package com.core.linkup.club.entity;

import com.core.linkup.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity(name = "club_comment")
@NoArgsConstructor
@AllArgsConstructor
public class ClubComment extends BaseEntity {

    @ManyToOne
    private ClubNotice clubNotice;

    @ManyToOne
    private ClubMember clubMember;
    private String comment;
}
