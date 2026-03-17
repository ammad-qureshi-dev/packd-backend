package com.packd.server_api.mappers;

import com.packd.server_api.controllers.dto.request.ActivityRequest;
import com.packd.server_api.controllers.dto.response.ActivityDto;
import com.packd.server_api.models.Activity;

public class ActivityMapper {
    public static Activity mapRequestToEntity(ActivityRequest request) {
        return Activity.builder()
                .activityCategory(request.activityCategory())
                .date(request.date())
                .description(request.description())
                .title(request.title())
                .endTime(request.endTime())
                .startTime(request.startTime())
                .build();
    }

    public static void mapRequestToEntity(ActivityRequest request, Activity activity) {
        activity.setActivityCategory(request.activityCategory());
        activity.setDate(request.date());
        activity.setDescription(request.description());
        activity.setTitle(request.title());
        activity.setEndTime(request.endTime());
        activity.setStartTime(request.startTime());
    }

    public static ActivityDto mapEntityToDto(Activity entity) {
        return new ActivityDto(
                entity.getId(),
                entity.getDate(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getActivityCategory(),
                entity.getStartTime(),
                entity.getEndTime()
        );
    }
}
