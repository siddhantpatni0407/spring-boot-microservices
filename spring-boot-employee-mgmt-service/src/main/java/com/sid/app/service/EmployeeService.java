package com.sid.app.service;

import com.sid.app.entity.Employee;
import com.sid.app.exception.UserNotFoundException;
import com.sid.app.repository.EmployeeRepository;
import com.sid.app.utils.ApplicationUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author Siddhant Patni
 */

@Service
@Slf4j
@SuppressWarnings("PMD")
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Mono<Employee> addEmployee(Employee employee) {
        Employee employeeData = employeeRepository.save(employee);
        if (log.isDebugEnabled()) {
            log.debug("addEmployee() : employeeData -> {}", ApplicationUtils.getJSONString(employeeData));
        }
        return Mono.just(employeeData);
    }

    public Mono<List<Employee>> getAllEmployees() {
        List<Employee> allEmployee = employeeRepository.findAll();
        if (log.isDebugEnabled()) {
            log.debug("getAllEmployees() : allEmployee -> {}", ApplicationUtils.getJSONString(allEmployee));
        }
        return Mono.just(allEmployee);
    }


    public Mono<Employee> getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        if (log.isDebugEnabled()) {
            log.debug("getEmployeeById() : employee -> {}", ApplicationUtils.getJSONString(employee));
        }
        return Mono.just(employee);
    }


    public Mono<Employee> updateEmployee(Employee employeeRequest, Long id) {

        Employee updatedEmployee = employeeRepository.findById(id)
                .map(employee -> {
                    employee.setFirstName(employeeRequest.getFirstName());
                    employee.setLastName(employeeRequest.getLastName());
                    employee.setDepartment(employeeRequest.getDepartment());
                    employee.setSalary(employeeRequest.getSalary());
                    employee.setEmail(employeeRequest.getEmail());
                    employee.setGender(employeeRequest.getGender());
                    employee.setContact(employeeRequest.getContact());
                    employee.setCountry(employeeRequest.getCountry());
                    employee.setDob(employeeRequest.getDob());
                    return employeeRepository.save(employee);
                })
                .orElseThrow(() -> new UserNotFoundException(id));
        if (log.isDebugEnabled()) {
            log.debug("updateEmployee() : updatedEmployee -> {}", ApplicationUtils.getJSONString(updatedEmployee));
        }
        return Mono.just(updatedEmployee);

    }

    public Mono<String> deleteEmployee(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        employeeRepository.deleteById(id);
        if (log.isInfoEnabled()) {
            log.info("deleteEmployee() : User with id : {} has been deleted successfully", id);
        }
        return Mono.just("User with id " + id + " has been deleted successfully.");
    }

}