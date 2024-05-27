package com.core.linkup.club.entity;

import com.core.linkup.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity(name = "club_board")
@NoArgsConstructor
@AllArgsConstructor
public class ClubBoard extends BaseEntity {

    @ManyToOne
    private Club clubId;

    private String title;
    private String content;
    private String type; //게시판 or 공지
}
