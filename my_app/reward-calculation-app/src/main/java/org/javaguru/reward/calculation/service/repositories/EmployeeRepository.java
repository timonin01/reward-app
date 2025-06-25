package org.javaguru.reward.calculation.service.repositories;

import org.javaguru.reward.calculation.service.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findBId(Long emloyeeId);

}
