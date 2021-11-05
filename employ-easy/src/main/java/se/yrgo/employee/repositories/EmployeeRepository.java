package se.yrgo.employee.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import se.yrgo.employee.domain.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	@Query(value = "SELECT employee.* emp FROM EMPLOYEE WHERE employee.job_title = ?1", nativeQuery = true)
	public List<Employee> findByJobTitle(@Param(value = "jobTitle") String jobTitle);
}
