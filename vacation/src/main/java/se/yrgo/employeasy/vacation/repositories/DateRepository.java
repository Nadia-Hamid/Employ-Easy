package se.yrgo.employeasy.vacation.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import se.yrgo.employeasy.vacation.entities.VacationDate;

@Repository
public interface DateRepository extends JpaRepository<VacationDate, Long> {

	@Query(value = "SELECT * FROM VACATIONDATE WHERE VACATIONDATE.job_title = ?1 "
			+ " AND VACATIONDATE.user_id IS NULL", nativeQuery = true)
	List<VacationDate> findByJobTitle(@Param(value = "jobTitle") String jobTitle);

	@Query(value = "SELECT * FROM VACATIONDATE WHERE VACATIONDATE.job_title = ?1"
			+ " AND VACATIONDATE.date = ?2 AND VACATIONDATE.user_id IS NULL", nativeQuery = true)
	List<VacationDate> findByJobTitleOpenDate(@Param(value = "jobTitle") String jobTitle,
			@Param(value = "date") LocalDate date);

	@Query(value = "SELECT CASE WHEN COUNT(v) > 0 THEN true ELSE false END FROM "
			+ "VACATIONDATE v WHERE v.date = ?1 AND v.user_id = ?2", nativeQuery = true)
	Boolean hasAlreadyBooked(@Param(value = "date") LocalDate date, @Param(value = "userId") String userId);

//	@Transactional
//	@Modifying(clearAutomatically = true)
//	@Query(value = "UPDATE VACATIONDATE v SET v.user_id = :userId WHERE v.id = :id", nativeQuery = true)
//	void addVacationDate(@Param(value = "userId") String userId, @Param(value = "id") long id);

}
