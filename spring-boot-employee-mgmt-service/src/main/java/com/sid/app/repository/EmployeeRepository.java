package com.sid.app.repository;

import com.sid.app.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Siddhant Patni
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}