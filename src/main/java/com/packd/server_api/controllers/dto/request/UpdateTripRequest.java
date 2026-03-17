package com.packd.server_api.controllers.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record UpdateTripRequest(
        @NotBlank
        @NotNull
        String title,

        @Min(value = 0)
        @Max(value = 1000000)
        BigDecimal totalBudget
) {
}
