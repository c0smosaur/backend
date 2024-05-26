package com.core.linkup.office.service;

import com.core.linkup.common.exception.BaseException;
import com.core.linkup.office.entity.OfficeBuilding;
import com.core.linkup.office.repository.OfficeRepository;
import com.core.linkup.office.request.OfficeSearchRequest;
import com.core.linkup.office.response.OfficeResponse;
import com.core.linkup.office.response.OfficeSearchResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.core.linkup.common.response.BaseResponseStatus.NOTFOUND_OFFICEBUILDING_ID;

@Service
@Slf4j
@RequiredArgsConstructor
public class OfficeService {

    private final OfficeRepository officeRepository;

//    public Page<OfficeResponse> getAllOffices(Pageable pageable, OfficeSearchRequest request) {
//        return officeRepository.searchPage(request, pageable).map(OfficeBuilding::toDto);
//    }
    public Page<OfficeResponse> getAllOffices(Pageable pageable, OfficeSearchRequest request) {
        return officeRepository.searchPage(request, pageable).map(OfficeBuilding::toDto);
    }

    public OfficeSearchResponse findOffice(Long officeBuildingId) {
        return officeRepository.findById(officeBuildingId).map(OfficeBuilding::toDetailDto)
                .orElseThrow(() -> new BaseException(NOTFOUND_OFFICEBUILDING_ID));
    }
}

