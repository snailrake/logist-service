package ru.intership.logistservice.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.intership.logistservice.dto.LocationDto;
import ru.intership.logistservice.mapper.LocationMapperImpl;
import ru.intership.logistservice.model.Location;
import ru.intership.logistservice.model.Route;
import ru.intership.logistservice.model.Task;
import ru.intership.logistservice.repository.LocationRepository;
import ru.intership.logistservice.validator.UserValidator;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LocationServiceTest {

    @Mock
    private LocationRepository locationRepository;

    @Spy
    private LocationMapperImpl locationMapper;

    @Mock
    private RouteService routeService;

    @Mock
    private UserValidator userValidator;

    @InjectMocks
    private LocationService locationService;

    @Test
    public void create_ValidArgs() {
        LocationDto expectedLocationDto = getLocationDto();
        when(routeService.findById(anyLong())).thenReturn(getRoute());
        when(locationRepository.save(any(Location.class))).thenReturn(getLocation());

        LocationDto actualLocationDto = locationService.create(expectedLocationDto);

        assertEquals(expectedLocationDto, actualLocationDto);
        verify(locationMapper, times(1)).toEntity(any(LocationDto.class));
        verify(routeService, times(1)).findById(anyLong());
        verify(locationRepository, times(1)).save(any(Location.class));
        verify(locationMapper, times(1)).toDto(any(Location.class));
    }

    @Test
    public void getLastRouteLocation_ValidArgs() {
        LocationDto expectedLocationDto = getLocationDto();
        when(routeService.findById(anyLong())).thenReturn(getRoute());
        when(locationRepository.getLastRouteLocation(anyLong())).thenReturn(getLocation());

        LocationDto actualLocationDto = locationService.getLastRouteLocation(1, Set.of());

        assertEquals(expectedLocationDto, actualLocationDto);
        verify(routeService, times(1)).findById(anyLong());
        verify(userValidator, times(1)).validateUserIsCompanyLogist(anyString(), anySet());
        verify(locationRepository, times(1)).getLastRouteLocation(anyLong());
        verify(locationMapper, times(1)).toDto(any(Location.class));
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

    private LocationDto getLocationDto() {
        return LocationDto.builder()
                .id(23523)
                .build();
    }

    private Location getLocation() {
        return Location.builder()
                .id(23523)
                .build();
    }
}
