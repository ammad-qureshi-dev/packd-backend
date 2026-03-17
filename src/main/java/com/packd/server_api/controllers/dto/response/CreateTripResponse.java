package com.packd.server_api.controllers.dto.response;

import jakarta.validation.constraints.*;

import java.util.List;
import java.util.UUID;

import static com.packd.server_api.utils.Constants.DestinationConstants.MAX_DESTINATION_PER_TRIP;
import static com.packd.server_api.utils.Constants.DestinationConstants.MIN_DESTINATION_PER_TRIP;

public record CreateTripResponse(
        @NotNull UUID tripId,

        @Size(min = MIN_DESTINATION_PER_TRIP, max = MAX_DESTINATION_PER_TRIP)
        List<UUID> destinationIds
) {
}
