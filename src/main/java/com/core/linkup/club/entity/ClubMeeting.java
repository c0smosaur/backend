package com.core.linkup.club.entity;

import com.core.linkup.club.entity.enums.NotificationType;
import com.core.linkup.common.entity.BaseEntity;
import com.core.linkup.member.entity.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity(name = "club_meeting")
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ClubMeeting extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member; //소모임을 작성한 사람

    private String title;
    private LocalDateTime date;
    private String meetingLocation;
    private Integer maxCapacity;
    private Integer fee;
    private NotificationType notificationType; //Boolean으로 할까?

}
