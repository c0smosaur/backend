package com.core.linkup.office.repository;

import com.core.linkup.office.entity.OfficeBuilding;
import com.core.linkup.office.request.OfficeSearchControllerRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OfficeSearchRepository {
    Page<OfficeBuilding> searchPage(OfficeSearchControllerRequest request, Pageable pageable);
}
