# spring-boot-file-storage-service

Spring Boot App to Store and download the file from database.

- There are 3 endpoints
    1. Upload File
    2. Download File
    3. Get all file details 
    4. Delete File

### Front End App Details :

- App Name - file-storage-ui
- Platform - ReactJS
- URL - http://localhost:3000

### SQL Queries:

- select * from dev.file_details;

- Check Object Existence:  Verify that the large object with the specified object ID exists in the PostgreSQL database. You can do this by querying the database directly.

````sql
SELECT * FROM pg_largeobject WHERE loid = [object_id];
SELECT * FROM pg_largeobject;
DELETE FROM pg_largeobject;
````