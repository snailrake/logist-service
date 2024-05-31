package ru.intership.logistservice.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.intership.logistservice.dto.TaskDto;
import ru.intership.logistservice.mapper.TaskMapper;
import ru.intership.logistservice.model.Task;
import ru.intership.logistservice.model.User;
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
    private final UserService userService;
    private final UserValidator userValidator;

    @Transactional
    public TaskDto create(TaskDto taskDto, String companyId, Set<String> roles) {
        userValidator.validateUserIsCompanyLogist(companyId, roles);
        Task task = taskMapper.toEntity(taskDto);
        setDriverAndCompanyId(task, companyId);
        Task savedTask = taskRepository.save(task);
        log.info("Task saved: {}", savedTask.getId());
        return taskMapper.toDto(savedTask);
    }

    @Transactional(readOnly = true)
    public TaskDto getById(long id, Set<String> roles) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Task with id '%d' not found", id)));
        userValidator.validateUserIsCompanyLogist(task.getCompanyId(), roles);
        return taskMapper.toDto(task);
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

    private void setDriverAndCompanyId(Task task, String companyId) {
        User user = userService.getById(task.getDriver().getId());
        task.setDriver(user);
        task.setCompanyId(companyId);
    }
}
