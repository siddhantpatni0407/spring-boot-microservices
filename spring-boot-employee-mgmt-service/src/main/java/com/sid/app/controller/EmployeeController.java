package com.sid.app.controller;

import com.sid.app.constant.AppConstants;
import com.sid.app.entity.Employee;
import com.sid.app.repository.EmployeeRepository;
import com.sid.app.service.EmployeeService;
import com.sid.app.utils.ApplicationUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@Slf4j
@CrossOrigin("http://localhost:3000")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;


    @Autowired
    private EmployeeRepository employeeRepository;

    @PostMapping(value = AppConstants.EMPLOYEE_ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
    private Mono<Employee> addEmployee(@RequestBody Employee request) {
        if (log.isInfoEnabled()) {
            log.info("addEmployee() : Add Employee - START");
        }
        return employeeService.addEmployee(request)
                .flatMap(employeeResponse -> {
                    if (log.isInfoEnabled()) {
                        log.info("addEmployee() : Add Employee API. Request -> {} and Response -> {}",
                                ApplicationUtils.getJSONString(request), ApplicationUtils.getJSONString(employeeResponse));
                        log.info("addEmployee() : Add Employee - END");
                    }
                    return Mono.just(employeeResponse);
                });
    }

    @GetMapping(value = AppConstants.EMPLOYEE_ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
    private Mono<List<Employee>> getAllEmployees() {
        if (log.isInfoEnabled()) {
            log.info("getAllEmployees() : Get all Employee - START");
        }
        return employeeService.getAllEmployees()
                .flatMap(employeesList -> {
                    if (log.isInfoEnabled()) {
                        log.info("getAllEmployees() : Get all Employee API. Response -> {}",
                                ApplicationUtils.getJSONString(employeesList));
                        log.info("getAllEmployees() : Get all Employee - END");
                    }
                    return Mono.just(employeesList);
                });

    }

    @GetMapping(value = AppConstants.EMPLOYEE_ENDPOINT + "/" + "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    private Mono<Employee> getEmployeeById(@PathVariable Long id) {
        if (log.isInfoEnabled()) {
            log.info("getEmployeeById() : Get Employee - START");
        }
        return employeeService.getEmployeeById(id)
                .flatMap(employee -> {
                    if (log.isInfoEnabled()) {
                        log.info("getEmployeeById() : Get Employee API. Request Param -> {} and Response -> {}",
                                id, ApplicationUtils.getJSONString(employee));
                        log.info("getEmployeeById() : Get Employee - END");
                    }
                    return Mono.just(employee);
                });

    }

    @PutMapping(value = AppConstants.EMPLOYEE_ENDPOINT + "/" + "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    private Mono<Employee> updateEmployee(@RequestBody Employee employeeRequest, @PathVariable Long id) {
        if (log.isInfoEnabled()) {
            log.info("updateEmployee() : Update Employee - START");
        }
        return employeeService.updateEmployee(employeeRequest, id)
                .flatMap(updatedEmployee -> {
                    if (log.isInfoEnabled()) {
                        log.info("updateEmployee() : Update Employee API. Request -> {}, Request Param -> {} and Response -> {}",
                                ApplicationUtils.getJSONString(employeeRequest), id, ApplicationUtils.getJSONString(updatedEmployee));
                        log.info("updateEmployee() : Update Employee - END");
                    }
                    return Mono.just(updatedEmployee);
                });

    }

    @DeleteMapping(value = AppConstants.EMPLOYEE_ENDPOINT + "/" + "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    private Mono<String> deleteEmployee(@PathVariable Long id) {
        if (log.isInfoEnabled()) {
            log.info("deleteEmployee() : Delete Employee - START");
        }
        return employeeService.deleteEmployee(id)
                .flatMap(deleteEmployeeResponse -> {
                    if (log.isInfoEnabled()) {
                        log.info("updateEmployee() : Delete Employee API. Request Param -> {} and Response -> {}",
                                id, deleteEmployeeResponse);
                        log.info("updateEmployee() : Delete Employee - END");
                    }
                    return Mono.just(deleteEmployeeResponse);
                });
    }


}