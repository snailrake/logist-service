package ru.intership.logistservice.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import ru.intership.logistservice.dto.RouteConditionDto;
import ru.intership.logistservice.dto.RouteDto;
import ru.intership.logistservice.dto.RouteEventDto;
import ru.intership.logistservice.mapper.RouteMapperImpl;
import ru.intership.logistservice.model.Route;
import ru.intership.logistservice.model.RouteEvent;
import ru.intership.logistservice.model.RouteEventType;
import ru.intership.logistservice.model.Task;
import ru.intership.logistservice.repository.RouteRepository;
import ru.intership.logistservice.validator.UserValidator;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RouteServiceTest {

    @Mock
    private RouteRepository routeRepository;

    @Spy
    private RouteMapperImpl routeMapper;

    @Mock
    private UserValidator userValidator;

    @Mock
    private TaskService taskService;

    @Mock
    private RouteEventService routeEventService;

    @InjectMocks
    private RouteService routeService;

    @Test
    public void create_ValidArgs() {
        RouteDto expectedRouteDto = getRouteDto();
        when(taskService.findById(anyLong())).thenReturn(getTask());
        when(routeRepository.save(any(Route.class))).thenReturn(getRoute());

        RouteDto actualRouteDto = routeService.create(1, Set.of());

        assertEquals(expectedRouteDto, actualRouteDto);
        verify(taskService, times(1)).findById(anyLong());
        verify(userValidator, times(1)).validateUserIsCompanyLogist(anyString(), anySet());
        verify(routeRepository, times(1)).save(any(Route.class));
        verify(routeEventService, times(1)).create(any(RouteEventDto.class));
        verify(routeMapper, times(1)).toDto(any(Route.class));
    }

    @Test
    public void getById_ValidArgs() {
        RouteConditionDto expectedRouteConditionDto = getRouteConditionDto();
        when(routeRepository.findById(anyLong())).thenReturn(Optional.of(getRoute()));
        when(routeEventService.getCurrentRouteCondition(anyLong())).thenReturn(getRouteEvent());

        RouteConditionDto actualRouteConditionDto = routeService.getById(1, Set.of());

        assertEquals(expectedRouteConditionDto, actualRouteConditionDto);
        verify(routeRepository, times(1)).findById(anyLong());
        verify(userValidator, times(1)).validateUserIsCompanyLogist(anyString(), anySet());
        verify(routeMapper, times(1)).toDto(any(Route.class));
        verify(routeEventService, times(1)).getCurrentRouteCondition(anyLong());
    }

    @Test
    public void getById_RouteDoesNotExist_ThrowsEntityNotFoundException() {
        when(routeRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> routeService.getById(1, Set.of()));
    }

    @Test
    public void getAllTaskRoutes_ValidArgs() {
        List<RouteDto> expectedRouteDtos = getRouteDtos();
        when(taskService.findById(anyLong())).thenReturn(getTask());
        when(routeRepository.findAllByTaskId(anyLong(), any(Pageable.class))).thenReturn(getRoutes());

        List<RouteDto> actualRouteDtos = routeService.getAllTaskRoutes(1, Set.of(), 0, 25);

        assertEquals(expectedRouteDtos, actualRouteDtos);
        verify(taskService, times(1)).findById(anyLong());
        verify(userValidator, times(1)).validateUserIsCompanyLogist(anyString(), anySet());
        verify(routeRepository, times(1)).findAllByTaskId(anyLong(), any(Pageable.class));
        verify(routeMapper, times(1)).toDto(anyList());
    }

    @Test
    public void findById_ValidArgs() {
        Route expectedRoute = getRoute();
        when(routeRepository.findById(anyLong())).thenReturn(Optional.of(expectedRoute));

        Route actualRoute = routeService.findById(1);

        assertEquals(expectedRoute, actualRoute);
        verify(routeRepository, times(1)).findById(anyLong());
    }

    @Test
    public void findById_RouteDoesNotExist_ThrowsEntityNotFoundException() {
        when(routeRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> routeService.findById(1));
    }

    private List<RouteDto> getRouteDtos() {
        return List.of(getRouteDto(), getRouteDto(), getRouteDto(), getRouteDto());
    }

    private List<Route> getRoutes() {
        return List.of(getRoute(), getRoute(), getRoute(), getRoute());
    }

    private RouteConditionDto getRouteConditionDto() {
        return RouteConditionDto.builder()
                .route(getRouteDto())
                .condition(getRouteEventType())
                .build();
    }

    private RouteEvent getRouteEvent() {
        return RouteEvent.builder()
                .type(getRouteEventType())
                .build();
    }

    private RouteEventType getRouteEventType() {
        return RouteEventType.BREAKDOWN;
    }

    private RouteDto getRouteDto() {
        return RouteDto.builder()
                .id(122)
                .taskId(getTask().getId())
                .build();
    }

    private Route getRoute() {
        return Route.builder()
                .id(122)
                .task(getTask())
                .build();
    }

    private Task getTask() {
        return Task.builder()
                .id(1242345)
                .companyId(getCompanyId())
                .driverUsername("Oleg")
                .build();
    }

    private String getCompanyId() {
        return "123423423";
    }
}
