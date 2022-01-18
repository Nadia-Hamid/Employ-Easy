package se.yrgo.employeasy.vacation.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import se.yrgo.employeasy.vacation.entities.VacationDate;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UserDateRepository extends JpaRepository<VacationDate, Long> {
    @Query(value = "SELECT USERDATE.* ud FROM USERDATE WHERE USERDATE.job_title = ?1", nativeQuery = true)
    List<VacationDate> findByJobTitle(@Param(value = "jobTitle") String jobTitle);

    @Query(value = "SELECT * FROM USERDATE u WHERE u.user_id = ?1 AND u.date = ?2", nativeQuery = true)
    VacationDate findUserDate(String userId, LocalDate date);
}
