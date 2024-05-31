package ru.intership.logistservice.controller;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.intership.common.UserContext;
import ru.intership.logistservice.dto.TaskDto;
import ru.intership.logistservice.service.TaskService;

import java.util.List;

@Validated
@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final UserContext userContext;

    @PostMapping
    public TaskDto create(@RequestBody TaskDto taskDto,
                          @RequestParam String companyId) {
        return taskService.create(taskDto, companyId, userContext.getUserRoles());
    }

    @GetMapping("/{taskId}")
    public TaskDto getById(@PathVariable @Positive long taskId) {
        return taskService.getById(taskId, userContext.getUserRoles());
    }

    @GetMapping
    public List<TaskDto> getAllCompanyTasks(@RequestParam String companyId,
                                            @RequestParam(defaultValue = "0") @Min(0) int page,
                                            @RequestParam(defaultValue = "20") @Min(1) @Max(100) int size) {
        return taskService.getAllCompanyTasks(companyId, userContext.getUserRoles(), page, size);
    }
}
