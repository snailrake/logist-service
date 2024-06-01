package ru.intership.logistservice.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import ru.intership.logistservice.dto.TaskDto;
import ru.intership.logistservice.dto.TaskLongDto;
import ru.intership.logistservice.dto.UserDto;
import ru.intership.logistservice.mapper.TaskMapperImpl;
import ru.intership.logistservice.model.Task;
import ru.intership.logistservice.repository.TaskRepository;
import ru.intership.logistservice.validator.UserValidator;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Spy
    private TaskMapperImpl taskMapper;

    @Mock
    private UserValidator userValidator;

    @Mock
    private PortalService portalService;

    @InjectMocks
    private TaskService taskService;

    @Test
    public void create_ValidArgs() {
        TaskDto expectedTaskDto = getTaskDto();
        when(taskRepository.save(any(Task.class))).thenReturn(getTask());

        TaskDto actualTaskDto = taskService.create(expectedTaskDto, getCompanyId(), Set.of());

        assertEquals(expectedTaskDto, actualTaskDto);
        verify(userValidator, times(1)).validateUserIsCompanyLogist(anyString(), anySet());
        verify(taskMapper, times(1)).toEntity(any(TaskDto.class));
        verify(taskRepository, times(1)).save(any(Task.class));
        verify(taskMapper, times(1)).toDto(any(Task.class));
    }

    @Test
    public void getById_ValidArgs() {
        TaskLongDto expectedTaskLongDto = getTaskLongDto();
        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(getTask()));
        when(portalService.getUserByUsername(anyString())).thenReturn(getUserDto());

        TaskLongDto actualTaskLongDto = taskService.getById(1, Set.of());

        assertEquals(expectedTaskLongDto, actualTaskLongDto);
        verify(taskRepository, times(1)).findById(anyLong());
        verify(userValidator, times(1)).validateUserIsCompanyLogist(anyString(), anySet());
        verify(portalService, times(1)).getUserByUsername(anyString());
        verify(taskMapper, times(1)).toLongDto(any(Task.class));
    }

    @Test
    public void getById_TaskDoesNotExist_ThrowsEntityNotFoundException() {
        when(taskRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> taskService.getById(1, Set.of()));
    }

    @Test
    public void getAllCompanyTasks_ValidArgs() {
        List<TaskDto> expectedTaskDtos = getTaskDtos();
        when(taskRepository.findAllByCompanyId(anyString(), any(Pageable.class))).thenReturn(getTasks());

        List<TaskDto> actualTaskDtos = taskService.getAllCompanyTasks(getCompanyId(), Set.of(), 0, 25);

        assertEquals(expectedTaskDtos, actualTaskDtos);
        verify(userValidator, times(1)).validateUserIsCompanyLogist(anyString(), anySet());
        verify(taskRepository, times(1)).findAllByCompanyId(anyString(), any(Pageable.class));
        verify(taskMapper, times(1)).toDto(anyList());
    }

    @Test
    public void findById_ValidArgs() {
        Task expectedTask = getTask();
        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(expectedTask));

        Task actualTask = taskService.findById(1);

        assertEquals(expectedTask, actualTask);
        verify(taskRepository, times(1)).findById(anyLong());
    }

    @Test
    public void findById_TaskDoesNotExist_ThrowsEntityNotFoundException() {
        when(taskRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> taskService.findById(1));
    }

    private List<Task> getTasks() {
        return List.of(getTask(), getTask(), getTask());
    }

    private List<TaskDto> getTaskDtos() {
        return List.of(getTaskDto(), getTaskDto(), getTaskDto());
    }

    private TaskLongDto getTaskLongDto() {
        return TaskLongDto.builder()
                .id(getTask().getId())
                .driver(getUserDto())
                .companyId(getCompanyId())
                .build();
    }

    private UserDto getUserDto() {
        return UserDto.builder()
                .email("aerkngbajker@mail.ru")
                .build();
    }

    private TaskDto getTaskDto() {
        return TaskDto.builder()
                .id(1242345)
                .companyId(getCompanyId())
                .driverUsername("Oleg")
                .build();
    }

    private Task getTask() {
        return Task.builder()
                .id(1242345)
                .companyId(getCompanyId())
                .driverUsername("Oleg")
                .build();
    }

    private String getCompanyId() {
        return "123423423";
    }
}
