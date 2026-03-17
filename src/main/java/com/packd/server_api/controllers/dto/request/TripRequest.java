package com.packd.server_api.controllers.dto.request;

import com.packd.server_api.validations.annotations.OverlappingDates;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.List;

import static com.packd.server_api.utils.Constants.BudgetConstants.MAX_TRIP_BUDGET_AMOUNT;
import static com.packd.server_api.utils.Constants.BudgetConstants.MIN_TRIP_BUDGET_AMOUNT;
import static com.packd.server_api.utils.Constants.DestinationConstants.MAX_DESTINATION_PER_TRIP;
import static com.packd.server_api.utils.Constants.DestinationConstants.MIN_DESTINATION_PER_TRIP;

public record TripRequest(
        @NotBlank
        @NotNull
        String title,

        @Min(value = MIN_TRIP_BUDGET_AMOUNT)
        @Max(value = MAX_TRIP_BUDGET_AMOUNT)
        BigDecimal totalBudget,

        @OverlappingDates
        @Size(min = MIN_DESTINATION_PER_TRIP, max = MAX_DESTINATION_PER_TRIP)
        List<DestinationRequest> destinations
) {
}
