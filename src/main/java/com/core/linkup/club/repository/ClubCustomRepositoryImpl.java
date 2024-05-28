package com.core.linkup.club.repository;

import com.core.linkup.club.entity.Club;
import com.core.linkup.club.entity.QClub;
import com.core.linkup.club.request.ClubRequest;
import com.core.linkup.club.response.ClubsResponse;
import com.core.linkup.common.entity.enums.CategoryType;
import com.core.linkup.common.exception.BaseException;
import com.core.linkup.member.entity.Member;
import com.core.linkup.member.repository.MemberRepository;
import com.core.linkup.office.entity.OfficeBuilding;
import com.core.linkup.office.repository.OfficeRepository;
import com.core.linkup.security.MemberDetails;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.core.linkup.common.response.BaseResponseStatus.INVALID_OFFICEBUILDING_ID;
import static com.core.linkup.common.response.BaseResponseStatus.UNREGISTERD_MEMBER;

@Repository
@RequiredArgsConstructor
public class ClubCustomRepositoryImpl implements ClubCustomRepository {

    private final JPAQueryFactory queryFactory;
    private final MemberRepository memberRepository;
    private final OfficeRepository officeRepository;
    private final ClubRepository clubRepository;

    @Override
    public List<ClubsResponse> clubRegister(MemberDetails memberDetails, ClubRequest clubRequest) {
        if (memberDetails != null) {
            Long memberId = memberDetails.getId();
            Member member = memberRepository.findById(memberId)
                    .orElseThrow(() -> new BaseException(UNREGISTERD_MEMBER));

            OfficeBuilding officeBuilding = officeRepository.findById(clubRequest.getOfficeBuildingId())
                    .orElseThrow(() -> new BaseException(INVALID_OFFICEBUILDING_ID));

            Club club = createClub(member, officeBuilding, clubRequest);
            Club savedClub = clubRepository.save(club);

            QClub qClub = QClub.club;
            List<Club> clubs = queryFactory
                    .selectFrom(qClub)
                    .where(
                            qClub.officeBuilding.id.eq(clubRequest.getOfficeBuildingId()),
                            qClub.member.id.eq(memberId),
                            qClub.clubAccessibility.eq(clubRequest.getClubAccessibility()),
                            qClub.category.eq(clubRequest.getCategory()),
                            qClub.introduction.eq(clubRequest.getIntroduction()),
                            qClub.detailedIntroduction.eq(clubRequest.getDetailedIntroduction()),
                            qClub.title.eq(clubRequest.getTitle()),
                            qClub.clubThumbnail.eq(clubRequest.getClubThumbnail()),
                            qClub.applicationIntroduction.eq(clubRequest.getApplicationIntroduction())
                    )
                    .fetch();

            return clubs.stream()
                    .map(ClubsResponse::new)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
//        return savedClub;
    }

    private Club createClub(Member member, OfficeBuilding officeBuilding, ClubRequest clubRequest) {
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


//    @Override
//    @Transactional
//    public List<ClubsResponse> createClub(MemberDetails memberDetails, ClubRequest clubRequest) {
//        Member member = memberRepository.findById(memberDetails.getId()).orElseThrow(() -> new IllegalArgumentException("Invalid member ID"));
//
//        // Create a new Club entity
//        Club club = new Club();
//        club.setName(clubRequest.getName());
//        club.setDescription(clubRequest.getDescription());
//        club.setMember(member);
//
//        // Save the new club entity to the database
//        Club savedClub = clubCusRepository.save(club);
//
//        // Convert the saved club entity to ClubsResponse DTO
//        ClubsResponse response = new ClubsResponse(savedClub.getId(), savedClub.getName(), savedClub.getDescription());
//
//        // Return a list with the single response
//        return Collections.singletonList(response);

//        QClub club = QClub.club;
//        QOfficeBuilding officeBuilding = QOfficeBuilding.officeBuilding;
//
//        Member member = entityManager.find(Member.class, memberDetails.getMember().getId());
//        OfficeBuilding officeBuildingEntity = entityManager.find(OfficeBuilding.class, clubRequest.getOfficeBuildingId());
//
//        CategoryType categoryType = CategoryType.fromKor(clubRequest.getCategory());
//
//        Club newClub = new Club();
//        newClub.setMember(member);
//        newClub.setOfficeBuilding(officeBuildingEntity);
//        newClub.setClubAccessibility(clubRequest.getClubAccessibility());
//        newClub.setCategory(String.valueOf(categoryType));
//        newClub.setRecruitCount(clubRequest.getRecruitCount());
//        newClub.setIntroduction(clubRequest.getIntroduction());
//        newClub.setDetailedIntroduction(clubRequest.getDetailedIntroduction());
//        newClub.setTitle(clubRequest.getTitle());
//        newClub.setClubThumbnail(clubRequest.getClubThumbnail());
//        newClub.setApplicationIntroduction(clubRequest.getApplicationIntroduction());
//
//        entityManager.persist(newClub);
//
//        List<Club> query = queryFactory
//                .selectFrom(club)
//                .where(setBooleanBuilder(clubRequest, club))
//                .join(club.officeBuilding, officeBuilding)
//                .fetchJoin()
//                .fetch();
//
//        List<ClubsResponse> clubsResponse = query.stream()
//                .map(c -> new ClubsResponse(
//                        c.getId(),
//                        c.getMember().getId(),
//                        c.getOfficeBuilding().getId(),
//                        c.getClubAccessibility(),
//                        c.getCategory(),
//                        c.getRecruitCount(),
//                        c.getIntroduction(),
//                        c.getDetailedIntroduction(),
//                        c.getTitle(),
//                        c.getClubThumbnail(),
//                        c.getApplicationIntroduction()))
//                .collect(Collectors.toList());
//
//        return clubsResponse;
//
//    }
//
//    private BooleanBuilder setBooleanBuilder(ClubRequest clubRequest, QClub club) {
//
//        BooleanBuilder builder = new BooleanBuilder();
//        CategoryType categoryType = CategoryType.fromKor(clubRequest.getCategory());
//
//        if (clubRequest.getClubAccessibility() != null) {
//            builder.and(club.clubAccessibility.eq(clubRequest.getClubAccessibility()));
//        }
//        if (clubRequest.getCategory() != null) {
//            builder.and(club.category.eq(String.valueOf(categoryType)));
//        }
//        if (clubRequest.getRecruitCount() != null) {
//            builder.and(club.recruitCount.eq(clubRequest.getRecruitCount()));
//        }
//        if (clubRequest.getIntroduction() != null){
//            builder.and(club.introduction.eq(clubRequest.getIntroduction()));
//        }
//        if (clubRequest.getDetailedIntroduction() != null){
//            builder.and(club.detailedIntroduction.eq(clubRequest.getDetailedIntroduction()));
//        }
//        if (clubRequest.getTitle() != null){
//            builder.and(club.title.eq(clubRequest.getTitle()));
//        }
//        if (clubRequest.getClubThumbnail() != null){
//            builder.and(club.clubThumbnail.eq(clubRequest.getClubThumbnail()));
//        }
//        if (clubRequest.getApplicationIntroduction() != null){
//            builder.and(club.applicationIntroduction.eq(clubRequest.getApplicationIntroduction()));
//        }
//
//        return builder;
//
//    }
}
