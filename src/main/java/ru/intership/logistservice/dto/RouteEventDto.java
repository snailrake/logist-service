package ru.intership.logistservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.intership.logistservice.model.RouteEventType;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RouteEventDto {

    private long id;
    private RouteEventType type;
    private LocalDateTime occurredAt;
    private long routeId;
}
