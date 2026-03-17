package com.packd.server_api.services;

import com.packd.server_api.controllers.dto.request.DestinationRequest;
import com.packd.server_api.exceptions.ApiException;
import com.packd.server_api.exceptions.ExceptionCode;
import com.packd.server_api.mappers.DestinationMapper;
import com.packd.server_api.models.Destination;
import com.packd.server_api.models.Trip;
import com.packd.server_api.repositories.DestinationRepository;
import com.packd.server_api.repositories.TripRepository;
import com.packd.server_api.validations.annotations.OverlappingDates;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.*;

@Service
@RequiredArgsConstructor
@Validated
public class DestinationService {

    private final DestinationRepository destinationRepository;
    private final TripRepository tripRepository;

    private final AppUserService appUserService;

    public UUID addDestinationToTrip(@Valid DestinationRequest destination, @NotNull UUID tripId) {
        var newDestination = DestinationMapper.toDestinationEntity(destination);
        var trip = getTripById(tripId);

        if (!fromDateBeforeOrEqualToToDate(newDestination)) {
            throw new ApiException(ExceptionCode.VALIDATION_FAILED, "'from date' cannot precede 'to date'");
        }

        if (newDestinationOverlaps(trip.getDestinations(), newDestination)) {
            throw new ApiException(ExceptionCode.VALIDATION_FAILED, "Destination dates cannot overlap");
        }

        newDestination.setTrip(trip);
        destinationRepository.save(newDestination);
        return newDestination.getId();
    }

    @Transactional
    public List<UUID> addDestinationsToTrip(@NotEmpty @OverlappingDates List<@Valid DestinationRequest> destinations, @NotNull Trip trip) {
        var destinationsToAdd = destinations.stream()
                .map(e -> {
                    var d = DestinationMapper.toDestinationEntity(e);
                    d.setTrip(trip);
                    return d;
                } )
                .toList();

        destinationRepository.saveAll(destinationsToAdd);
        return destinationsToAdd.stream().map(Destination::getId).toList();
    }

    @Transactional
    public void updateDestination(UUID appUserId, UUID tripId, UUID destinationId, DestinationRequest request) {
        var destination = getDestinationById(destinationId);
        var trip = getTripById(tripId);
        var updatedDestination = DestinationMapper.toDestinationEntity(request);

        var savedDestinations = new ArrayList<>(trip.getDestinations()
                .stream()
                .filter(e -> !e.getId().equals(destinationId))
                .toList());

        // Validate destination
        if (newDestinationOverlaps(savedDestinations, updatedDestination)) {
            throw new ApiException(ExceptionCode.VALIDATION_FAILED, "Destination dates cannot overlap");
        }

        if (!fromDateBeforeOrEqualToToDate(request)) {
            throw new ApiException(ExceptionCode.VALIDATION_FAILED, "'from date' cannot precede 'to date'");
        }

        // Update persisted destination
        if (!request.fromDate().equals(destination.getFromDate())) {
            destination.setFromDate(request.fromDate());
        }

        if (!request.toDate().equals(destination.getToDate())) {
            destination.setToDate(request.toDate());
        }

        if (!request.place().equals(destination.getPlace())) {
            destination.setPlace(request.place());
        }

        var appUser = appUserService.getAppUserById(appUserId);
        destination.setLastUpdatedBy(appUser.getEmail());

        destinationRepository.save(destination);
    }

    public Destination getDestinationById(UUID destinationId) {
        var destination = destinationRepository.findById(destinationId);
        if (destination.isEmpty()) {
            throw new ApiException(ExceptionCode.DESTINATION_DOES_NOT_EXIST, "Destination does not exist");
        }

        return destination.get();
    }

    private Trip getTripById(UUID tripID) {
        var trip = tripRepository.findById(tripID);

        if (trip.isEmpty()) {
            throw new ApiException(ExceptionCode.TRIP_DOES_NOT_EXIST, "Trip does not exist");
        }

        return trip.get();
    }

    private static boolean newDestinationOverlaps(List<Destination> destinations, Destination newDestination) {
        if (Objects.isNull(destinations) || destinations.isEmpty()) {
            return false;
        }

        destinations.add(newDestination);

        var sortedDestinations = destinations.stream()
                .sorted(Comparator.comparing(Destination::getFromDate))
                .toList();

        var prevDestination = sortedDestinations.getFirst();

        if (!fromDateBeforeOrEqualToToDate(prevDestination)) {
            throw new ApiException(ExceptionCode.VALIDATION_FAILED, "'from date' cannot precede 'to date'");
        }

        for (int i = 1; i < sortedDestinations.size(); i++) {
            var currDestination = sortedDestinations.get(i);

            if (!fromDateBeforeOrEqualToToDate(currDestination)) {
                throw new ApiException(ExceptionCode.VALIDATION_FAILED, "'from date' cannot precede 'to date'");
            }

            if (datesOverlap(prevDestination, currDestination)) {
                return true;
            }

            prevDestination = currDestination;
        }

        return false;
    }

    private static boolean datesOverlap(Destination prev, Destination curr) {
        return prev.getToDate().isAfter(curr.getFromDate());
    }

    private static boolean fromDateBeforeOrEqualToToDate(Destination request) {
        return !request.getFromDate().isAfter(request.getToDate());
    }

    private static boolean fromDateBeforeOrEqualToToDate(DestinationRequest request) {
        return !request.fromDate().isAfter(request.toDate());
    }
}
