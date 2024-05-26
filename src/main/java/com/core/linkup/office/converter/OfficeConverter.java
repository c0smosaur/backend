package com.core.linkup.office.converter;

import com.core.linkup.common.annotation.Converter;
import com.core.linkup.office.entity.OfficeBuilding;
import com.core.linkup.office.entity.OfficeDetail;
import com.core.linkup.office.response.OfficeDetailSearchResponse;
import com.core.linkup.office.response.OfficeResponse;
import com.core.linkup.office.response.OfficeSearchResponse;

@Converter
public class OfficeConverter {

    public OfficeResponse toOfficesResponse(OfficeBuilding office) {
        return OfficeResponse.builder()
                .id(office.getId())
                .location(office.getLocation())
                .region(office.getRegion())
                .city(office.getCity().getCityName())
                .street(office.getStreet())
                .address(office.getAddress())
                .capacity(office.getCapacity())
                .trafficInfo(office.getTrafficInfo())
                .latitude(office.getLatitude())
                .longitude(office.getLongitude())
                .images(office.getImages())
                .build();
    }

    public OfficeSearchResponse toOfficeResponse(OfficeBuilding office) {
        OfficeDetail officeDetail = office.getOfficeDetail();
        OfficeDetailSearchResponse detailSearchResponse = (officeDetail != null) ? toOfficeDetailDto(officeDetail) : null;
        return OfficeSearchResponse.builder()
                .id(office.getId())
                .location(office.getLocation())
                .region(office.getRegion())
                .city(office.getCity().getCityName())
                .street(office.getStreet())
                .address(office.getAddress())
                .capacity(office.getCapacity())
                .trafficInfo(office.getTrafficInfo())
                .latitude(office.getLatitude())
                .longitude(office.getLongitude())
                .images(office.getImages())
                .officeDetail(detailSearchResponse)
                .build();
    }

    public OfficeDetailSearchResponse toOfficeDetailDto(OfficeDetail detail){
        return OfficeDetailSearchResponse.builder()
                .location(detail.getLocation())
                .openDesk(detail.getOpenDesk())
                .partied(detail.getPartied())
                .isolationRoom(detail.getIsolationRoom())
                .monitorDesk(detail.getMonitorDesk())
                .conf4(detail.getConf4())
                .conf8(detail.getConf8())
                .studio(detail.getStudio())
                .lounge(detail.getLounge())
                .phoneBooth(detail.getPhoneBooth())
                .relaxRoom(detail.getRelaxRoom())
                .oaRoom(detail.getOaRoom())
                .snackbar(detail.getSnackbar())
                .coffee(detail.getCoffee())
                .postbox(detail.getPostbox())
                .parking(detail.getParking())
                .build();
    }

}
