package org.javaguru.reward.calculation.rest.employee;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.reward.calculation.service.domain.Employee;
import org.javaguru.reward.calculation.service.repositories.EmployeeRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/test/employee")
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class GetEmployeeController {

    private final EmployeeRepository employeeRepository;

    @GetMapping(path = "/{id}",
            produces = "application/json")
    public GetEmployeeResponse getEmployee(@PathVariable Long id) {
        EmployeeDTO dto = searchEmployeeInDb(id);
        return new GetEmployeeResponse(dto);
    }

    private EmployeeDTO searchEmployeeInDb(Long id){
        Optional<Employee> employee = employeeRepository.findById(id);
        if(employee.isPresent()){
            return createEmployeeDTO(employee.get());
        }
        throw new IllegalArgumentException("Employee not found by id " + id);
    }

    private EmployeeDTO createEmployeeDTO(Employee employee){
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(employee.getId());
        employeeDTO.setFirstName(employee.getFirstName());
        employeeDTO.setLastName(employee.getLastName());
        employeeDTO.setBonusCoefficient(employee.getBonusCoefficient());
        return employeeDTO;
    }

}
