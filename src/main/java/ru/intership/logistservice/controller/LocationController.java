package ru.intership.logistservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.intership.common.UserContext;
import ru.intership.logistservice.dto.LocationDto;
import ru.intership.logistservice.service.LocationService;

@RestController
@RequestMapping("/location")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;
    private final UserContext userContext;

    @GetMapping
    public LocationDto getLastRouteLocation(@RequestParam long routeId) {
        return locationService.getLastRouteLocation(routeId, userContext.getUserRoles());
    }
}
