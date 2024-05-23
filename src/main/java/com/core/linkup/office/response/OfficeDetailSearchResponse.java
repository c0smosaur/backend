package com.core.linkup.office.response;

public record OfficeDetailSearchResponse(
        Long id,
        String location,
        Boolean openDesk,
        Boolean partied,
        Boolean isolationRoom,
        Boolean monitorDesk,
        Boolean conf4,
        Boolean conf8,
        Boolean studio,
        Boolean lounge,
        Boolean phoneBooth,
        Boolean relaxRoom,
        Boolean oaRoom,
        Boolean snackbar,
        Boolean coffee,
        Boolean postbox,
        Boolean parking

) {

}
