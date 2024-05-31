package ru.intership.logistservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.intership.logistservice.dto.RouteEventDto;
import ru.intership.logistservice.model.RouteEvent;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RouteEventMapper {

    @Mapping(target = "routeId", source = "route.id")
    RouteEventDto toDto(RouteEvent routeEvent);

    RouteEvent toEntity(RouteEventDto routeEventDto);
}
