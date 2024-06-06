package com.core.linkup.club.clubmeeting.entity;

import com.core.linkup.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity(name = "club_meeting")
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ClubMeeting extends BaseEntity {

    private Long clubId;
    @Setter
    private Long memberId;
    private String title;
    private LocalDateTime date;
    private String meetingLocation;
    private Integer maxCapacity;
    private Integer fee;

}
