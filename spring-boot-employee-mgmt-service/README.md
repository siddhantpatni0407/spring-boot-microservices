# spring-boot-employee-mgmt-service

Spring Boot App for Employee Management using Spring Reactive.

- This Service manage all employee data into employee_mgmt_db database
- There are 5 endpoints has been exposed in the service
    1. Add Employee
    2. Get Employee by Id
    3. Get All Employee
    4. Update Employee
    5. Delete Employee

## Architecture Overview

![employee-mgmt-flow-diagram.png](src%2Fmain%2Fresources%2Femployee-mgmt-flow-diagram.png)

## Sequence Diagram

![employee-mgmt-sequence-diagram.png](src%2Fmain%2Fresources%2Femployee-mgmt-sequence-diagram.png)

### SQL Queries:

- select * from dev.employee;