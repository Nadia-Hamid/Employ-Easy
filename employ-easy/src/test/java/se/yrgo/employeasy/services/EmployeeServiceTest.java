package se.yrgo.employeasy.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.yrgo.employeasy.dto.EmployeeDTO;
import se.yrgo.employeasy.entities.Employee;
import se.yrgo.employeasy.entities.enums.EmployeeStatus;
import se.yrgo.employeasy.entities.enums.SystemStatus;
import se.yrgo.employeasy.exceptions.ConflictException;
import se.yrgo.employeasy.exceptions.ObjectNotFoundException;
import se.yrgo.employeasy.repositories.EmployeeRepository;

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
    void findEmployeeByBadUserId() {
        when(mockedEmployeeRepository.findEmployeeByUserId(any(String.class))).thenReturn(null);
        assertThrows(ObjectNotFoundException.class, () -> employeeServiceTest.getEmployeeByUserId("BadUserId"));
    }

    @Test
    void findByEmailUsingNull() {
        assertThrows(NullPointerException.class, () -> employeeServiceTest.findByEmail(null));
    }

    @Test
    void findByEmailNonExisting() {
        when(mockedEmployeeRepository.findByEmail(any(String.class))).thenReturn(new ArrayList<>());
        assertThrows(ObjectNotFoundException.class, () -> employeeServiceTest.findByEmail("NotExisting"));
    }

    @Test
    void findByJobTitleNotExisting() {
        when(mockedEmployeeRepository.findByJobTitle(any(String.class))).thenReturn(new ArrayList<>());
        assertThrows(ObjectNotFoundException.class, () -> employeeServiceTest.findByJobTitle("NotExisting"));
    }

    @Nested
    class TestRequiringSingleton{

        private final Employee emp = new Employee(
                -1,
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

        @Test
        void addEmployeeUsingBadEmail() {
            emp.setEmail(null);
            assertThrows(NullPointerException.class, () -> employeeServiceTest.addEmployee(new EmployeeDTO(emp)));
        }

        @Test
        void addEmployeeUsingConflictingEmail() {
            when(mockedEmployeeRepository.findByEmail("marius@gmail.com")).thenReturn(List.of(emp));
            assertThrows(ConflictException.class, () -> employeeServiceTest.addEmployee(new EmployeeDTO(emp)));
        }

        @Test
        void addEmployeeUsingCorrectEmail() {
            EmployeeDTO result = employeeServiceTest.addEmployee(new EmployeeDTO(emp));
            verify(mockedEmployeeRepository, times(1)).save(any(Employee.class));
            assertEquals(emp.getEmail(), result.getEmail());
            assertNotEquals(emp.getUserId(), result.getUserId());
        }

        @Test
        void getByGoodUserId() {
            final String existingUserId = emp.getUserId();
            when(mockedEmployeeRepository.findEmployeeByUserId(existingUserId)).thenReturn(emp);
            assertEquals(existingUserId, employeeServiceTest.getByUserId(existingUserId).getUserId());
        }

        @Test
        void updateEmployee() {
            when(mockedEmployeeRepository.findEmployeeByUserId(any(String.class))).thenReturn(emp);
            final String newEmail = "new@email.com";
            emp.setEmail(newEmail);
            when(mockedEmployeeRepository.save(any(Employee.class))).thenReturn(emp);
            assertEquals(newEmail, employeeServiceTest.updateEmployee(new EmployeeDTO(emp)).getEmail());
        }

        @Test
        void deleteEmployeeExisting() {
            when(mockedEmployeeRepository.findEmployeeByUserId(emp.getUserId())).thenReturn(emp);
            employeeServiceTest.deleteEmployee(emp.getUserId());
            verify(mockedEmployeeRepository, times(1)).delete(emp);
        }

        @Test
        void findByEmailSingle() {
            when(mockedEmployeeRepository.findByEmail(any(String.class))).thenReturn(List.of(emp));
            assertEquals(new EmployeeDTO(emp), employeeServiceTest.findByEmail(emp.getEmail()));
        }
    }

    @Nested
    class TestRequiringList{

        private final List<Employee> employees = new ArrayList<>();

        @BeforeEach
        void setUp() {
            employees.add(new Employee(
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
            ));
            employees.add(new Employee(
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
            ));
        }

        @Test
        void findAll() {
            when(mockedEmployeeRepository.findAll()).thenReturn(employees);
            assertEquals(2, employeeServiceTest.findAll().size());
        }

        @Test
        void findByEmailMultiple() {
            when(mockedEmployeeRepository.findByEmail(any(String.class))).thenReturn(employees);
            Throwable conflictException = assertThrows(ConflictException.class, () -> employeeServiceTest.findByEmail(employees.get(0).getEmail()));
            assertEquals("Several instances with email " + employees.get(1).getEmail().toLowerCase() + " was found", conflictException.getMessage());
        }

        @Test
        void findByJobTitleExisting() {
            when(mockedEmployeeRepository.findByJobTitle("developer")).thenReturn(employees);
            assertEquals(employees.size(), employeeServiceTest.findByJobTitle("developer").size());
        }
    }
}
