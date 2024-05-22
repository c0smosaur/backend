package com.core.linkup.office.Service;

import com.core.linkup.office.entity.OfficeBuilding;
import com.core.linkup.office.repository.OfficeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class OfficeService {

    private final OfficeRepository officeRepository;


    public List<OfficeBuilding> getAllOffice() {
        return officeRepository.findAll();
    }

    public Optional<OfficeBuilding> getOfficeById(Long officeBuildingId) {
        return officeRepository.findById(officeBuildingId);
    }
}

