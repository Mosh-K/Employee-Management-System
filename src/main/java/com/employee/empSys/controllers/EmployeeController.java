package com.employee.empSys.controllers;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employee.exceptions.CustomExceptions.DuplicationException;
import com.employee.exceptions.CustomExceptions.NotFoundException;
import com.employee.exceptions.CustomExceptions.ValidationException;
import com.employee.model.Employee;
import com.employee.storage.FileStorage;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

  @PostMapping
  public ResponseEntity<String> createEmployee(@RequestBody Employee employee) throws IOException {
    FileStorage.saveEmployee(employee, true);
    return new ResponseEntity<>("Employee created successfully", HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<Employee[]> getAllEmployees() throws IOException {
    Employee[] employees = FileStorage.readAllEmployees();
    return new ResponseEntity<>(employees, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Employee> getEmployee(@PathVariable String id) throws IOException {
    Employee employee = FileStorage.readEmployee(id);
    return new ResponseEntity<>(employee, HttpStatus.OK);
  }

  @PutMapping("/{id}")
  public ResponseEntity<String> updateEmployee(@PathVariable String id, @RequestBody Employee employee)
      throws IOException {
    if (!id.equals(employee.getId())) {
      return new ResponseEntity<>("Employee ID in the path and in the request body do not match",
          HttpStatus.BAD_REQUEST);
    }
    FileStorage.saveEmployee(employee, false);
    return new ResponseEntity<>("Employee updated successfully", HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteEmployee(@PathVariable String id) throws IOException {
    FileStorage.deleteEmployee(id);
    return new ResponseEntity<>("Employee deleted successfully", HttpStatus.OK);
  }

  @ExceptionHandler({ NotFoundException.class, ValidationException.class, DuplicationException.class })
  public ResponseEntity<String> handleException(RuntimeException ex) {
    HttpStatus status = HttpStatus.BAD_REQUEST;
    if (ex instanceof NotFoundException) {
      status = HttpStatus.NOT_FOUND;
    }
    return new ResponseEntity<>(ex.getMessage(), status);
  }
}
