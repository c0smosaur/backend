package com.core.linkup.club.clubnotice.converter;

import com.core.linkup.club.clubnotice.request.ClubNoticeRequest;
import com.core.linkup.club.clubnotice.response.ClubNoticeResponse;
import com.core.linkup.club.entity.Club;
import com.core.linkup.club.entity.ClubNotice;
import com.core.linkup.common.annotation.Converter;

@Converter
public class ClubNoticeConverter {

    public ClubNotice toClubNoticeEntity(ClubNoticeRequest request, Club club) {
        return ClubNotice.builder()
                .club(club)
                .title(request.title())
                .content(request.content())
                .type(request.type())
                .build();
    }

    public ClubNoticeResponse toClubNoticeResponse(ClubNotice clubNotice) {
        return ClubNoticeResponse.builder()
                .noticeId(clubNotice.getId())
                .title(clubNotice.getTitle())
                .content(clubNotice.getContent())
                .type(clubNotice.getType())
                .build();
    }

}
