package ru.intership.logistservice.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.intership.logistservice.dto.RouteConditionDto;
import ru.intership.logistservice.dto.RouteDto;
import ru.intership.logistservice.dto.RouteEventDto;
import ru.intership.logistservice.mapper.RouteMapper;
import ru.intership.logistservice.model.Route;
import ru.intership.logistservice.model.RouteEventType;
import ru.intership.logistservice.model.Task;
import ru.intership.logistservice.repository.RouteRepository;
import ru.intership.logistservice.validator.UserValidator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class RouteService {

    private final RouteRepository routeRepository;
    private final RouteMapper routeMapper;
    private final UserValidator userValidator;
    private final TaskService taskService;
    private final RouteEventService routeEventService;

    @Transactional
    public RouteDto create(long taskId, Set<String> roles) {
        Task task = taskService.findById(taskId);
        userValidator.validateUserIsCompanyLogist(task.getCompanyId(), roles);
        Route route = Route.builder()
                .createdAt(LocalDateTime.now())
                .build();
        route.setTask(task);
        Route savedRoute = routeRepository.save(route);
        log.info("Route saved: {}", savedRoute.getId());
        createStartedEvent(route);
        return routeMapper.toDto(savedRoute);
    }

    @Transactional(readOnly = true)
    public RouteConditionDto getById(long routeId, Set<String> roles) {
        Route route = this.findById(routeId);
        userValidator.validateUserIsCompanyLogist(route.getTask().getCompanyId(), roles);
        return RouteConditionDto.builder()
                .route(routeMapper.toDto(route))
                .condition(routeEventService.getCurrentRouteCondition(routeId).getType())
                .build();
    }

    @Transactional(readOnly = true)
    public List<RouteDto> getAllTaskRoutes(long taskId, Set<String> roles, int page, int size) {
        validateUserIsCompanyLogist(taskId, roles);
        List<Route> routes = routeRepository.findAllByTaskId(taskId, PageRequest.of(page, size));
        return routeMapper.toDto(routes);
    }

    @Transactional(readOnly = true)
    public Route findById(long routeId) {
        return routeRepository.findById(routeId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Route with id %s not found", routeId)));
    }

    @Transactional(readOnly = true)
    public long findDailyEventCountByCompanyId(RouteEventType routeEventType, String companyId) {
        return routeRepository.findDailyEventCountByCompanyId(routeEventType.name(), companyId);
    }

    private void createStartedEvent(Route route) {
        RouteEventDto routeEvent = RouteEventDto.builder()
                .type(RouteEventType.CREATED)
                .occurredAt(LocalDateTime.now())
                .routeId(route.getId())
                .build();
        routeEventService.create(routeEvent);
    }

    private void validateUserIsCompanyLogist(long taskId, Set<String> roles) {
        Task task = taskService.findById(taskId);
        userValidator.validateUserIsCompanyLogist(task.getCompanyId(), roles);
    }
}