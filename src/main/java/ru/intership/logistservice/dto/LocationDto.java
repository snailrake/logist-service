package ru.intership.logistservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationDto {

    private long id;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private LocalDateTime recordedAt;
    private long routeId;
}
