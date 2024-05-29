package com.core.linkup.club.service;

import com.core.linkup.club.converter.ClubConverter;
import com.core.linkup.club.entity.Club;
import com.core.linkup.club.repository.ClubRepository;
import com.core.linkup.club.requset.ClubCreateRequest;
import com.core.linkup.club.requset.ClubSearchRequest;
import com.core.linkup.club.requset.ClubUpdateRequest;
import com.core.linkup.club.response.ClubSearchResponse;
import com.core.linkup.common.exception.BaseException;
import com.core.linkup.common.response.BaseResponseStatus;
import com.core.linkup.security.MemberDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClubService {

    private final ClubRepository clubRepository;
    //    private final ClubCustomRepository clubCustomRepository;
    private final ClubConverter clubConverter;

    public ClubSearchResponse findClub(Long clubId) {
        return clubRepository.findById(clubId).map(clubConverter::toClubResponse)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_CLUB_ID));
    }

    public Page<ClubSearchResponse> findClubs(Pageable pageable, ClubSearchRequest request) {
        return clubRepository.findSearchClubs(request, pageable)
                .map(clubConverter::toClubResponse);
    }

    public ClubSearchResponse createClub(MemberDetails member, ClubCreateRequest request) {
        Long memberId = getMemberId(member);
        Club club = clubConverter.toClubEntity(request, memberId);
        Club savedClub = clubRepository.save(club);

        return clubConverter.toClubResponse(savedClub);
    }

    public ClubSearchResponse updateClub(MemberDetails member, Long clubId, ClubUpdateRequest updateRequest) {
        Long memberId = getMemberId(member);
        Club existingClub = clubRepository.findById(clubId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_CLUB_ID));

        if (!existingClub.getMember().getId().equals(memberId)) {
            throw new BaseException(BaseResponseStatus.INVALID_MEMBER);
        }

        Club updatedClub = clubConverter.updateClubEntity(existingClub, updateRequest, memberId);
        Club savedClub = clubRepository.save(updatedClub);
        return clubConverter.toClubResponse(savedClub);
    }

    private static Long getMemberId(MemberDetails member) {
        if (member == null) {
            throw new BaseException(BaseResponseStatus.UNREGISTERD_MEMBER);
        }

        Long memberId = member.getId();
        return memberId;
    }

    public void delete(MemberDetails member, Long clubId) {
        clubRepository.deleteById(clubId);
    }

    //멤버 1이 등록한 소모임 조회
//    @Transactional(readOnly = true)
//    public List<ClubSearchResponse28> getClubsByMemberId(Long memberId) {
//        List<Club> clubs = clubCustomRepository.findClubsByMemberId(memberId);
//        return clubs.stream()
//                .map(clubConverter::toClubResponse)
//                .collect(Collectors.toList());
//    }
}
