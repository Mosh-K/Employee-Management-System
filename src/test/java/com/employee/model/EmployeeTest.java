package com.employee.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.employee.exceptions.CustomExceptions.ValidationException;

class EmployeeTest {

  @Test
  void testConstructor() {
    Employee employee = new Employee("1", "John Doe", "Manager", 100000.0);
    assertEquals("1", employee.getId());
    assertEquals("John Doe", employee.getName());
    assertEquals("Manager", employee.getPosition());
    assertEquals(100000.0, employee.getSalary());
  }

  @Test
  void testConstructorWithInvalidId() {
    assertThrows(ValidationException.class, () -> new Employee(null, "John Doe", "Manager", 100000.0));
    assertThrows(ValidationException.class, () -> new Employee("", "John Doe", "Manager", 100000.0));
    assertThrows(ValidationException.class, () -> new Employee("12345678901", "John Doe", "Manager", 100000.0));
  }

  @Test
  void testConstructorWithInvalidName() {
    assertThrows(ValidationException.class, () -> new Employee("1", null, "Manager", 100000.0));
    assertThrows(ValidationException.class, () -> new Employee("1", "", "Manager", 10000.0));
    assertThrows(ValidationException.class, () -> new Employee("1", "John Doe John Doe John Doe", "Manager", 100000.0));
  }

  @Test
  void testConstructorWithInvalidPosition() {
    assertThrows(ValidationException.class, () -> new Employee("1", "John Doe", null, 100000.0));
    assertThrows(ValidationException.class, () -> new Employee("1", "John Doe", "", 100000.0));
    assertThrows(ValidationException.class, () -> new Employee("1", "John Doe", "Manager Manager Manager Manager Manager", 100000.0));
  }

  @Test
  void testConstructorWithInvalidSalary() {
    assertThrows(ValidationException.class, () -> new Employee("1", "John Doe", "Manager", -100000.0));
    assertThrows(ValidationException.class, () -> new Employee("1", "John Doe", "Manager", 1000000.0));
  }
}