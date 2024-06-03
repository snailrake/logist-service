package ru.intership.logistservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.intership.logistservice.dto.LocationDto;
import ru.intership.logistservice.mapper.LocationMapper;
import ru.intership.logistservice.model.Location;
import ru.intership.logistservice.model.Route;
import ru.intership.logistservice.repository.LocationRepository;
import ru.intership.logistservice.validator.UserValidator;

import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;
    private final RouteService routeService;
    private final UserValidator userValidator;

    @Transactional
    public LocationDto create(LocationDto locationDto) {
        Location location = locationMapper.toEntity(locationDto);
        Route route = routeService.findById(locationDto.getRouteId());
        location.setRoute(route);
        Location savedLocation = locationRepository.save(location);
        log.info("Location saved: {}", savedLocation.getId());
        return locationMapper.toDto(savedLocation);
    }

    @Transactional(readOnly = true)
    public LocationDto getLastRouteLocation(long routeId, Set<String> roles) {
        Route route = routeService.findById(routeId);
        userValidator.validateUserIsCompanyLogist(route.getTask().getCompanyId(), roles);
        Location location = locationRepository.getLastRouteLocation(routeId);
        return locationMapper.toDto(location);
    }
}
