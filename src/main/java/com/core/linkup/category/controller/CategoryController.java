package com.core.linkup.category.controller;

import com.core.linkup.common.entity.enums.CityType;
import com.core.linkup.common.entity.enums.ClubType;
import com.core.linkup.common.entity.enums.IndustryType;
import com.core.linkup.common.entity.enums.OccupationType;
import com.core.linkup.common.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/category")
public class CategoryController {

    @GetMapping("/city")
    public BaseResponse<List<String>> getCityEnumValues(){
        return BaseResponse.response(
                Stream.of(CityType.values())
                .map(CityType::getCityName)
                .collect(Collectors.toList()));
    }

    @GetMapping("/industry")
    public BaseResponse<List<String>> geIndustryEnumValues(){
        return BaseResponse.response(
                Stream.of(IndustryType.values())
                .map(IndustryType::getIndustryName)
                .collect(Collectors.toList()));
    }

    @GetMapping("/occupation")
    public BaseResponse<List<String>> getOccupationEnumValues(){
        return BaseResponse.response(
                Stream.of(OccupationType.values())
                        .map(OccupationType::getOccupationName)
                        .collect(Collectors.toList()));
    }

    @GetMapping("/club")
    public BaseResponse<List<String>> getClubEnumValues(){
        return BaseResponse.response(
                Stream.of(ClubType.values())
                        .map(ClubType::getClubCategoryName)
                        .collect(Collectors.toList()));
    }
}
