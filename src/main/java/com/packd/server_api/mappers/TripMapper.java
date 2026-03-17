package com.packd.server_api.mappers;

import com.packd.server_api.controllers.dto.response.DestinationDto;
import com.packd.server_api.controllers.dto.response.TripDto;
import com.packd.server_api.models.Trip;

import java.util.ArrayList;

public class TripMapper {
    public static TripDto mapEntityToDto(Trip entity) {
        var destinationDtos = new ArrayList<DestinationDto>();
        for (var destination : entity.getDestinations()) {
            var destinationDto = DestinationMapper.mapEntityToDto(destination);
            destinationDtos.add(destinationDto);
        }

        return new TripDto(entity.getId(), entity.getTitle(), entity.getTotalBudget(), destinationDtos);
    }
}
