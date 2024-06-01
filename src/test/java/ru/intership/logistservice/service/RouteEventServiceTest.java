package ru.intership.logistservice.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.intership.logistservice.dto.RouteEventDto;
import ru.intership.logistservice.mapper.RouteEventMapperImpl;
import ru.intership.logistservice.model.Route;
import ru.intership.logistservice.model.RouteEvent;
import ru.intership.logistservice.repository.RouteEventRepository;
import ru.intership.logistservice.repository.RouteRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RouteEventServiceTest {

    @Mock
    private RouteEventRepository routeEventRepository;

    @Mock
    private RouteRepository routeRepository;

    @Spy
    private RouteEventMapperImpl routeEventMapper;

    @InjectMocks
    private RouteEventService routeEventService;

    @Test
    public void create_ValidArgs() {
        RouteEventDto expectedRouteEventDto = getRouteEventDto();
        when(routeRepository.findById(anyLong())).thenReturn(Optional.of(getRoute()));
        when(routeEventRepository.save(any(RouteEvent.class))).thenReturn(getRouteEvent());

        RouteEventDto actualRouteEventDto = routeEventService.create(expectedRouteEventDto);

        assertEquals(expectedRouteEventDto, actualRouteEventDto);
        verify(routeEventMapper, times(1)).toEntity(any(RouteEventDto.class));
        verify(routeRepository, times(1)).findById(anyLong());
        verify(routeEventRepository, times(1)).save(any(RouteEvent.class));
        verify(routeEventMapper, times(1)).toDto(any(RouteEvent.class));
    }

    @Test
    public void create_RouteDoesNotExist_ThrowsEntityNotFoundException() {
        when(routeRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> routeEventService.create(getRouteEventDto()));
    }

    @Test
    public void getCurrentRouteCondition_ValidArgs() {
        RouteEvent expectedRouteEvent = getRouteEvent();
        when(routeEventRepository.findCurrentRouteCondition(anyLong())).thenReturn(expectedRouteEvent);

        RouteEvent actualRouteEvent = routeEventService.getCurrentRouteCondition(expectedRouteEvent.getId());

        assertEquals(expectedRouteEvent, actualRouteEvent);
        verify(routeEventRepository, times(1)).findCurrentRouteCondition(anyLong());
    }

    private Route getRoute() {
        return Route.builder()
                .id(122)
                .build();
    }

    private RouteEventDto getRouteEventDto() {
        return RouteEventDto.builder()
                .id(1235423)
                .build();
    }

    private RouteEvent getRouteEvent() {
        return RouteEvent.builder()
                .id(1235423)
                .build();
    }
}
