### Assignment: Advanced Java Programming Challenge - Employee Management System

**Overview**: Design and implement an advanced employee management system in Java as an HTTP server, focusing on complex programming tasks and challenges.

### Requirements:

1. **Employee Class:** Implement a class named **Employee** with the following attributes:

   - ID
   - Name
   - Position
   - Salary

2. **Data Storage:** Utilize simple file-based storage for employee records. Each employee should be stored as a separate record.

3. **CRUD Operations**:

   - **Create**: Implement a method to add a new employee to the system. Each new employee should be stored in a separate file.
   - **Read**: Implement methods to retrieve information about employees based on various criteria (e.g., ID, name, position).
   - **Update**: Implement methods to update the information of an existing employee.
   - **Delete**: Implement a method to remove an employee from the system.

4. **Validation:** Implement comprehensive input validation to ensure data integrity and security. Include validation for all fields, as well as checks for data consistency and integrity constraints.

5. **Concurrency Control:** Implement basic concurrency control mechanisms to handle simultaneous access to employee data. Use file locking or other techniques to prevent data corruption in case of concurrent modifications.

6. **Advanced Programming Challenges**:

   1. **Custom Serialization**: Implement custom serialization and deserialization for the **Employee** class, allowing for more efficient storage and retrieval of employee data.
   2. **Algorithmic Challenges**: Include algorithmic challenges such as sorting employees based on salary using a custom sorting algorithm (e.g., merge sort, quicksort) rather than built-in sorting methods.
   3. **Design Patterns**: Implement relevant design patterns (e.g., Singleton, Factory, Strategy) to improve the structure and maintainability of the codebase.

7. **HTTP Server**:

   - Implement an HTTP server to expose the functionality of the employee management system via a Representational State Transfer (REST) API.
   - Provide endpoints for performing CRUD operations on employee records. Use standard HTTP methods (GET, POST, PUT, DELETE) and appropriate status codes for responses.

8. **User Interface (React.js)**:

   - Develop a user interface using React.js to interact with the HTTP server's REST API.
   - Include components for adding, viewing, updating, and deleting employees.
   - Ensure that the user interface is intuitive, responsive, and aesthetically pleasing.
   - Ensure that the user interface provides real-time updates and feedback to the user when performing CRUD operations.

9. **Usage Documentation**:

   - Provide comprehensive usage documentation for interacting with the HTTP server. Include instructions for accessing endpoints, executing requests, and handling responses.

### Evaluation Criteria:

1.  **Functionality**: Does the system meet all the specified requirements, including advanced programming challenges and the HTTP server with a REST API?
2.  **Code Quality**: Is the code well-structured, modular, and adheres to best practices and design patterns?
3.  **Error Handling**: Are errors and exceptions handled effectively, with appropriate logging and recovery mechanisms?
4.  **Algorithmic Complexity**: Are complex algorithms implemented efficiently, with consideration for performance and scalability?
5.  **Documentation**: Is the usage documentation comprehensive and well-organized, covering all aspects of interacting with the HTTP server?
