package se.yrgo.employeasy.vacation.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import se.yrgo.employeasy.vacation.entities.VacationDate;

@Repository
public interface DateRepository extends JpaRepository<VacationDate, Long> {

    @Query(value = "SELECT * FROM VACATIONDATE v WHERE v.job_title = ?1 AND v.user_id IS NULL " +
            "AND (v.date > CURRENT_DATE)", nativeQuery = true)
    List<VacationDate> findBookableByJobTitle(@Param(value = "jobTitle") String jobTitle);

    @Query(value = "SELECT * FROM VACATIONDATE v WHERE v.job_title = ?1 " +
            "AND v.date = ?2 AND v.user_id IS NULL", nativeQuery = true)
    List<VacationDate> findDateSlots(@Param(value = "jobTitle") String jobTitle,
                                     @Param(value = "date") LocalDate date);

	@Query(value = "SELECT CASE WHEN COUNT(v) > 0 THEN true ELSE false END FROM "
            + "VACATIONDATE v WHERE v.date = ?1 AND v.user_id = ?2", nativeQuery = true)
    Boolean hasAlreadyBooked(@Param(value = "date") LocalDate date, @Param(value = "userId") String userId);

    @Modifying
    @Query(value = "UPDATE VACATIONDATE v SET user_id = NULL WHERE v.user_id = ?1 " +
            "AND (v.date > CURRENT_DATE)", nativeQuery = true)
    void resetFutureChoices(@Param(value = "userId") String userId);

    @Query(value = "SELECT * FROM VACATIONDATE v WHERE v.user_id = ?1 " +
            "AND EXTRACT(YEAR from v.date) = date_part('year', CURRENT_DATE)", nativeQuery = true)
    List<VacationDate> findAnnualByUserId(@Param(value = "userId") String userId);

    @Query(value = "SELECT * FROM VACATIONDATE v WHERE EXTRACT(YEAR from v.date) = date_part('year', CURRENT_DATE)"
            , nativeQuery = true)
    List<VacationDate> findAllAnnual();

    @Query(value = "SELECT * FROM VACATIONDATE v WHERE v.job_title = ?2 AND EXTRACT(year FROM v.date) = ?1", nativeQuery = true)
    List<VacationDate> findAllByYearAndJobTitle(@Param(value = "year") int year, @Param(value = "jobTitle") String jobTitle);
}
