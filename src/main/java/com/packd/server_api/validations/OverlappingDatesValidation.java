package com.packd.server_api.validations;

import com.packd.server_api.controllers.dto.request.DestinationRequest;
import com.packd.server_api.controllers.dto.request.TripRequest;
import com.packd.server_api.validations.annotations.OverlappingDates;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class OverlappingDatesValidation implements ConstraintValidator<OverlappingDates, List<DestinationRequest>> {
    @Override
    public boolean isValid(List<DestinationRequest> destinations, ConstraintValidatorContext constraintValidatorContext) {

        constraintValidatorContext.disableDefaultConstraintViolation();
        constraintValidatorContext.buildConstraintViolationWithTemplate("Destination dates cannot overlap")
                .addConstraintViolation();

        if (Objects.isNull(destinations) || destinations.isEmpty()) {
            return true;
        }

        var sortedDestinations = destinations.stream()
                .sorted(Comparator.comparing(DestinationRequest::fromDate))
                .toList();

        var prevDestination = sortedDestinations.getFirst();

        for (int i = 1; i < sortedDestinations.size(); i++) {
            var currDestination = sortedDestinations.get(i);

            if (datesOverlap(prevDestination, currDestination)) {
                return false;
            }

            prevDestination = currDestination;
        }

        return true;
    }

    private static boolean datesOverlap(DestinationRequest prev, DestinationRequest curr) {
        return !prev.toDate().isBefore(curr.fromDate());
    }
}
