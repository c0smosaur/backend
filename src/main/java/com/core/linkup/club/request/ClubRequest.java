package com.core.linkup.club.request;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClubRequest {

    private Long officeBuildingId;
    private String category;
    private Boolean clubAccessibility;
    private Integer recruitCount;
    private String title;
    private String introduction;
    private String detailedIntroduction;
    private String applicationIntroduction;
    private String clubThumbnail;

}
