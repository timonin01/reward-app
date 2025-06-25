package org.javaguru.reward.calculation.rest.employee;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.reward.calculation.service.domain.Employee;
import org.javaguru.reward.calculation.service.repositories.EmployeeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/test/employee")
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class CreateEmployeeController {

    private final EmployeeRepository employeeRepository;

    @PostMapping(path = "/",
            consumes = "application/json",
            produces = "application/json"
    )
    public CreateEmployeeResponse createEmployee(@RequestBody CreateEmployeeRequest employeeRequest){
        Employee employee = storeEmployeeInDb(employeeRequest);
        EmployeeDTO dto = createEmployeeDto(employee);
        return new CreateEmployeeResponse(dto);
    }

    private Employee storeEmployeeInDb(CreateEmployeeRequest employeeRequest){
        Employee employee = new Employee();
        employee.setFirstName(employeeRequest.getFirstName());
        employee.setLastName(employeeRequest.getLastName());
        employee.setBonusCoefficient(employeeRequest.getBonusCoefficient());
        employeeRepository.save(employee);
        return employee;
    }

    private EmployeeDTO createEmployeeDto(Employee employee){
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(employee.getId());
        dto.setFirstName(employee.getFirstName());
        dto.setLastName(employee.getLastName());
        dto.setBonusCoefficient(employee.getBonusCoefficient());
        return dto;
    }

}
