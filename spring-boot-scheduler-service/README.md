# spring-boot-scheduler-service

Spring Boot application for Scheduler services.

### Explanation:

- Scheduler based microservice can be used to Scheduled the jobs.
- @Scheduled is used to schedule the method execution.
- We can use different parameter within @Scheduled annotation as below
    1. fixedDelay
    2. fixedDelayString
    3. fixedRate
    4. fixedRateString
    5. initialDelay
    6. initialDelayString
    7. cron
- We can assign multiple thread for the execution using below spring properties.
  spring.task.scheduling.pool.size=10 (default size is 1)

## References

- https://www.youtube.com/watch?v=l-40aP9jCok
- https://crontab.cronhub.io/