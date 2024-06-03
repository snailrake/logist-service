package ru.intership.logistservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.intership.logistservice.dto.TasksStatistics;
import ru.intership.logistservice.service.StatisticsService;

@RestController
@RequestMapping("/statistics")
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping("/task")
    public TasksStatistics getRouteStatistics(@RequestParam String companyId) {
        return statisticsService.getTasksStatistics(companyId);
    }
}
