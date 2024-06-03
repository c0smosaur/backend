package com.core.linkup.club.entity;

import com.core.linkup.club.entity.enums.NotificationType;
import com.core.linkup.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity(name = "club_notice")
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ClubNotice extends BaseEntity { // board -> notice

    @ManyToOne
    @JoinColumn(name = "club_id")
    private Club club;


    private String title;
    private String content;
    private NotificationType type; //게시판 or 공지
}
