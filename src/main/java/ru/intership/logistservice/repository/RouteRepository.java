package ru.intership.logistservice.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.intership.logistservice.model.Route;

import java.util.List;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {

    List<Route> findAllByTaskId(long taskId, Pageable pageable);

    @Query(nativeQuery = true, value = """
            SELECT COUNT(CASE WHEN event_type = :eventType AND DATE(occurred_at) = CURRENT_DATE THEN 1 END)
            FROM (SELECT route_event.*,
                  ROW_NUMBER() OVER (PARTITION BY route_id ORDER BY occurred_at DESC) AS rn
                  FROM route_event) AS re
                     JOIN route ON re.route_id = route.id
                     JOIN task ON route.task_id = task.id
            WHERE task.company_id = :companyId AND re.rn = 1;
            """)
    long findDailyEventCountByCompanyId(String eventType, String companyId);
}
