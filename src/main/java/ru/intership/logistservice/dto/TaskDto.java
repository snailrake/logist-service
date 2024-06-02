package ru.intership.logistservice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {

    private long id;

    @NotNull
    private String startPoint;

    @NotNull
    private String endPoint;

    @NotNull
    private String driverUsername;

    @NotNull
    private String cargoDescription;

    @NotNull
    @Size(min = 6, max = 10)
    private String vehicleLicencePlate;

    private String companyId;
}
