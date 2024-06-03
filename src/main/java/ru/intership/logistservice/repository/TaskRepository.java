package ru.intership.logistservice.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.intership.logistservice.model.Task;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findAllByCompanyId(String companyId, Pageable pageable);

    List<Task> findAllByDriverUsername(String username, Pageable pageable);

    @Query(nativeQuery = true, value = """
            SELECT COUNT(*)
            FROM task
            WHERE task.company_id = :companyId AND DATE(task.created_at) = CURRENT_DATE
            """)
    long findDailyStartedTasksCount(String companyId);
}
