package se.yrgo.employee.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.yrgo.employee.dto.EmployeeDTO;
import se.yrgo.employee.entities.Employee;
import se.yrgo.employee.entities.enums.EmployeeStatus;
import se.yrgo.employee.entities.enums.SystemStatus;
import se.yrgo.employee.exceptions.ConflictException;
import se.yrgo.employee.exceptions.ObjectNotFoundException;
import se.yrgo.employee.repositories.EmployeeRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
        Employee emp = new Employee(
                -1L,
            "Marius",
            "Marthinussen",
            "890519-XXXX",
            "Marius@gmail.com",
            "12345678",
            "Södra Vägen",
            "44556",
            "Göteborg",
            "developer",
            "volvo",
            LocalDate.of(2000, 1, 1),
            null,
            EmployeeStatus.ACTIVE,
            SystemStatus.USER
        );
        employeeList.add(emp);
        emp =
            new Employee(
                    -1L,
                "Nadia",
                "Hamid",
                "900519-XXXX",
                "Nadia@gmail.com",
                "87654321",
                "Norra Vägen",
                "44556",
                "Göteborg",
                "developer",
                "saab",
                LocalDate.of(2005, 1, 1),
                null,
                EmployeeStatus.VACATION,
                SystemStatus.SYSTEM_ADMIN
            );
        employeeList.add(emp);
        when(mockedEmployeeRepository.findAll()).thenReturn(employeeList);
        assertEquals(2, employeeServiceTest.findAll().size());
    }

    @Test
    void addEmployeeUsingBadEmail() {
        Employee emp = new Employee(
                1234,
                "Marius",
                "Marthinussen",
                "890519-XXXX",
                null,
                "12345678",
                "Södra Vägen",
                "44556",
                "Göteborg",
                "developer",
                "volvo",
                LocalDate.of(2000, 1, 1),
                null,
                EmployeeStatus.ACTIVE,
                SystemStatus.USER
        );
        assertThrows(ConflictException.class, () -> employeeServiceTest.addEmployee(new EmployeeDTO(emp)));
    }

    @Test
    void addEmployeeUsingCorrectEmail() {
        Employee emp = new Employee(
                1234,
                "Marius",
                "Marthinussen",
                "890519-XXXX",
                "Marius@gmail.com",
                "12345678",
                "Södra Vägen",
                "44556",
                "Göteborg",
                "developer",
                "volvo",
                LocalDate.of(2000, 1, 1),
                null,
                EmployeeStatus.ACTIVE,
                SystemStatus.USER
        );
        EmployeeDTO result = employeeServiceTest.addEmployee(new EmployeeDTO(emp));
        verify(mockedEmployeeRepository, times(1)).save(any(Employee.class));
        assertEquals(emp.getEmail(), result.getEmail());
        assertNotEquals(emp.getUserId(), result.getUserId());
    }

    @Test
    void getByBadUserId() {
        when(mockedEmployeeRepository.findEmployeeByUserId(any(String.class))).thenReturn(null);
        assertThrows(ObjectNotFoundException.class, () -> employeeServiceTest.getByUserId("BadUserId"));
    }

    @Test
    void getByGoodUserId() {
        Employee emp = new Employee(
                1234,
                "Marius",
                "Marthinussen",
                "890519-XXXX",
                "Marius@gmail.com",
                "12345678",
                "Södra Vägen",
                "44556",
                "Göteborg",
                "developer",
                "volvo",
                LocalDate.of(2000, 1, 1),
                null,
                EmployeeStatus.ACTIVE,
                SystemStatus.USER
        );
        final String existingUserId = "marmar1234";
        when(mockedEmployeeRepository.findEmployeeByUserId(existingUserId)).thenReturn(emp);
        assertEquals(existingUserId, employeeServiceTest.getByUserId(existingUserId).getUserId());
    }

    @Test
    void updateEmployee() {
        Employee emp = new Employee(-1, "Marius", "Marthinussen", "890519-XXXX", "Marius@gmail.com", "12345678",
				"Södra Vägen", "44556", "Göteborg", "developer", "volvo", LocalDate.of(2000, 1, 1), null, EmployeeStatus.ACTIVE, SystemStatus.USER);
		when(mockedEmployeeRepository.findEmployeeByUserId(any(String.class))).thenReturn(emp);
		final String newEmail = "new@email.com";
        emp.setEmail(newEmail);
		when(mockedEmployeeRepository.save(any(Employee.class))).thenReturn(emp);
        assertEquals(newEmail, employeeServiceTest.updateEmployee(new EmployeeDTO(emp)).getEmail());
    }

    @Test
    void deleteEmployeeNotExisting() {
        when(mockedEmployeeRepository.findEmployeeByUserId(any(String.class))).thenReturn(null);
        assertThrows(ObjectNotFoundException.class, () -> employeeServiceTest.deleteEmployee("badUserId"));
    }

    @Test
    void deleteEmployeeExisting() {
        Employee emp = new Employee(
                -1L,
            "Marius",
            "Marthinussen",
            "890519-XXXX",
            "Marius@gmail.com",
            "12345678",
            "Södra Vägen",
            "44556",
            "Göteborg",
            "developer",
            "volvo",
            LocalDate.of(2000, 1, 1),
            null,
            EmployeeStatus.ACTIVE,
            SystemStatus.USER
        );
        when(mockedEmployeeRepository.findEmployeeByUserId(emp.getUserId())).thenReturn(emp);
        employeeServiceTest.deleteEmployee(emp.getUserId());
        verify(mockedEmployeeRepository, times(1)).delete(emp);
    }

    @Test
    void findByEmailUsingNull() {
        Throwable conflictException = assertThrows(ConflictException.class, () -> employeeServiceTest.findByEmail(null));
        assertEquals("Null email value not allowed!", conflictException.getMessage());
    }

    @Test
    void findByEmailNonExisting() {
        when(mockedEmployeeRepository.findByEmail(any(String.class))).thenReturn(new ArrayList<>());
        assertThrows(ObjectNotFoundException.class, () -> employeeServiceTest.findByEmail("NotExisting"));
    }

    @Test
    void findByEmailMultiple() {
        List<Employee> employeeList = new ArrayList<>();
        final Employee emp1 = new Employee(
                -1L,
                "Marius",
                "Marthinussen",
                "890519-XXXX",
                "Marius@gmail.com",
                "12345678",
                "Södra Vägen",
                "44556",
                "Göteborg",
                "developer",
                "volvo",
                LocalDate.of(2000, 1, 1),
                null,
                EmployeeStatus.ACTIVE,
                SystemStatus.USER
        );
        employeeList.add(emp1);
        final Employee emp2 =
                new Employee(
                        -1L,
                        "Nadia",
                        "Hamid",
                        "900519-XXXX",
                        "Marius@gmail.com",
                        "87654321",
                        "Norra Vägen",
                        "44556",
                        "Göteborg",
                        "developer",
                        "saab",
                        LocalDate.of(2005, 1, 1),
                        null,
                        EmployeeStatus.VACATION,
                        SystemStatus.SYSTEM_ADMIN
                );
        employeeList.add(emp2);
        when(mockedEmployeeRepository.findByEmail(any(String.class))).thenReturn(employeeList);
        Throwable conflictException = assertThrows(ConflictException.class, () -> employeeServiceTest.findByEmail(emp1.getEmail()));
        assertEquals("Several instances with email " + emp2.getEmail().toLowerCase() + " was found", conflictException.getMessage());
    }

    @Test
    void findByEmailSingle() {
        List<Employee> employeeList = new ArrayList<>();
        final Employee emp1 = new Employee(
                -1L,
                "Marius",
                "Marthinussen",
                "890519-XXXX",
                "Marius@gmail.com",
                "12345678",
                "Södra Vägen",
                "44556",
                "Göteborg",
                "developer",
                "volvo",
                LocalDate.of(2000, 1, 1),
                null,
                EmployeeStatus.ACTIVE,
                SystemStatus.USER
        );
        employeeList.add(emp1);
        when(mockedEmployeeRepository.findByEmail(any(String.class))).thenReturn(employeeList);
        assertEquals(new EmployeeDTO(emp1), employeeServiceTest.findByEmail(emp1.getEmail()));
    }

    @Test
    void findByJobTitleNotExisting() {
        when(mockedEmployeeRepository.findByJobTitle(any(String.class))).thenReturn(new ArrayList<>());
        assertThrows(ObjectNotFoundException.class, () -> employeeServiceTest.findByJobTitle("NotExisting"));
    }

    @Test
    void findByJobTitleExisting() {
        List<Employee> employeeList = new ArrayList<>();
        final Employee emp1 = new Employee(
                -1L,
                "Marius",
                "Marthinussen",
                "890519-XXXX",
                "Marius@gmail.com",
                "12345678",
                "Södra Vägen",
                "44556",
                "Göteborg",
                "developer",
                "volvo",
                LocalDate.of(2000, 1, 1),
                null,
                EmployeeStatus.ACTIVE,
                SystemStatus.USER
        );
        employeeList.add(emp1);
        final Employee emp2 =
                new Employee(
                        -1L,
                        "Nadia",
                        "Hamid",
                        "900519-XXXX",
                        "Marius@gmail.com",
                        "87654321",
                        "Norra Vägen",
                        "44556",
                        "Göteborg",
                        "developer",
                        "saab",
                        LocalDate.of(2005, 1, 1),
                        null,
                        EmployeeStatus.VACATION,
                        SystemStatus.SYSTEM_ADMIN
                );
        employeeList.add(emp2);
        when(mockedEmployeeRepository.findByJobTitle("developer")).thenReturn(employeeList);
        assertEquals(employeeList.size(), employeeServiceTest.findByJobTitle("developer").size());
    }
}
