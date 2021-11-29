package se.yrgo.employee.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import se.yrgo.employee.entities.Employee;
import se.yrgo.employee.repositories.EmployeeRepository;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

	@Mock
	private EmployeeRepository mockedEmployeeRepository;

	@InjectMocks
	private EmployeeService employeeServiceTest;

	@Test
	void findAll() {
		employeeServiceTest = new EmployeeService(mockedEmployeeRepository);
		List<Employee> employeeList = new ArrayList<>();
		Employee emp = new Employee("Marius", "Marthinussen", "890519-XXXX", "Marius@gmail.com", "12345678",
				"Södra Vägen", "44556", "Göteborg", "developer", "volvo", LocalDate.of(2000, 1, 1), null);
		employeeList.add(emp);
		emp = new Employee("Nadia", "Hamid", "900519-XXXX", "Nadia@gmail.com", "87654321", "Norra Vägen", "44556",
				"Göteborg", "developer", "saab", LocalDate.of(2005, 1, 1), null);
		employeeList.add(emp);
		when(mockedEmployeeRepository.findAll()).thenReturn(employeeList);
		assertEquals(2, employeeServiceTest.findAll().size());
	}

	@Test
	void addEmployeeTest() {

		Employee emp = new Employee("Ana", "Beatriz", "890519-XXXX", "anna@gmail.com", "12345678", "Södra Vägen",
				"44556", "Göteborg", "developer", "volvo", LocalDate.of(2000, 1, 1), null);
		
		employeeServiceTest.addEmployee(emp);
		verify(mockedEmployeeRepository, times(1)).save(emp);
	}

	@Test
	void deleteEmployeeTest() {

		Employee emp = new Employee();
		emp.setfirstName("Test Name");
		emp.setId(3L);
		
		employeeServiceTest.deleteEmployee(emp.getId());
		verify(mockedEmployeeRepository).deleteById(emp.getId());
	}
}