package se.yrgo.employeasy.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import se.yrgo.employeasy.entities.Employee;

/**
 * class EmployeeRepository
 * abstract Jpa Repository extension for Employee entities able to find employees by job, email and userid.
 * updated 2022-01-20
 */
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    /**
     * Get all employees using job title ignoring case.
     * @param jobTitle A name that describes someone's job or position.
     * @return Employee list matching job title
     */
    @Query(value = "SELECT * FROM EMPLOYEE e WHERE e.job_title = ?1", nativeQuery = true)
    List<Employee> findByJobTitle(@Param(value = "jobTitle") String jobTitle);

    /**
     * Get all employees using email ignoring case.
     * @param email An email belonging to a user. Probably unique.
     * @return Employee list matching email
     */
    @Query(value = "SELECT * FROM EMPLOYEE e WHERE e.email = ?1", nativeQuery = true)
    List<Employee> findByEmail(@Param(value = "email") String email);

    /**
     * Get employee from user id.
     * @param userId 10 letter string representation 6 first letter and 4 last number
     * @return Employee matching userId
     */
    @Query(value = "SELECT * FROM EMPLOYEE e WHERE e.user_id = ?1", nativeQuery = true)
    Employee findEmployeeByUserId(String userId);
}
