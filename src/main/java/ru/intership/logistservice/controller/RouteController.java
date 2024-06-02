package ru.intership.logistservice.controller;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.intership.logistservice.dto.RouteConditionDto;
import ru.intership.logistservice.dto.RouteDto;
import ru.intership.logistservice.service.RouteService;
import ru.intership.webcommonspringbootstarter.UserContext;

import java.util.List;

@Validated
@RestController
@RequestMapping("/route")
@RequiredArgsConstructor
public class RouteController {

    private final RouteService routeService;
    private final UserContext userContext;

    @PostMapping
    public RouteDto create(@RequestParam @Positive long taskId) {
        return routeService.create(taskId, userContext.getUserRoles());
    }

    @GetMapping("/{routeId}")
    public RouteConditionDto getById(@PathVariable @Positive long routeId) {
        return routeService.getById(routeId, userContext.getUserRoles());
    }

    @GetMapping
    public List<RouteDto> getAllTaskRoutes(@RequestParam @Positive long taskId,
                                           @RequestParam(defaultValue = "0") @Min(0) int page,
                                           @RequestParam(defaultValue = "20") @Min(1) @Max(100) int size) {
        return routeService.getAllTaskRoutes(taskId, userContext.getUserRoles(), page, size);
    }
}
