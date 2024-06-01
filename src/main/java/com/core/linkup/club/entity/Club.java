package com.core.linkup.club.entity;

import com.core.linkup.common.entity.BaseEntity;
import com.core.linkup.member.entity.Member;
import com.core.linkup.office.entity.OfficeBuilding;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "club")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@SuperBuilder
public class Club extends BaseEntity {

//    @ManyToOne
//    @JoinColumn(name = "office_building_id")
//    private OfficeBuilding officeBuilding;
//
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private Boolean clubAccessibility;
    private String category;
    private Integer recruitCount;
    private String title;
    private String introduction;
    private String detailedIntroduction;
    private String clubThumbnail;
    private String applicationIntroduction;

    @OneToMany
    @Builder.Default
    private List<ClubQuestion> clubQuestions = new ArrayList<>();

}
