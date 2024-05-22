package com.core.linkup.office.controller;


import com.core.linkup.common.response.BaseResponse;
import com.core.linkup.office.Service.OfficeService;
import com.core.linkup.office.entity.OfficeBuilding;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/office")
public class OfficeController {

    private final OfficeService officeService;

    @GetMapping("/search")
    public BaseResponse<List<OfficeBuilding>> getAllOffice(){
        List<OfficeBuilding> officeBuildings = officeService.getAllOffice();
        return BaseResponse.response(officeBuildings);
    }

    @GetMapping("/{officeBuildingId}")
    public BaseResponse<Optional<OfficeBuilding>> getOneOffice(@PathVariable Long officeBuildingId){
        Optional<OfficeBuilding> officeBuilding = officeService.getOfficeById(officeBuildingId);
        return BaseResponse.response(officeBuilding);
    }
}
