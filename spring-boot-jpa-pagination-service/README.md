# spring-boot-jpa-pagination-service

Spring Boot JPA Pagination Service.

- This Service is used to implement the Pagination in JPA.
- There are 5 endpoints has been exposed in the service
    1. Add Products
    2. Get All Products
    3. Get Products with Sorting
    4. Get Products with Pagination
    5. Get Products with Pagination and Sorting

## Sequence Diagram

![jpa-pagination-service-sequence-diagram.png](src%2Fmain%2Fresources%2Fjpa-pagination-service-sequence-diagram.png)

## API Details

### 1. Add Products

- URL - http://localhost:8080/api/v1/jpa-pagination-service/products
- HTTP Method - POST
- Response -

````
[
    {
        "id": 1,
        "name": "product1",
        "quantity": 8,
        "price": 35112
    },
    {
        "id": 2,
        "name": "product2",
        "quantity": 64,
        "price": 39345
    },
    {
        "id": 3,
        "name": "product3",
        "quantity": 73,
        "price": 33185
    },
    {
        "id": 4,
        "name": "product4",
        "quantity": 2,
        "price": 21023
    },
    {
        "id": 5,
        "name": "product5",
        "quantity": 57,
        "price": 5178
    },
    {
        "id": 6,
        "name": "product6",
        "quantity": 10,
        "price": 9906
    },
    {
        "id": 7,
        "name": "product7",
        "quantity": 4,
        "price": 47532
    },
    {
        "id": 8,
        "name": "product8",
        "quantity": 90,
        "price": 17972
    },
    {
        "id": 9,
        "name": "product9",
        "quantity": 45,
        "price": 20576
    },
    {
        "id": 10,
        "name": "product10",
        "quantity": 66,
        "price": 35761
    },
    {
        "id": 11,
        "name": "product11",
        "quantity": 43,
        "price": 12866
    },
    {
        "id": 12,
        "name": "product12",
        "quantity": 55,
        "price": 24007
    },
    {
        "id": 13,
        "name": "product13",
        "quantity": 25,
        "price": 42869
    },
    {
        "id": 14,
        "name": "product14",
        "quantity": 84,
        "price": 17485
    },
    {
        "id": 15,
        "name": "product15",
        "quantity": 60,
        "price": 19993
    },
    {
        "id": 16,
        "name": "product16",
        "quantity": 62,
        "price": 6723
    },
    {
        "id": 17,
        "name": "product17",
        "quantity": 1,
        "price": 40757
    },
    {
        "id": 18,
        "name": "product18",
        "quantity": 45,
        "price": 40895
    },
    {
        "id": 19,
        "name": "product19",
        "quantity": 9,
        "price": 30701
    },
    {
        "id": 20,
        "name": "product20",
        "quantity": 85,
        "price": 8116
    },
    {
        "id": 21,
        "name": "product21",
        "quantity": 81,
        "price": 12696
    },
    {
        "id": 22,
        "name": "product22",
        "quantity": 54,
        "price": 3099
    },
    {
        "id": 23,
        "name": "product23",
        "quantity": 32,
        "price": 37575
    },
    {
        "id": 24,
        "name": "product24",
        "quantity": 46,
        "price": 39009
    },
    {
        "id": 25,
        "name": "product25",
        "quantity": 12,
        "price": 41414
    },
    {
        "id": 26,
        "name": "product26",
        "quantity": 97,
        "price": 18195
    },
    {
        "id": 27,
        "name": "product27",
        "quantity": 7,
        "price": 12553
    },
    {
        "id": 28,
        "name": "product28",
        "quantity": 88,
        "price": 38836
    },
    {
        "id": 29,
        "name": "product29",
        "quantity": 0,
        "price": 48569
    },
    {
        "id": 30,
        "name": "product30",
        "quantity": 57,
        "price": 27352
    }
]
````

### 2. Get All Employees

- URL - http://localhost:8080/api/v1/employee-mgmt-service/employee
- HTTP Method - GET

- Response -

