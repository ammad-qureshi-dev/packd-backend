package com.packd.server_api.controllers.dto.request;

import com.packd.server_api.models.enums.ActivityCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ActivityRequest(
        @NotNull
        LocalDate date,

        @NotNull
        @NotBlank
        String title,

        String description,

        ActivityCategory activityCategory,

        @NotNull
        LocalDateTime startTime,

        LocalDateTime endTime
)
{
}
