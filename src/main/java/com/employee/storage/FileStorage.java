package com.employee.storage;

import com.employee.model.Employee;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.nio.file.*;

public class FileStorage {
  // DIRECTORY_NAME shouldn't be final or private because it's modified in the test
  public static String DIRECTORY_NAME = "employees";
  private static final String FILE_EXTENSION = ".json";
  private static final ObjectMapper mapper = new ObjectMapper();

  // Method to save employee data to a file
  public static void saveEmployee(Employee employee, boolean isNew) throws IOException {
    Path path = getFilePath(employee.getId());
    // Check if the employee already exists
    if (isNew && Files.exists(path)) {
      throw new IllegalArgumentException("DuplicationError: An employee with this ID already exists");
    } else if (!isNew && !Files.exists(path)) {
      throw new IllegalArgumentException("NotFoundError: No employee with this ID exists");
    }
    // Write the employee data to the file
    Files.writeString(path, mapper.writeValueAsString(employee));
  }

  // Method to read employee data from a file
  public static Employee readEmployee(String id) throws IOException {
    Path path = getFilePath(id);
    // Check if the file exists
    if (!Files.exists(path)) {
      throw new IllegalArgumentException("NotFoundError: No employee with this ID exists");
    }
    // Read the employee's data from the file
    return mapper.readValue(Files.readString(path), Employee.class);
  }

  // Method to Load all the employees from the directory
  public static Employee[] readAllEmployees() throws IOException {
    Path directory = Paths.get(DIRECTORY_NAME);
    // Get the list of files in the directory
    String[] files = directory.toFile().list();
    // Make sure the directory is not empty
    if (files == null || files.length == 0) {
      return new Employee[0];
    }
    // Read each employee from their file
    Employee[] employees = new Employee[files.length];
    for (int i = 0; i < files.length; i++) {
      String id = files[i].replace(FILE_EXTENSION, "");
      employees[i] = readEmployee(id);
    }
    return employees;
  }

  // Method to delete an employee file
  public static void deleteEmployee(String id) throws IOException {
    Path path = getFilePath(id);
    // Check if the file exists
    if (!Files.exists(path)) {
      throw new IllegalArgumentException("NotFoundError: No employee with this ID exists");
    }
    // Delete the file corresponding to the employee ID
    Files.delete(path);
  }

  // Method to get the file path and create the directory if it doesn't exist
  private static Path getFilePath(String id) throws IOException {
    Path directory = Paths.get(DIRECTORY_NAME);
    // Create the directory if it doesn't exist
    if (!Files.exists(directory)) {
      Files.createDirectory(directory);
    }
    return Paths.get(DIRECTORY_NAME, id + FILE_EXTENSION);
  }
}
