# spring-boot-batch-file-upload-service

Spring Boot app using Spring Batch and Spring reactive for the batch processing.
- This Service will import the CSV data of Customer and load it into database
- We have used Spring Batch along with Spring reactive framework
- There are 3 main process
    1. Reader 
    2. Processor
    3. Write

## Architecture Overview
![spring-batch-architecture.png](src%2Fmain%2Fresources%2Fdiagrams%2Fspring-batch-architecture.png)

## Flow Diagram 
![spring-batch-flow-diagram.png](src%2Fmain%2Fresources%2Fdiagrams%2Fspring-batch-flow-diagram.png)

## Sequence Diagram
![spring-batch-sequence-diagram.png](src%2Fmain%2Fresources%2Fdiagrams%2Fspring-batch-sequence-diagram.png)

### SQL Queries:

- select * from dev.customers_info;
- select * from dev.batch_job_execution;
- select * from dev.batch_job_execution_context;
- select * from dev.batch_job_execution_params;
- select * from dev.batch_job_instance;
- select * from dev.batch_step_execution;
- select * from dev.batch_step_execution_context;