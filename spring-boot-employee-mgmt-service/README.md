# spring-boot-employee-mgmt-service

Spring Boot App for Employee Management using Spring Reactive.

- This Service manage all employee data into employee_mgmt_db database
- There are 5 endpoints has been exposed in the service
    1. Add Employee
    2. Get All Employee
    3. Get Employee by id
    4. Update Employee
    5. Delete Employee

## Architecture Overview

![employee-mgmt-flow-diagram.png](src%2Fmain%2Fresources%2Femployee-mgmt-flow-diagram.png)

## Sequence Diagram

![employee-mgmt-sequence-diagram.png](src%2Fmain%2Fresources%2Femployee-mgmt-sequence-diagram.png)

## API Details

### 1. Add Employee

- URL - http://localhost:8080/api/v1/employee-mgmt-service/employee
- HTTP Method - POST
- Request -

````
{
    "firstName": "Siddhant",
    "lastName": "Patni",
    "department": "Engineering",
    "salary": 100000,
    "email": "siddhant.patni@gmail.com",
    "gender": "male",
    "contact": "7588811796",
    "country": "India",
    "dob": "04/07/1995"
}
````

- Response -

````
{
    "id": 1,
    "firstName": "Siddhant",
    "lastName": "Patni",
    "department": "Engineering",
    "salary": 100000.0,
    "email": "siddhant.patni@gmail.com",
    "gender": "male",
    "contact": "7588811796",
    "country": "India",
    "dob": "04/07/1995"
}
````

### 2. Get All Employees

- URL - http://localhost:8080/api/v1/employee-mgmt-service/employee
- HTTP Method - GET

- Response -

````
{
    "id": 1,
    "firstName": "Siddhant",
    "lastName": "Patni",
    "department": "Engineering",
    "salary": 100000.0,
    "email": "siddhant.patni@gmail.com",
    "gender": "male",
    "contact": "7588811796",
    "country": "India",
    "dob": "04/07/1995"
}
````

### 3. Get Employee by id

- URL - http://localhost:8080/api/v1/employee-mgmt-service/employee/1
- HTTP Method - GET

- Response -

````
{
    "id": 1,
    "firstName": "Siddhant",
    "lastName": "Patni",
    "department": "Engineering",
    "salary": 100000.0,
    "email": "siddhant.patni@gmail.com",
    "gender": "male",
    "contact": "7588811796",
    "country": "India",
    "dob": "04/07/1995"
}
````

### 4. Update Employee

- URL - http://localhost:8080/api/v1/employee-mgmt-service/employee/1
- HTTP Method - PUT

````
{
    "firstName": "Siddhant",
    "lastName": "Jain",
    "department": "Engineering",
    "salary": 100000,
    "email": "siddhant.jain@gmail.com",
    "gender": "male",
    "contact": "7588811796",
    "country": "India",
    "dob": "04/07/1995"
}
````

- Response -

````
{
    "id": 1,
    "firstName": "Siddhant",
    "lastName": "Jain",
    "department": "Engineering",
    "salary": 100000.0,
    "email": "siddhant.jain@gmail.com",
    "gender": "male",
    "contact": "7588811796",
    "country": "India",
    "dob": "04/07/1995"
}
````

### 5. Delete Employee

- URL - http://localhost:8080/api/v1/employee-mgmt-service/employee/1
- HTTP Method - DELETE

- Response -

````
User with id 1 has been deleted successfully.
````

### Front End App Details :

- App Name - employee-mgmt-ui
- Platform - ReactJS
- URL - http://localhost:3000

### SQL Queries:

- select * from dev.employee;