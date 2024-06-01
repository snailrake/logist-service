package ru.intership.logistservice.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.intership.logistservice.dto.RouteEventDto;
import ru.intership.logistservice.mapper.RouteEventMapper;
import ru.intership.logistservice.model.Route;
import ru.intership.logistservice.model.RouteEvent;
import ru.intership.logistservice.repository.RouteEventRepository;
import ru.intership.logistservice.repository.RouteRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class RouteEventService {

    private final RouteEventRepository routeEventRepository;
    private final RouteRepository routeRepository;
    private final RouteEventMapper routeEventMapper;

    @Transactional
    public RouteEventDto create(RouteEventDto routeEventDto) {
        RouteEvent routeEvent = routeEventMapper.toEntity(routeEventDto);
        Route route = this.findRouteById(routeEventDto.getRouteId());
        routeEvent.setRoute(route);
        RouteEvent savedRouteEvent = routeEventRepository.save(routeEvent);
        log.info("Route event saved: {}", savedRouteEvent);
        return routeEventMapper.toDto(savedRouteEvent);
    }

    @Transactional(readOnly = true)
    public RouteEvent getCurrentRouteCondition(long routeId) {
        return routeEventRepository.findCurrentRouteCondition(routeId);
    }

    private Route findRouteById(long id) {
        return routeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Route event with id %s not found", id)));
    }
}
