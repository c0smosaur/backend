package com.core.linkup.club.converter;

import com.core.linkup.club.entity.Club;
import com.core.linkup.club.requset.ClubCreateRequest28;
import com.core.linkup.club.requset.ClubUpdateRequest;
import com.core.linkup.club.response.ClubSearchResponse28;
import com.core.linkup.common.annotation.Converter;
import com.core.linkup.common.entity.enums.ClubType;
import com.core.linkup.common.exception.BaseException;
import com.core.linkup.common.response.BaseResponseStatus;
import com.core.linkup.member.entity.Member;

@Converter
public class ClubConverter28 {

    public ClubSearchResponse28 toClubResponse(Club club) {
        return ClubSearchResponse28.builder()
                .id(club.getId())
                .title(club.getTitle())
                .introduction(club.getIntroduction())
                .clubType(club.getCategory())
                .recruitCount(club.getRecruitCount())
                .build();
    }

    public Club toClubEntity(ClubCreateRequest28 request) {
        ClubType category;
        try {
            category = ClubType.fromKor(String.valueOf(request.clubType()));
        } catch (IllegalArgumentException e) {
            throw new BaseException(BaseResponseStatus.INVALID_REQUEST);
        }
        Club club = Club.builder()
                .category(String.valueOf(category))
                .clubAccessibility(request.clubAccessibility())
                .title(request.title())
                .introduction(request.introduction())
                .recruitCount(request.recruitCount())
                .detailedIntroduction(request.detailedIntroduction())
                .applicationIntroduction(request.applicationIntroduction())
                .clubThumbnail(request.clubThumbnail())
                .build();

        //TODO : list로 question 받는거 해야 함, clubid르 null로 받아옴
//        Optional.ofNullable(request.clubQuestions()).orElse(List.of()).forEach(q -> {
//            ClubQuestion clubQuestion = ClubQuestion.builder()
//                    .question(q.getQuestion())
//                    .qorders(q.getQorders())
//                    .build();
//            clubQuestion.setClub(club);
//            club.getClubQuestions().add(clubQuestion);
//        });

        return club;
    }

    public Club updateClubEntity(Club updateClub, ClubUpdateRequest updateRequest) {
        ClubType category = ClubType.fromKor(String.valueOf(updateRequest.clubType()));
        return Club.builder()
                .id(updateClub.getId())
                .category(String.valueOf(category))
                .title(updateRequest.title())
                .introduction(updateRequest.introduction())
                .recruitCount(updateRequest.recruitCount())
                .detailedIntroduction(updateRequest.detailedIntroduction())
                .applicationIntroduction(updateRequest.applicationIntroduction())
                .clubThumbnail(updateRequest.clubThumbnail())
                .build();
    }

}
