# Spring Boot "AccountManagement" Example Project
This is a sample Java / Maven / Springboot application. In this project I try to adhere to as many best practices as possible.

## How to Run

* You can build the project and run the tests by running ```mvn clean package```
* Or you can run all the tests and generate a Jacoco report by running ```mvn clean verify```

* To start the application run the following command: ```mvn spring-boot:run```

## About the Service

The service is just a account management REST service. It uses an in-memory database (H2) to store the data. By default it runs on (http://localhost:8080). All operations are validated against some built in logic. If input is invalid the service returns tailored error messages.

## Endpoints [CRUD]

### [C]reate an account resource
POST /v1/account/
Accept: application/json
Content-Type: application/json

{
  "type": "Current Account",
  "openingDate": "2022-08-08",
  "temporary": false,
  "closureDate": null,
  "initialDeposit": 1000000,
  "holder": {
    "firstName" : "John",
    "lastName" : "Doo",
    "dateOfBirth" : "2000-12-10",
    "email" : "test@domain.com"
  }
}

### [R]etrieve an account resource
GET /v1/account/<uuid>

### [U]pdate an account resource
POST /v1/account/
Accept: application/json
Content-Type: application/json

{
  "uuid": <valid uuid>,
  "type": "Current Account",
  "openingDate": "2022-08-08",
  "temporary": false,
  "closureDate": null,
  "initialDeposit": 1000000,
  "holder": {
    "firstName" : "John",
    "lastName" : "Doo",
    "dateOfBirth" : "2000-12-10",
    "email" : "test@domain.com"
  }
}
  
### [D]elete an account resource
Delete /v1/account/<uuid>
