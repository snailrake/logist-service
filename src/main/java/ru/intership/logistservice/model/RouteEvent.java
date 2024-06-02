package ru.intership.logistservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "route_event")
public class RouteEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_type", nullable = false)
    private RouteEventType type;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "occurred_at", nullable = false)
    private LocalDateTime occurredAt;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "route_id", nullable = false)
    private Route route;
}
