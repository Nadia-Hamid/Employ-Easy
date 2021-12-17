package se.yrgo.employee.services;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.yrgo.employee.dto.EmployeeDTO;
import se.yrgo.employee.entities.Employee;
import se.yrgo.employee.exceptions.ObjectNotFoundException;
import se.yrgo.employee.repositories.EmployeeRepository;

@Service
@Transactional
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<EmployeeDTO> findAll() {
        return employeeRepository.findAll().stream().map(EmployeeDTO::new).collect(Collectors.toList());
    }

    public EmployeeDTO addEmployee(EmployeeDTO employeeDTO) {
        String prefix = employeeDTO.generateName();
        while (true) {
            String userId = prefix + Employee.generateSuffix();
            Employee existing = employeeRepository.findEmployeeByUserId(userId);
            if (existing == null) {
                Employee employee = new Employee(employeeDTO, userId);
                employeeRepository.save(employee);
                return new EmployeeDTO(employee);
            }
        }
    }

    public EmployeeDTO getByUserId(String userId) {
        Employee entity = employeeRepository.findEmployeeByUserId(userId);
        return new EmployeeDTO(entity);
    }

    public List<EmployeeDTO> findByJobTitle(String jobTitle) {
        return employeeRepository.findByJobTitle(jobTitle).stream().map(EmployeeDTO::new).collect(Collectors.toList());
    }

    public EmployeeDTO updateEmployee(EmployeeDTO employeeDTO) {
        Employee updatedEmployee = new Employee(employeeDTO, employeeDTO.getUserId());
        Employee employee = employeeRepository.save(updatedEmployee);
        return new EmployeeDTO(employee);
    }

    public void deleteEmployee(String userId) {
        try {
            Employee employee = employeeRepository.findEmployeeByUserId(userId);
            employeeRepository.delete(employee);
        } catch (EmptyResultDataAccessException e) {
            throw new ObjectNotFoundException("Object not found");
        }
    }

    public EmployeeDTO findByEmail(String email) {
        return new EmployeeDTO(employeeRepository.findByMail(email));
    }
}
