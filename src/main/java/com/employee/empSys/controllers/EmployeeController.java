package com.employee.empSys.controllers;

import com.employee.model.Employee;
import com.employee.storage.FileStorage;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

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
  public ResponseEntity<String> updateEmployee(@PathVariable String id, @RequestBody Employee employee) throws IOException {
    if (!id.equals(employee.getId())) {
      return new ResponseEntity<>("Employee ID in the path and in the request body do not match", HttpStatus.BAD_REQUEST);
    }
    FileStorage.saveEmployee(employee, false);
    return new ResponseEntity<>("Employee updated successfully", HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteEmployee(@PathVariable String id) throws IOException {
    FileStorage.deleteEmployee(id);
    return new ResponseEntity<>("Employee deleted successfully", HttpStatus.OK);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
    if (ex.getMessage().contains("DuplicationError") || ex.getMessage().contains("ValidationError")) {
      return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    } else if (ex.getMessage().contains("NotFoundError")) {
      return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    } else {
      throw new RuntimeException(ex);
    }
  }
}
