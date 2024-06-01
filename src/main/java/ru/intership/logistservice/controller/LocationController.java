package ru.intership.logistservice.controller;

import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.intership.logistservice.dto.LocationDto;
import ru.intership.logistservice.service.LocationService;
import ru.intership.webcommonspringbootstarter.UserContext;

@Validated
@RestController
@RequestMapping("/location")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;
    private final UserContext userContext;

    @GetMapping
    public LocationDto getLastRouteLocation(@RequestParam @Positive long routeId) {
        return locationService.getLastRouteLocation(routeId, userContext.getUserRoles());
    }
}