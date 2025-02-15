package com.core.linkup.office.service;

import com.core.linkup.common.exception.BaseException;
import com.core.linkup.office.converter.OfficeConverter;
import com.core.linkup.office.repository.OfficeRepository;
import com.core.linkup.office.request.OfficeSearchControllerRequest;
import com.core.linkup.office.response.OfficeResponse;
import com.core.linkup.office.response.OfficeSearchResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.core.linkup.common.response.BaseResponseStatus.INVALID_OFFICEBUILDING_ID;

@Service
@Slf4j
@RequiredArgsConstructor
public class OfficeService {

    private final OfficeRepository officeRepository;

    private final OfficeConverter officeConverter;

    public Page<OfficeResponse> findOffices(Pageable pageable, OfficeSearchControllerRequest request) {
        return officeRepository.searchPage(request, pageable)
                .map(officeConverter::toOfficesResponse);
    }

    public OfficeSearchResponse findOffice(Long officeBuildingId) {
        return officeRepository.findById(officeBuildingId).map(officeConverter::toOfficeResponse)
                .orElseThrow(() -> new BaseException(INVALID_OFFICEBUILDING_ID));

    }
}

