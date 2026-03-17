package com.packd.server_api.controllers;

import com.packd.server_api.controllers.dto.request.TripRequest;
import com.packd.server_api.controllers.dto.request.UpdateTripRequest;
import com.packd.server_api.controllers.dto.response.ApiResponse;
import com.packd.server_api.controllers.dto.response.CreateTripResponse;
import com.packd.server_api.controllers.dto.response.TripDto;
import com.packd.server_api.models.BaseEntity;
import com.packd.server_api.models.Trip;
import com.packd.server_api.services.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/trip")
@RequiredArgsConstructor
public class TripController extends BaseController {

    @Value("${TEMP_APP_USER_ID}")
    private UUID tempUserId;

    private final TripService tripService;

    @PostMapping
    public ResponseEntity<ApiResponse<CreateTripResponse>> createTrip(@RequestBody TripRequest request) {
        var tripId = tripService.createTrip(tempUserId, request);
        return getResponse(HttpStatus.CREATED, tripId);
    }

    @PutMapping("/{tripId}")
    public ResponseEntity<ApiResponse<UUID>> updateTrip(@PathVariable UUID tripId, @RequestBody UpdateTripRequest request) {
        tripService.updateTrip(tempUserId, tripId, request);
        return getResponse(HttpStatus.OK, tripId);
    }

    @GetMapping("/{tripId}")
    public ResponseEntity<ApiResponse<TripDto>> getTripById(@PathVariable UUID tripId) {
        return getResponse(HttpStatus.OK, tripService.getTrip(tripId));
    }
}
