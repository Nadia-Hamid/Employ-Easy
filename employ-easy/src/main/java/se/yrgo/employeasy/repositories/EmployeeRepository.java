package se.yrgo.employeasy.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import se.yrgo.employeasy.entities.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query(value = "SELECT employee.* emp FROM EMPLOYEE WHERE employee.job_title = ?1", nativeQuery = true)
    List<Employee> findByJobTitle(@Param(value = "jobTitle") String jobTitle);

    @Query(value = "SELECT employee.* emp FROM EMPLOYEE WHERE employee.email = ?1", nativeQuery = true)
    List<Employee> findByEmail(@Param(value = "email") String email);

    @Query(value = "SELECT * FROM EMPLOYEE e WHERE e.user_id = ?1", nativeQuery = true)
    Employee findEmployeeByUserId(String userId);
}
