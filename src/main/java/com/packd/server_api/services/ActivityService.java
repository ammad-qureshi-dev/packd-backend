package com.packd.server_api.services;

import com.packd.dtos.enums.ExceptionCode;
import com.packd.exceptions.ApiException;
import com.packd.server_api.controllers.dto.request.ActivityRequest;
import com.packd.server_api.mappers.ActivityMapper;
import com.packd.server_api.models.Activity;
import com.packd.server_api.models.Destination;
import com.packd.server_api.repositories.ActivityRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ActivityService {

    private final ActivityRepository activityRepository;

    private final DestinationService destinationService;
    private final AppUserService appUserService;

    // ToDo: When an activity spans across multiple days, make sure to add that activity for how many days long it is
    @Transactional
    public UUID createActivity(@NotNull UUID destinationId, @Valid ActivityRequest request) {
        var destination = destinationService.getDestinationById(destinationId);
        var activitiesOnDate = destination.getActivities()
                .stream()
                .filter(e -> e.getDate().equals(request.date()))
                .toList();

        var newActivity = ActivityMapper.mapRequestToEntity(request);

        if (Objects.isNull(newActivity.getEndTime())) {
            newActivity.setEndTime(newActivity.getDate().atTime(LocalTime.MAX));
        }

        if (newActivity.getStartTime().isAfter(newActivity.getEndTime())) {
            throw new ApiException(ExceptionCode.VALIDATION_FAILED, "End Time cannot be before Start Time");
        }

        if (overlapsOtherActivities(newActivity, activitiesOnDate)) {
            throw new ApiException(ExceptionCode.VALIDATION_FAILED, "Overlapping Activity times");
        }

        if (!activityWithinDestinationRange(newActivity, destination)) {
            throw new ApiException(ExceptionCode.VALIDATION_FAILED, "Activity not within destination range");
        }

        newActivity.setDestination(destination);
        activityRepository.save(newActivity);

        return newActivity.getId();
    }

    @Transactional
    public void updateActivity(UUID activityId, UUID destinationId, UUID appUserId, ActivityRequest request) {
        var activity = getActivityById(activityId);
        var destination = destinationService.getDestinationById(destinationId);
        var activities = destination.getActivities().stream().filter(e -> e.getDate().equals(request.date())).toList();
        var newActivity = ActivityMapper.mapRequestToEntity(request);
        var appUser = appUserService.getAppUserById(appUserId);

        // make sure newActivity doesnt overlap
        if (overlapsOtherActivities(newActivity, activities)) {
            throw new ApiException(ExceptionCode.VALIDATION_FAILED, "Overlapping Activity times");
        }

        activity.setLastUpdatedBy(appUser.getEmail());
        ActivityMapper.mapRequestToEntity(request, activity);
        activityRepository.save(activity);
    }

    public Activity getActivityById(UUID activityId) {
        var activity = activityRepository.findById(activityId);
        if (activity.isEmpty()) {
            throw new ApiException(ExceptionCode.ACTIVITY_NOT_FOUND, "Could not find activity");
        }

        return activity.get();
    }

    private static boolean activityWithinDestinationRange(Activity newActivity, Destination destination) {
        var activityDate = newActivity.getDate();

        return !activityDate.isBefore(destination.getFromDate()) &&
                !activityDate.isAfter(destination.getToDate());
    }

    private static boolean overlapsOtherActivities(Activity activityRequest, List<Activity> activities) {
        if (Objects.isNull(activities) || activities.isEmpty()) {
            return false;
        }

        var savedActivities = new ArrayList<>(activities);

        // If the incoming activity request persists, remove it from the persisted activities to ensure correct overlap checks for new dates/times for new activity request
        savedActivities.removeIf(e -> e.getId().equals(activityRequest.getId()));

        savedActivities.add(activityRequest);

        var ordered = savedActivities.stream().sorted(Comparator.comparing(Activity::getStartTime)).toList();

        var prev = ordered.getFirst();

        for (int i = 1; i < ordered.size(); i++) {
            var curr = ordered.get(i);

            if (prev.getEndTime().isAfter(curr.getStartTime())) {
               return true;
            }

            prev = curr;
        }

        return false;
    }
}
