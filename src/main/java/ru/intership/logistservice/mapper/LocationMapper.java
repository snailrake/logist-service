package ru.intership.logistservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.intership.logistservice.dto.LocationDto;
import ru.intership.logistservice.model.Location;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LocationMapper {

    @Mapping(target = "routeId", source = "route.id")
    LocationDto toDto(Location location);

    Location toEntity(LocationDto location);
}
