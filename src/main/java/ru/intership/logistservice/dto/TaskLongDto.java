package ru.intership.logistservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskLongDto {

    private long id;
    private String startPoint;
    private String endPoint;
    private UserDto driver;
    private String cargoDescription;
    private String vehicleLicencePlate;
    private String companyId;
}