````
{
    "recordCount": 30,
    "response": [
        {
            "id": 1,
            "name": "product1",
            "quantity": 8,
            "price": 35112
        },
        {
            "id": 2,
            "name": "product2",
            "quantity": 64,
            "price": 39345
        },
        {
            "id": 3,
            "name": "product3",
            "quantity": 73,
            "price": 33185
        },
        {
            "id": 4,
            "name": "product4",
            "quantity": 2,
            "price": 21023
        },
        {
            "id": 5,
            "name": "product5",
            "quantity": 57,
            "price": 5178
        },
        {
            "id": 6,
            "name": "product6",
            "quantity": 10,
            "price": 9906
        },
        {
            "id": 7,
            "name": "product7",
            "quantity": 4,
            "price": 47532
        },
        {
            "id": 8,
            "name": "product8",
            "quantity": 90,
            "price": 17972
        },
        {
            "id": 9,
            "name": "product9",
            "quantity": 45,
            "price": 20576
        },
        {
            "id": 10,
            "name": "product10",
            "quantity": 66,
            "price": 35761
        },
        {
            "id": 11,
            "name": "product11",
            "quantity": 43,
            "price": 12866
        },
        {
            "id": 12,
            "name": "product12",
            "quantity": 55,
            "price": 24007
        },
        {
            "id": 13,
            "name": "product13",
            "quantity": 25,
            "price": 42869
        },
        {
            "id": 14,
            "name": "product14",
            "quantity": 84,
            "price": 17485
        },
        {
            "id": 15,
            "name": "product15",
            "quantity": 60,
            "price": 19993
        },
        {
            "id": 16,
            "name": "product16",
            "quantity": 62,
            "price": 6723
        },
        {
            "id": 17,
            "name": "product17",
            "quantity": 1,
            "price": 40757
        },
        {
            "id": 18,
            "name": "product18",
            "quantity": 45,
            "price": 40895
        },
        {
            "id": 19,
            "name": "product19",
            "quantity": 9,
            "price": 30701
        },
        {
            "id": 20,
            "name": "product20",
            "quantity": 85,
            "price": 8116
        },
        {
            "id": 21,
            "name": "product21",
            "quantity": 81,
            "price": 12696
        },
        {
            "id": 22,
            "name": "product22",
            "quantity": 54,
            "price": 3099
        },
        {
            "id": 23,
            "name": "product23",
            "quantity": 32,
            "price": 37575
        },
        {
            "id": 24,
            "name": "product24",
            "quantity": 46,
            "price": 39009
        },
        {
            "id": 25,
            "name": "product25",
            "quantity": 12,
            "price": 41414
        },
        {
            "id": 26,
            "name": "product26",
            "quantity": 97,
            "price": 18195
        },
        {
            "id": 27,
            "name": "product27",
            "quantity": 7,
            "price": 12553
        },
        {
            "id": 28,
            "name": "product28",
            "quantity": 88,
            "price": 38836
        },
        {
            "id": 29,
            "name": "product29",
            "quantity": 0,
            "price": 48569
        },
        {
            "id": 30,
            "name": "product30",
            "quantity": 57,
            "price": 27352
        }
    ]
}
````

### 3. Get Products with Sorting

- URL - http://localhost:8080/api/v1/jpa-pagination-service/products/sort?field=price
- HTTP Method - GET

- Response -

````
{
    "recordCount": 30,
    "response": [
        {
            "id": 22,
            "name": "product22",
            "quantity": 54,
            "price": 3099
        },
        {
            "id": 5,
            "name": "product5",
            "quantity": 57,
            "price": 5178
        },
        {
            "id": 16,
            "name": "product16",
            "quantity": 62,
            "price": 6723
        },
        {
            "id": 20,
            "name": "product20",
            "quantity": 85,
            "price": 8116
        },
        {
            "id": 6,
            "name": "product6",
            "quantity": 10,
            "price": 9906
        },
        {
            "id": 27,
            "name": "product27",
            "quantity": 7,
            "price": 12553
        },
        {
            "id": 21,
            "name": "product21",
            "quantity": 81,
            "price": 12696
        },
        {
            "id": 11,
            "name": "product11",
            "quantity": 43,
            "price": 12866
        },
        {
            "id": 14,
            "name": "product14",
            "quantity": 84,
            "price": 17485
        },
        {
            "id": 8,
            "name": "product8",
            "quantity": 90,
            "price": 17972
        },
        {
            "id": 26,
            "name": "product26",
            "quantity": 97,
            "price": 18195
        },
        {
            "id": 15,
            "name": "product15",
            "quantity": 60,
            "price": 19993
        },
        {
            "id": 9,
            "name": "product9",
            "quantity": 45,
            "price": 20576
        },
        {
            "id": 4,
            "name": "product4",
            "quantity": 2,
            "price": 21023
        },
        {
            "id": 12,
            "name": "product12",
            "quantity": 55,
            "price": 24007
        },
        {
            "id": 30,
            "name": "product30",
            "quantity": 57,
            "price": 27352
        },
        {
            "id": 19,
            "name": "product19",
            "quantity": 9,
            "price": 30701
        },
        {
            "id": 3,
            "name": "product3",
            "quantity": 73,
            "price": 33185
        },
        {
            "id": 1,
            "name": "product1",
            "quantity": 8,
            "price": 35112
        },
        {
            "id": 10,
            "name": "product10",
            "quantity": 66,
            "price": 35761
        },
        {
            "id": 23,
            "name": "product23",
            "quantity": 32,
            "price": 37575
        },
        {
            "id": 28,
            "name": "product28",
            "quantity": 88,
            "price": 38836
        },
        {
            "id": 24,
            "name": "product24",
            "quantity": 46,
            "price": 39009
        },
        {
            "id": 2,
            "name": "product2",
            "quantity": 64,
            "price": 39345
        },
        {
            "id": 17,
            "name": "product17",
            "quantity": 1,
            "price": 40757
        },
        {
            "id": 18,
            "name": "product18",
            "quantity": 45,
            "price": 40895
        },
        {
            "id": 25,
            "name": "product25",
            "quantity": 12,
            "price": 41414
        },
        {
            "id": 13,
            "name": "product13",
            "quantity": 25,
            "price": 42869
        },
        {
            "id": 7,
            "name": "product7",
            "quantity": 4,
            "price": 47532
        },
        {
            "id": 29,
            "name": "product29",
            "quantity": 0,
            "price": 48569
        }
    ]
}
````

