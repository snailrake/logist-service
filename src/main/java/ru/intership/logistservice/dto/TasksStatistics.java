package ru.intership.logistservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TasksStatistics {

    private long startedRoutes;
    private long completedRoutes;
    private long canceledRoutes;
    private long startedTasks;
}
