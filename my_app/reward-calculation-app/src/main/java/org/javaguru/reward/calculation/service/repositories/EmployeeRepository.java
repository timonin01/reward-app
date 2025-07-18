package org.javaguru.reward.calculation.service.repositories;

import io.micrometer.core.annotation.Timed;
import org.javaguru.reward.calculation.service.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Timed(value = "jpa_EmployeeRepository_findById", histogram = true)
    Optional<Employee> findById(Long emloyeeId);

}
