package com.core.linkup.club.requset;

import lombok.*;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Setter
public class ClubSearchRequest {

    private Long officeBuildingId;
    private String clubType;

}
