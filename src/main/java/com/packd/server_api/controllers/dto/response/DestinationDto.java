package com.packd.server_api.controllers.dto.response;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record DestinationDto(
        UUID destinationId,
        String place,
        LocalDate fromDate,
        LocalDate toDate,
        List<ActivityDto> activities
) {
}
