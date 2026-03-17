package com.packd.server_api.controllers.dto.response;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record TripDto(
        UUID tripId,
        String title,
        BigDecimal totalBudget,
        List<DestinationDto> destinations
) {
}
