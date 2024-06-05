package com.core.linkup.club.club.entity;

import com.core.linkup.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity(name = "club_question")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@SuperBuilder
public class ClubQuestion extends BaseEntity {

    @ManyToOne
    private Club club;

    private String question;
    private Integer qorders;

}
