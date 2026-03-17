package com.packd.server_api.services;

import com.packd.server_api.controllers.dto.request.TripRequest;
import com.packd.server_api.controllers.dto.request.UpdateTripRequest;
import com.packd.server_api.controllers.dto.response.CreateTripResponse;
import com.packd.server_api.controllers.dto.response.DestinationDto;
import com.packd.server_api.controllers.dto.response.TripDto;
import com.packd.server_api.exceptions.ApiException;
import com.packd.server_api.exceptions.ExceptionCode;
import com.packd.server_api.mappers.ActivityMapper;
import com.packd.server_api.mappers.DestinationMapper;
import com.packd.server_api.mappers.TripMapper;
import com.packd.server_api.models.Trip;
import com.packd.server_api.repositories.TripRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TripService {

    private final TripRepository tripRepository;

    private final AppUserService appUserService;
    private final DestinationService destinationService;

    @Transactional
    public CreateTripResponse createTrip(UUID appUserId, TripRequest request) {
        if (tripRepository.existsByName(appUserId, request.title())) {
            throw new ApiException(ExceptionCode.TRIP_ALREADY_EXISTS, "Trip with that name already exists");
        }

        var owner = appUserService.getAppUserById(appUserId);

        var trip = Trip.builder()
                .owner(owner)
                .title(request.title())
                .totalBudget(request.totalBudget())
                .build();

        trip.setLastUpdatedBy(owner.getEmail());

        tripRepository.save(trip);

        var destinationIds = destinationService.addDestinationsToTrip(request.destinations(), trip);

        return new CreateTripResponse(trip.getId(), destinationIds);
    }

    @Transactional
    public void updateTrip(UUID appUserId, UUID tripId, UpdateTripRequest request) {
        var trip = getTripById(tripId);

        if (!request.title().equals(trip.getTitle())) {
            trip.setTitle(request.title());
        }

        if (!request.totalBudget().equals(trip.getTotalBudget())) {
            trip.setTotalBudget(request.totalBudget());
        }

        var appUser = appUserService.getAppUserById(appUserId);
        trip.setLastUpdatedBy(appUser.getEmail());

        tripRepository.save(trip);
    }


    public Trip getTripById(@NotNull UUID tripId) {
        var trip = tripRepository.findById(tripId);
        if (trip.isEmpty()) {
            throw new ApiException(ExceptionCode.TRIP_DOES_NOT_EXIST, "Trip does not exist");
        }

        return trip.get();
    }

    public TripDto getTrip(UUID tripId) {
        var trip = getTripById(tripId);
        return TripMapper.mapEntityToDto(trip);
    }

    public Trip testGetTrip(UUID tripId) {
        var trip = getTripById(tripId);
        return trip;
    }
}
