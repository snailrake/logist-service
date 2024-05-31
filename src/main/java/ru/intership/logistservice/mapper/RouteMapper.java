package ru.intership.logistservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.intership.logistservice.dto.RouteDto;
import ru.intership.logistservice.model.Route;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RouteMapper {

    @Mapping(target = "taskId", source = "task.id")
    RouteDto toDto(Route route);

    @Mapping(target = "startedAt", ignore = true)
    @Mapping(target = "endedAt", ignore = true)
    Route toEntity(RouteDto routeDto);

    List<RouteDto> toDto(List<Route> routes);
}
