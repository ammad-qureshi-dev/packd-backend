package com.packd.server_api.mappers;

import com.packd.server_api.controllers.dto.request.DestinationRequest;
import com.packd.server_api.controllers.dto.response.DestinationDto;
import com.packd.server_api.models.Destination;

public class DestinationMapper {
    public static Destination toDestinationEntity(DestinationRequest request) {
        return Destination.builder()
                .place(request.place())
                .toDate(request.toDate())
                .fromDate(request.fromDate())
                .build();
    }

    public static DestinationDto mapEntityToDto(Destination destination) {
        var activitiesDto = destination.getActivities().stream().map(ActivityMapper::mapEntityToDto).toList();
        return new DestinationDto (
                destination.getId(),
                destination.getPlace(),
                destination.getFromDate(),
                destination.getToDate(),
                activitiesDto
        );
    }
}
