package se.yrgo.employee.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import se.yrgo.employee.entities.Employee;

/**
 * @class Employee Repository class
 */
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	/**
	 * Query to find employees by their job titles.
	 * @param jobTitle
	 * @return An Employee List matching the job title.
	 */
	@Query(value = "SELECT employee.* emp FROM EMPLOYEE WHERE employee.job_title = ?1", nativeQuery = true)
	List<Employee> findByJobTitle(@Param(value = "jobTitle") String jobTitle);

	/**
	 * Query to find employees with matching email.
	 * @param email
	 * @return An Employee List with matching email.
	 */
	@Query(value = "SELECT employee.* emp FROM EMPLOYEE WHERE employee.email = ?1", nativeQuery = true)
	List<Employee> findByEmail(@Param(value = "email") String email);

	/**
	 * Query to find an employee by it´s user id.
	 * @param userId
	 * @return An Employee with matching user id.
	 */
	@Query(value = "SELECT * FROM EMPLOYEE e WHERE e.user_id = ?1", nativeQuery = true)
	Employee findEmployeeByUserId(String userId);
}
