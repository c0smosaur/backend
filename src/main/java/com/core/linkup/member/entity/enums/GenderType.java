package com.core.linkup.member.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GenderType {

    MALE("남성"),
    FEMALE("여성"),
    NEITHER("선택 안함"),;

    private final String description;

}
