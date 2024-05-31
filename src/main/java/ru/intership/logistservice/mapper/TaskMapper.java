package ru.intership.logistservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.intership.logistservice.dto.TaskDto;
import ru.intership.logistservice.model.Task;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = UserMapper.class)
public interface TaskMapper {

    TaskDto toDto(Task task);

    Task toEntity(TaskDto taskDto);

    List<TaskDto> toDto(List<Task> tasks);
}
