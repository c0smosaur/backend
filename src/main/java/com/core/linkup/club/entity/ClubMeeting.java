package com.core.linkup.club.entity;

import com.core.linkup.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "club_meeting")
@NoArgsConstructor
@AllArgsConstructor
public class ClubMeeting extends BaseEntity {

    @ManyToOne
    private Club clubId;

    private String title;
    private LocalDateTime date;
    private String meetingLocation;
    private Integer maxCapacity;
    private Integer fee;

}
