package com.core.linkup.club.service;

import com.core.linkup.club.entity.Club;
import com.core.linkup.club.repository.ClubRepository;
import com.core.linkup.club.request.ClubRequest;
import com.core.linkup.club.response.ClubsResponse;
import com.core.linkup.common.entity.enums.CategoryType;
import com.core.linkup.common.exception.BaseException;
import com.core.linkup.member.entity.Member;
import com.core.linkup.member.repository.MemberRepository;
import com.core.linkup.office.entity.OfficeBuilding;
import com.core.linkup.office.repository.OfficeRepository;
import com.core.linkup.security.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

import static com.core.linkup.common.response.BaseResponseStatus.INVALID_OFFICEBUILDING_ID;
import static com.core.linkup.common.response.BaseResponseStatus.UNREGISTERD_MEMBER;

@Service
@Transactional
@RequiredArgsConstructor
public class ClubService {

    private final ClubRepository clubRepository;
    private final OfficeRepository officeRepository;
    private final MemberRepository memberRepository;

    //    public void clubRegister(MemberDetails memberDetails, ClubRequest clubRequest) {
    public List<ClubsResponse> clubRegister(MemberDetails memberDetails, ClubRequest clubRequest) {
        if (memberDetails != null) {
            Long memberId = memberDetails.getId();
            Member member = memberRepository.findById(memberId)
                    .orElseThrow(() -> new BaseException(UNREGISTERD_MEMBER));

            //TODO : officeId는 멤버십을 결재 한 후에 가져오는 것을 확인
            //TODO : 질문도 연관 관계 해서 가져오는 것을 해야함
            OfficeBuilding officeBuilding = officeRepository.findById(clubRequest.getOfficeBuildingId())
                    .orElseThrow(() -> new BaseException(INVALID_OFFICEBUILDING_ID));

//            Club club = createClub(member, clubRequest);
            Club club = createClub(member, officeBuilding, clubRequest);
            Club savedClub = clubRepository.save(club);
            return List.of(new ClubsResponse(savedClub));
        }
        return Collections.emptyList();
    }

        private Club createClub(Member member, OfficeBuilding officeBuilding, ClubRequest clubRequest){
//    private Club createClub(Member member, ClubRequest clubRequest) {
        CategoryType categoryType = CategoryType.fromKor(clubRequest.getCategory());

        return new Club(
                officeBuilding,
                member,
                clubRequest.getClubAccessibility(),
                categoryType.getInCategoryInKor(),
                clubRequest.getRecruitCount(),
                clubRequest.getIntroduction(),
                clubRequest.getDetailedIntroduction(),
                clubRequest.getTitle(),
                clubRequest.getClubThumbnail(),
                clubRequest.getApplicationIntroduction()
        );
    }
}
