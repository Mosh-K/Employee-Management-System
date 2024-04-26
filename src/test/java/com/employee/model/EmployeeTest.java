package com.employee.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
    assertThrows(IllegalArgumentException.class, () -> new Employee(null, "John Doe", "Manager", 100000.0));
    assertThrows(IllegalArgumentException.class, () -> new Employee("", "John Doe", "Manager", 100000.0));
    assertThrows(IllegalArgumentException.class, () -> new Employee("12345678901", "John Doe", "Manager", 100000.0));
  }

  @Test
  void testConstructorWithInvalidName() {
    assertThrows(IllegalArgumentException.class, () -> new Employee("1", null, "Manager", 100000.0));
    assertThrows(IllegalArgumentException.class, () -> new Employee("1", "", "Manager", 100000.0));
    assertThrows(IllegalArgumentException.class, () -> new Employee("1", "John Doe John Doe John Doe", "Manager", 100000.0));
  }

  @Test
  void testConstructorWithInvalidPosition() {
    assertThrows(IllegalArgumentException.class, () -> new Employee("1", "John Doe", null, 100000.0));
    assertThrows(IllegalArgumentException.class, () -> new Employee("1", "John Doe", "", 100000.0));
    assertThrows(IllegalArgumentException.class, () -> new Employee("1", "John Doe", "Manager Manager Manager", 100000.0));
  }

  @Test
  void testConstructorWithInvalidSalary() {
    assertThrows(IllegalArgumentException.class, () -> new Employee("1", "John Doe", "Manager", -100000.0));
    assertThrows(IllegalArgumentException.class, () -> new Employee("1", "John Doe", "Manager", 1000000.0));
  }
}