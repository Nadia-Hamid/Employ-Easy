package se.yrgo.employeasy.vacation.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import se.yrgo.employeasy.vacation.entities.OpenDate;

import java.util.List;

@Repository
public interface VacationRepository extends JpaRepository<OpenDate, Long> {
    @Query(value = "SELECT vacation.* vac FROM VACATION WHERE vacation.job_title = ?1", nativeQuery = true)
    List<OpenDate> findByJobTitle(@Param(value = "jobTitle") String jobTitle);
}
