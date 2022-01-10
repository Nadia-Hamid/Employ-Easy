package se.yrgo.employee.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.yrgo.employee.dto.EmployeeDTO;
import se.yrgo.employee.entities.Employee;
import se.yrgo.employee.exceptions.ConflictException;
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

    private EmployeeDTO dto(Employee employee){
        return new EmployeeDTO(employee);
    }

    public EmployeeDTO addEmployee(EmployeeDTO employeeDTO) {
        final String newEmail = employeeDTO.getEmail();
        if(employeeRepository.findByEmail(newEmail).size() > 0){
            throw new ConflictException("Employee with unique email " + newEmail + " was already added.");
        }
        String prefix = employeeDTO.generateName();
        while (true) {
            String userId = prefix + Employee.generateSuffix();
            Employee existing = employeeRepository.findEmployeeByUserId(userId);
            if (existing == null) {
                Employee employee = new Employee(employeeDTO, userId);
                employeeRepository.save(employee);
                return dto(employee);
            }
        }
    }

    private Employee getEmployeeByUserId(String userId) {
        Employee entity = employeeRepository.findEmployeeByUserId(userId.toLowerCase());
        if(entity == null){
            throw new ObjectNotFoundException("User was not found");
        }
        return entity;
    }

    public EmployeeDTO getByUserId(String userId) {
        return dto(getEmployeeByUserId(userId));
    }

    public List<EmployeeDTO> findByJobTitle(String jobTitle) {
        return employeeRepository
                .findByJobTitle(jobTitle.toLowerCase())
                .stream()
                .map(EmployeeDTO::new)
                .collect(Collectors.collectingAndThen(Collectors.toList(), Optional::of))
                .filter(l -> !l.isEmpty())
                .orElseThrow(() -> new ObjectNotFoundException("No user with job title " + jobTitle + " was found"));
    }

    public EmployeeDTO updateEmployee(EmployeeDTO employeeDTO) {
        Employee emp = getEmployeeByUserId(employeeDTO.getUserId());
        Employee updatedEmployee = new Employee(employeeDTO, emp.getId());
        Employee employee = employeeRepository.save(updatedEmployee);
        return dto(employee);
    }

    public void deleteEmployee(String userId) {
        Employee employee = employeeRepository.findEmployeeByUserId(userId.toLowerCase());
        if(employee == null){
            throw new ObjectNotFoundException("User to be deleted was not found");
        }
        employeeRepository.delete(employee);
    }

    public EmployeeDTO findByEmail(String email) {
        var getEmployeeByEmail = employeeRepository.findByEmail(email.toLowerCase());
        var size = getEmployeeByEmail.size();
        if(size < 1){
            throw new ObjectNotFoundException("No user with email " + email + " was found");
        } else if(size > 1){
            throw new ConflictException("Several instances with email " + email + " was found");
        }
        return dto(getEmployeeByEmail.get(0));
    }
}
