package ru.intership.logistservice.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.intership.logistservice.dto.TaskDto;
import ru.intership.logistservice.dto.TaskLongDto;
import ru.intership.logistservice.dto.UserDto;
import ru.intership.logistservice.mapper.TaskMapper;
import ru.intership.logistservice.model.Task;
import ru.intership.logistservice.repository.TaskRepository;
import ru.intership.logistservice.validator.UserValidator;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final UserValidator userValidator;
    private final PortalService portalService;

    @Transactional
    public TaskDto create(TaskDto taskDto, String companyId, Set<String> roles) {
        userValidator.validateUserIsCompanyLogist(companyId, roles);
        portalService.getUserByUsername(taskDto.getDriverUsername());
        Task task = taskMapper.toEntity(taskDto);
        task.setCompanyId(companyId);
        Task savedTask = taskRepository.save(task);
        log.info("Task saved: {}", savedTask.getId());
        return taskMapper.toDto(savedTask);
    }

    @Transactional(readOnly = true)
    public TaskLongDto getById(long id, Set<String> roles) {
        Task task = this.findById(id);
        userValidator.validateUserIsCompanyLogist(task.getCompanyId(), roles);
        UserDto userDto = portalService.getUserByUsername(task.getDriverUsername());
        TaskLongDto taskLongDto = taskMapper.toLongDto(task);
        taskLongDto.setDriver(userDto);
        return taskLongDto;
    }

    @Transactional(readOnly = true)
    public List<TaskDto> getAllCompanyTasks(String companyId, Set<String> roles, int page, int size) {
        userValidator.validateUserIsCompanyLogist(companyId, roles);
        List<Task> tasks = taskRepository.findAllByCompanyId(companyId, PageRequest.of(page, size));
        return taskMapper.toDto(tasks);
    }

    @Transactional(readOnly = true)
    public Task findById(long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Task with id '%d' not found", id)));
    }

    @Transactional(readOnly = true)
    public List<TaskDto> getAllDriverTasksByUsername(String username, int page, int size) {
        List<Task> tasks = taskRepository.findAllByDriverUsername(username, PageRequest.of(page, size));
        return taskMapper.toDto(tasks);
    }

    @Transactional(readOnly = true)
    public long findDailyStartedTasksCount(String companyId) {
        return taskRepository.findDailyStartedTasksCount(companyId);
    }
}
