package ru.intership.logistservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.intership.logistservice.dto.TasksStatistics;
import ru.intership.logistservice.model.RouteEventType;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final RouteService routeService;
    private final TaskService taskService;

    @Transactional(readOnly = true)
    public TasksStatistics getTasksStatistics(String companyId) {
        long completedRoutesCnt = routeService.findDailyEventCountByCompanyId(RouteEventType.ENDED, companyId);
        long cancelledRoutesCnt = routeService.findDailyEventCountByCompanyId(RouteEventType.CANCELLED, companyId);
        long startedRoutesCnt = routeService.findDailyEventCountByCompanyId(RouteEventType.STARTED, companyId);
        long startedTasksCnt = taskService.findDailyStartedTasksCount(companyId);
        return TasksStatistics.builder()
                .completedRoutes(completedRoutesCnt)
                .canceledRoutes(cancelledRoutesCnt)
                .startedRoutes(startedRoutesCnt)
                .startedTasks(startedTasksCnt)
                .build();
    }
}
