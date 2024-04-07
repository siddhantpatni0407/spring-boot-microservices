# spring-boot-jwt-service
Spring Boot App to Generate and manage JWT

Note : Execute sql_scritps.sql once application is up for the first time.


## SQ Scripts : 

```SQL
select * from dev.role;
select * from dev.user;
select * from dev.user_role;
insert into dev.role(id, name) values(1, 'ROLE_USER');
insert into dev.role(id, name) values(2, 'ROLE_ADMIN');
```