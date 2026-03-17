package com.packd.server_api.controllers.dto.response;

import com.packd.server_api.models.enums.ActivityCategory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record ActivityDto(
        UUID acitivityId,
        LocalDate date,
        String title,
        String description,
        ActivityCategory activityCategory,
        LocalDateTime startTime,
        LocalDateTime endtime
) {
}
