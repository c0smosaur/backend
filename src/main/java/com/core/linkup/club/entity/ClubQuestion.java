package com.core.linkup.club.entity;

import com.core.linkup.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity(name = "club_question")
@NoArgsConstructor
@AllArgsConstructor
public class ClubQuestion extends BaseEntity {

    @ManyToOne
    private Club clubId;

    private String question;
    private Integer order;
}
