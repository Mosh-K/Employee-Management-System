# Employee Management System

This is an Employee Management System built with Java, Spring Boot, and Maven. It provides a RESTful API for managing employee records.

## Features

- CRUD operations for employee records
- File-based storage for employee records
- Input validation for data integrity and security
- Design patterns to improve the structure and maintainability of the codebase
- HTTP server to expose the functionality via a REST API

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6.3 or higher

### Installation

1. Clone the repository

```bash
git clone https://github.com/Mosh-K/employee-management-system.git
```

1. Navigate to the project directory

```bash
cd employee-management-system
```

1. Build the project using Maven

```bash
mvn clean install
```

1. Run the spring boot application

```bash
mvn spring-boot:run
```

## Initial Data

Upon starting the server, a few employee records will be automatically created for convenience. These records are stored in the `employees` directory and can be viewed immediately via a GET request to the `/employees` endpoint.

## Testing

The project includes several unit tests to ensure the functionality of the system. The tests are located in the `src/test/java/com/employee` directory.

Here are the test files and their locations:

- `empSys/controllers/EmployeeControllerTest.java`: This file contains tests for the EmployeeController class.
- `model/EmployeeTest.java`: This file contains tests for the Employee model class.
- `storage/FileStorageTest.java`: This file contains tests for the FileStorage class.

To run the tests, navigate to the project directory and use the following command:

```bash
mvn test
```

## Usage

The application provides the following endpoints:

- `GET /employees`: Get all employees
- `GET /employees/{id}`: Get an employee by ID
- `POST /employees`: Add a new employee
- `PUT /employees/{id}`: Update an existing employee
- `DELETE /employees/{id}`: Delete an employee

### Employee Format

For the `POST /employees` and `PUT /employees/{id}` endpoints, the employee data should be provided in the following format:

```json
{
  "id": "1",
  "name": "John Doe",
  "position": "Engineer",
  "salary": 50000.0
}
```
