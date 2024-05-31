package ru.intership.logistservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.intership.logistservice.model.RouteEventType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RouteConditionDto {

    private RouteDto route;
    private RouteEventType condition;
}
