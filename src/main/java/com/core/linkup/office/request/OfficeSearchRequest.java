package com.core.linkup.office.request;

import com.core.linkup.common.entity.enums.CityType;
import com.core.linkup.common.entity.enums.IndustryType;
import lombok.*;

@Getter
@Builder
@RequiredArgsConstructor
public class OfficeSearchRequest {

    private final String region;
    private final CityType city;
    private final String street;

//TODO
//    private final IndustryType industry;
    //    private final OccupationType occupation;

    public OfficeSearchRequest(OfficeSearchControllerRequest request) {
        this.region = request.region();
        this.city = CityType.fromCityName(request.city());
        this.street = request.street();
//        this.industry = IndustryType.fromKor(request.industry());
//        this.occupation = OccupationType.fromKor(request.occupation());
    }
}


