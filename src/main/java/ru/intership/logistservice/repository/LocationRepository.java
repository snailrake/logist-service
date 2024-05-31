package ru.intership.logistservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.intership.logistservice.model.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    @Query(nativeQuery = true, value = """
            SELECT *
            FROM location
            ORDER BY recorded_at DESC
            LIMIT 1
            """)
    Location getLastRouteLocation(long routeId);
}
