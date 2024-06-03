package ru.intership.logistservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "start_point", nullable = false)
    private String startPoint;

    @Column(name = "end_point", nullable = false)
    private String endPoint;

    @Column(name = "user_id", nullable = false)
    private String driverUsername;

    @Column(name = "cargo_description", nullable = false)
    private String cargoDescription;

    @Column(name = "vehicle_licence_plate", nullable = false)
    private String vehicleLicencePlate;

    @Column(name = "company_id", nullable = false)
    private String companyId;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "task")
    private List<Route> routes;
}
