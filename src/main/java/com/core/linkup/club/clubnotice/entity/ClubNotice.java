package com.core.linkup.club.clubnotice.entity;

import com.core.linkup.club.clubnotice.entity.enums.NotificationType;
import com.core.linkup.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

    private Long clubId;
    private Long memberId;
    private String title;
    private String content;

    @Enumerated(EnumType.STRING)
    private NotificationType type; //게시판 or 공지
}
