package com.employee.storage;

import com.employee.exceptions.CustomExceptions.DuplicationException;
import com.employee.exceptions.CustomExceptions.NotFoundException;
import com.employee.model.Employee;
import org.junit.jupiter.api.*;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FileStorageTest {

  Employee employee1 = new Employee("1", "John Doe", "Engineer", 50000);
  Employee employee2 = new Employee("2", "Jane Doe", "Manager", 60000);

  @BeforeAll
  static void setUpAll() {
    FileStorage.DIRECTORY_NAME = "test_employees";
  }

  @AfterEach
  void tearDown() throws IOException {
    // Delete all files in the test directory
    Employee[] employees = FileStorage.readAllEmployees();
    for (Employee employee : employees) {
      FileStorage.deleteEmployee(employee.getId());
    }
  }

  @Test
  void saveAndReadEmployee() throws IOException {
    // Save a new employee with isNew flag set to true
    FileStorage.saveEmployee(employee1, true);
    Employee readEmployee = FileStorage.readEmployee(employee1.getId());
    assertEquals(employee1, readEmployee);

    // Save a new employee with isNew flag set to false
    assertThrows(NotFoundException.class, () -> FileStorage.saveEmployee(employee2, false));

    // Save an existing employee with isNew flag set to true
    assertThrows(DuplicationException.class, () -> FileStorage.saveEmployee(employee1, true));

    // Save an existing employee with isNew flag set to false
    FileStorage.saveEmployee(employee1, false);
    assertEquals(employee1, FileStorage.readEmployee(employee1.getId()));
  }

  @Test
  void saveAndReadAllEmployees() throws IOException {
    FileStorage.saveEmployee(employee1, true);
    FileStorage.saveEmployee(employee2, true);
    Employee[] employees = FileStorage.readAllEmployees();
    assertEquals(2, employees.length);
    assertTrue(employees[0].equals(employee1) || employees[0].equals(employee2));
    assertTrue(employees[1].equals(employee1) || employees[1].equals(employee2));
  }

  @Test
  void deleteEmployee() throws IOException {
    // Delete an existing employee
    FileStorage.saveEmployee(employee1, true);
    FileStorage.deleteEmployee(employee1.getId());
    assertThrows(NotFoundException.class, () -> FileStorage.readEmployee(employee1.getId()));

    // Delete a non-existing employee
    assertThrows(NotFoundException.class, () -> FileStorage.deleteEmployee(employee2.getId()));
  }
}