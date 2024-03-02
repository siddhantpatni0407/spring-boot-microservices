# batch-processing-service

Spring Boot app using Spring Batch and Spring reactive for the batch processing.
- This Service will import the CSV data of Customer and load it into database
- We have used Spring Batch along with Spring reactive framework
- There are 3 main process
  1. Read
  2. Process
  3. Write

## Architecture Overview

![batch-processing-architecture.png](src/main/resources/spring-batch-architecture.png)

### SQL Queries:

- select * from dev.customers_info; 
- select * from dev.batch_job_execution; 
- select * from dev.batch_job_execution_context; 
- select * from dev.batch_job_execution_params; 
- select * from dev.batch_job_instance; 
- select * from dev.batch_step_execution; 
- select * from dev.batch_step_execution_context;