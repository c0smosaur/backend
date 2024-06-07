package com.core.linkup.reservation.reservation.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class SeatSpaceResponse {
    private Long id;
    private String type;
    private String code;
    private boolean isAvailable;
    private List<String> am;
    private List<String> pm;
}
