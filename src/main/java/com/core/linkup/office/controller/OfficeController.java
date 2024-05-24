package com.core.linkup.office.controller;


import com.core.linkup.common.response.BaseResponse;
import com.core.linkup.office.Service.OfficeService;
import com.core.linkup.office.request.OfficeSearchRequest;
import com.core.linkup.office.response.OfficeResponse;
import com.core.linkup.office.response.OfficeSearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/office")
public class OfficeController {

    private final OfficeService officeService;

//    @GetMapping("/search")
//    public BaseResponse<Page<OfficeResponse>> getAllOffice(
//            @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
//            @ModelAttribute OfficeSearchRequest request
//    ) {
//        Page<OfficeResponse> officeBuildings = officeService.getAllOffices(pageable, new OfficeSearchRequest(request));
//        return BaseResponse.response(officeBuildings);
//    }
    @GetMapping("/search")
    public BaseResponse<Page<OfficeResponse>> getAllOffice(
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
            @ModelAttribute OfficeSearchRequest request
    ) {
        Page<OfficeResponse> officeBuildings = officeService.getAllOffices(pageable, new OfficeSearchRequest(request));
        return BaseResponse.response(officeBuildings);
    }

    @GetMapping("/{officeBuildingId}")
    public BaseResponse<OfficeSearchResponse> getOneOffice(@PathVariable Long officeBuildingId) {
        OfficeSearchResponse officeBuilding = officeService.findOffice(officeBuildingId);
        return BaseResponse.response(officeBuilding);
    }
}
