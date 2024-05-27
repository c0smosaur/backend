package com.core.linkup.club.response;

import com.core.linkup.club.entity.Club;;

public class ClubsResponse {
    private Long id;
    private String clubThumbnail;
    private String detailedIntroduction;
    private String title;
    private int recruitCount;

    public ClubsResponse(Club club) {
        this.id = club.getId();
        this.clubThumbnail = club.getClubThumbnail();
        this.detailedIntroduction = club.getDetailedIntroduction();
        this.title = club.getTitle();
        this.recruitCount = club.getRecruitCount();
    }
}
