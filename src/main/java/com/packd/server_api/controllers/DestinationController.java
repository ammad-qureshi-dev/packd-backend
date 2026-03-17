package com.packd.server_api.controllers;

import com.packd.server_api.controllers.dto.request.DestinationRequest;
import com.packd.server_api.controllers.dto.response.ApiResponse;
import com.packd.server_api.services.DestinationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/destination")
@RequiredArgsConstructor
public class DestinationController extends BaseController {

    @Value("${TEMP_APP_USER_ID}")
    private UUID tempUserId;

    private final DestinationService destinationService;

    @PostMapping("/add-to-trip/{tripId}")
    public ResponseEntity<ApiResponse<UUID>> addDestinationToTrip(@PathVariable UUID tripId, @RequestBody DestinationRequest request) {
        var destinationId = destinationService.addDestinationToTrip(request, tripId);
        return getResponse(HttpStatus.CREATED, destinationId);
    }

    @PutMapping("/{destinationId}/trip/{tripId}")
    public ResponseEntity<ApiResponse<UUID>> updateDestination(@PathVariable UUID tripId, @PathVariable UUID destinationId, @RequestBody DestinationRequest request) {
        destinationService.updateDestination(tempUserId, tripId, destinationId, request);
        return getResponse(HttpStatus.OK, destinationId);
    }
}
