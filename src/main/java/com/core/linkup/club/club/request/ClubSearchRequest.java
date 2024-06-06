package com.core.linkup.club.club.request;

import lombok.*;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Setter
public class ClubSearchRequest {

    private Long officeBuildingId;
    private String clubType;
//    private List<String> clubType;

}
