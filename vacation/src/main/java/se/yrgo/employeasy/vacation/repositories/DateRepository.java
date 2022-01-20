package se.yrgo.employeasy.vacation.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import se.yrgo.employeasy.vacation.entities.VacationDate;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DateRepository extends JpaRepository<VacationDate, Long> {

    @Query(value = "SELECT * FROM VACATIONDATE WHERE VACATIONDATE.job_title = ?1 AND VACATIONDATE.user_id IS NULL"
            , nativeQuery = true)
    List<VacationDate> findByJobTitle(@Param(value = "jobTitle") String jobTitle);

    @Query(value = "SELECT * FROM VACATIONDATE WHERE VACATIONDATE.job_title = ?1" +
            " AND VACATIONDATE.date = ?2 AND VACATIONDATE.user_id IS NULL", nativeQuery = true)
    List<VacationDate> findOpenDateByJobTitle(@Param(value = "jobTitle") String jobTitle,
                                              @Param(value = "date") LocalDate date);
}