### 4. Get Products with Pagination

- URL - http://localhost:8080/api/v1/jpa-pagination-service/products/pagination?offset=0&pageSize=10
- HTTP Method - GET

````
{
    "recordCount": 10,
    "response": {
        "content": [
            {
                "id": 1,
                "name": "product1",
                "quantity": 8,
                "price": 35112
            },
            {
                "id": 2,
                "name": "product2",
                "quantity": 64,
                "price": 39345
            },
            {
                "id": 3,
                "name": "product3",
                "quantity": 73,
                "price": 33185
            },
            {
                "id": 4,
                "name": "product4",
                "quantity": 2,
                "price": 21023
            },
            {
                "id": 5,
                "name": "product5",
                "quantity": 57,
                "price": 5178
            },
            {
                "id": 6,
                "name": "product6",
                "quantity": 10,
                "price": 9906
            },
            {
                "id": 7,
                "name": "product7",
                "quantity": 4,
                "price": 47532
            },
            {
                "id": 8,
                "name": "product8",
                "quantity": 90,
                "price": 17972
            },
            {
                "id": 9,
                "name": "product9",
                "quantity": 45,
                "price": 20576
            },
            {
                "id": 10,
                "name": "product10",
                "quantity": 66,
                "price": 35761
            }
        ],
        "pageable": {
            "sort": {
                "empty": true,
                "sorted": false,
                "unsorted": true
            },
            "offset": 0,
            "pageNumber": 0,
            "pageSize": 10,
            "paged": true,
            "unpaged": false
        },
        "last": false,
        "totalPages": 3,
        "totalElements": 30,
        "size": 10,
        "number": 0,
        "first": true,
        "numberOfElements": 10,
        "sort": {
            "empty": true,
            "sorted": false,
            "unsorted": true
        },
        "empty": false
    }
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
    "dob": "1995-07-04"
}
````

### 5. Get Products with Pagination and Sorting

- URL - http://localhost:8080/api/v1/jpa-pagination-service/products/paginationAndSort?offset=0&pageSize=10&field=price
- HTTP Method - GET

- Response -

````
{
    "recordCount": 10,
    "response": {
        "content": [
            {
                "id": 22,
                "name": "product22",
                "quantity": 54,
                "price": 3099
            },
            {
                "id": 5,
                "name": "product5",
                "quantity": 57,
                "price": 5178
            },
            {
                "id": 16,
                "name": "product16",
                "quantity": 62,
                "price": 6723
            },
            {
                "id": 20,
                "name": "product20",
                "quantity": 85,
                "price": 8116
            },
            {
                "id": 6,
                "name": "product6",
                "quantity": 10,
                "price": 9906
            },
            {
                "id": 27,
                "name": "product27",
                "quantity": 7,
                "price": 12553
            },
            {
                "id": 21,
                "name": "product21",
                "quantity": 81,
                "price": 12696
            },
            {
                "id": 11,
                "name": "product11",
                "quantity": 43,
                "price": 12866
            },
            {
                "id": 14,
                "name": "product14",
                "quantity": 84,
                "price": 17485
            },
            {
                "id": 8,
                "name": "product8",
                "quantity": 90,
                "price": 17972
            }
        ],
        "pageable": {
            "sort": {
                "empty": false,
                "sorted": true,
                "unsorted": false
            },
            "offset": 0,
            "pageNumber": 0,
            "pageSize": 10,
            "paged": true,
            "unpaged": false
        },
        "last": false,
        "totalPages": 3,
        "totalElements": 30,
        "size": 10,
        "number": 0,
        "first": true,
        "numberOfElements": 10,
        "sort": {
            "empty": false,
            "sorted": true,
            "unsorted": false
        },
        "empty": false
    }
}
````

### SQL Queries:

- select * from dev.product;