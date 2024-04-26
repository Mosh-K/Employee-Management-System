# Employee Management System

This is an Employee Management System built with Java, Spring Boot, and Maven. It provides a RESTful API for managing employee records.

## Features

- CRUD operations for employee records
- File-based storage for employee records
- Input validation for data integrity and security
- Concurrency control mechanisms
- Custom serialization and deserialization for the Employee class
- Algorithmic challenges such as sorting employees based on salary
- Design patterns to improve the structure and maintainability of the codebase
- HTTP server to expose the functionality via a REST API
- User interface developed with React.js

## Getting Started

### Prerequisites

- Java 11 or higher
- Maven 3.6.3 or higher
- Node.js and npm (for the React.js user interface)

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
1. Navigate to the React.js user interface directory
```bash
cd frontend
```
1. Install the required dependencies
```bash
npm install
```
1. Run the React.js application
```bash
npm start
```

## Usage
The application provides the following endpoints:

- `GET /employees`: Get all employees
- `GET /employees/{id}`: Get an employee by ID
- `POST /employees`: Add a new employee
- `PUT /employees/{id}`: Update an existing employee
- `DELETE /employees/{id}`: Delete an employee

The React.js user interface allows you to interact with the API to perform CRUD operations on employee records.

## Documentation
For more detailed information, please refer to the HELP.md file.

## License
MIT
