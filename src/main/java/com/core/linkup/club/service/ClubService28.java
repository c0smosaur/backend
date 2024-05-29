package com.core.linkup.club.service;

import com.core.linkup.club.converter.ClubConverter28;
import com.core.linkup.club.entity.Club;
import com.core.linkup.club.repository.ClubRepository28;
import com.core.linkup.club.requset.ClubCreateRequest28;
import com.core.linkup.club.requset.ClubUpdateRequest;
import com.core.linkup.club.response.ClubSearchResponse28;
import com.core.linkup.common.exception.BaseException;
import com.core.linkup.common.response.BaseResponseStatus;
import com.core.linkup.member.entity.Member;
import com.core.linkup.security.MemberDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClubService28 {

    private final ClubRepository28 clubRepository;
    private final ClubConverter28 clubConverter;

    public ClubSearchResponse28 findClub(Long clubId){
        return clubRepository.findById(clubId).map(clubConverter::toClubResponse)
                .orElseThrow(()-> new BaseException(BaseResponseStatus.INVALID_CLUB_ID));
    }

    public ClubSearchResponse28 createClub(MemberDetails member, ClubCreateRequest28 request) {
        if (member == null) { //로그인 되어 있는 경우 체크 뺄꺼임
            throw new BaseException(BaseResponseStatus.UNREGISTERD_MEMBER);
        }

        Long memberId = member.getId();

        Club club = clubConverter.toClubEntity(request);
//        clubConverter.setMember(club, memberId);

        Club savedClub = clubRepository.save(club);

        return clubConverter.toClubResponse(savedClub);
    }

    public ClubSearchResponse28 updateClub(MemberDetails member, Long clubId, ClubUpdateRequest updateRequest) {

        Club existingClub = clubRepository.findById(clubId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_CLUB_ID));
        Club updatedClub = clubConverter.updateClubEntity(existingClub, updateRequest);
        Club savedClub = clubRepository.save(updatedClub);
        return clubConverter.toClubResponse(savedClub);
    }

    public void delete(MemberDetails member, Long clubId) {
        clubRepository.deleteById(clubId);
    }
    
}
