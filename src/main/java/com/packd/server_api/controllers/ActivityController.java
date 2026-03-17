package com.packd.server_api.controllers;

import com.packd.server_api.controllers.dto.request.ActivityRequest;
import com.packd.server_api.controllers.dto.response.ApiResponse;
import com.packd.server_api.services.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/api/v1/activity")
@RestController
@RequiredArgsConstructor
public class ActivityController extends BaseController {

    @Value("${TEMP_APP_USER_ID}")
    private UUID tempUserId;

    private final ActivityService activityService;

    @PostMapping("/add-to-destination/{destinationId}")
    public ResponseEntity<ApiResponse<UUID>> createActivity(@PathVariable UUID destinationId, @RequestBody ActivityRequest request) {
        var activityId = activityService.createActivity(destinationId, request);
        return getResponse(HttpStatus.CREATED, activityId);
    }

    @PutMapping("/{activityId}/destination/{destinationId}")
    public ResponseEntity<ApiResponse<UUID>> updateActivity(@PathVariable UUID activityId, @PathVariable UUID destinationId, @RequestBody ActivityRequest request) {
        activityService.updateActivity(activityId, destinationId, tempUserId, request);
        return getResponse(HttpStatus.OK, activityId);
    }
}
