package com.core.linkup.common.entity;

import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@SuperBuilder
@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
public class BaseMembershipEntity extends BaseEntity{

    private String location;
    private Long price;
    private Integer duration;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
