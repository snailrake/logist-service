package ru.intership.logistservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.intership.logistservice.model.RouteEvent;

@Repository
public interface RouteEventRepository extends JpaRepository<RouteEvent, Long> {

    @Query(nativeQuery = true, value = """
            SELECT *
            FROM route_event
            ORDER BY occurred_at DESC
            LIMIT 1
            """)
    RouteEvent findCurrentRouteCondition(Long id);
}
