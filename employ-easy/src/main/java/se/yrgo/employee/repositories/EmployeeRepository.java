package se.yrgo.employee.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import se.yrgo.employee.domain.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	
}
