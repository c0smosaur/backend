package com.core.linkup.office.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SeatSpaceType {

    OPEN_DESK("오픈데스크"),
    FOCUS_DESK("포커스데스크"),
    ISOLATION_ROOM("1인실"),
    MONITOR_DESK("모니터데스크"),
    CONF4("미팅룸(4인)"),
    CONF8("미팅룸(8인)"),
    CONFERENCE_ROOM("컨퍼런스룸"),
    STUDIO("스튜디오"),
    DESIGNATED_SEAT("지정좌석"),
    COMPANY_EXCLUSIVE_SEAT("기업 전용 지정좌석")
    ;

    private final String typeName;

    public static SeatSpaceType fromKor(String seatTypeName) {
        for (SeatSpaceType type : SeatSpaceType.values()) {
            if (type.getTypeName().equals(seatTypeName)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No matching seat space type for: " + seatTypeName);
    }
}
