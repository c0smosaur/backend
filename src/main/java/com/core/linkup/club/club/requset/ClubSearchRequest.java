package com.core.linkup.club.club.requset;

import lombok.*;

import java.util.List;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Setter
public class ClubSearchRequest {

    private Long officeBuildingId;
    private String clubType;
//    private List<String> clubType;

}
