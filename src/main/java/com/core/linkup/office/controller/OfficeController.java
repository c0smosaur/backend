package com.core.linkup.office.controller;


import com.core.linkup.common.response.BaseResponse;
import com.core.linkup.office.request.OfficeSearchControllerRequest;
import com.core.linkup.office.response.OfficeResponse;
import com.core.linkup.office.response.OfficeSearchResponse;
import com.core.linkup.office.service.OfficeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/office")
@Tag(name = "Search", description = "지점 탐색 관련 API")
public class OfficeController {

    private final OfficeService officeService;

    @GetMapping("/search")
    public BaseResponse<Page<OfficeResponse>> getAllOffices(
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
            @ModelAttribute OfficeSearchControllerRequest request
    ) {
        Page<OfficeResponse> officeResponse = officeService.findOffices(pageable, request);
        return BaseResponse.response(officeResponse);
    }

    @GetMapping("/{officeBuildingId}")
    public BaseResponse<OfficeSearchResponse> getOneOffice(@PathVariable Long officeBuildingId) {
        OfficeSearchResponse officeBuilding = officeService.findOffice(officeBuildingId);
        return BaseResponse.response(officeBuilding);
    }
}
