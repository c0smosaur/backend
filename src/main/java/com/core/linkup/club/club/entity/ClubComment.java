package com.core.linkup.club.club.entity;

import com.core.linkup.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity(name = "club_comment")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ClubComment extends BaseEntity {

    private Long clubNoticeId;
    private Long clubMemberId;
    private String comment;
}
