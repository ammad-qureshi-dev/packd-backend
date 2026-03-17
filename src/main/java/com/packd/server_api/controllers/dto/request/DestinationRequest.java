package com.packd.server_api.controllers.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record DestinationRequest(
        @NotNull
        @NotBlank
        String place,

        @NotNull
        LocalDate fromDate,

        @NotNull
        LocalDate toDate
) {
}
