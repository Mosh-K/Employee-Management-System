package com.employee.empSys.controllers;

import com.employee.model.Employee;
import com.employee.storage.FileStorage;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @BeforeAll
  static void setUpAll() {
    FileStorage.DIRECTORY_NAME = "test_employees";
  }

  @BeforeEach
  void setUp() throws IOException {
    // Save a few employees to the test directory
    Employee employee1 = new Employee("1", "John Doe", "Engineer", 50000);
    Employee employee2 = new Employee("2", "Jane Doe", "Manager", 60000);

    FileStorage.saveEmployee(employee1, true);
    FileStorage.saveEmployee(employee2, true);
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
  public void testCreateEmployee() throws Exception {
    String employee = "{\"id\":\"3\",\"name\":\"Alice Smith\",\"position\":\"Analyst\",\"salary\":40000}";
    mockMvc.perform(MockMvcRequestBuilders.post("/employees").contentType(MediaType.APPLICATION_JSON).content(employee)) //
            .andExpect(status().isCreated()).andExpect(content().string("Employee created successfully"));
  }

  @Test     // Test trying to save an employee with an empty name
  public void testCreateEmployeeEmptyName() throws Exception {
    String emptyNameEmployee = "{\"id\":\"4\",\"name\":\"\",\"position\":\"Manager\",\"salary\":70000}";
    mockMvc.perform(MockMvcRequestBuilders.post("/employees").contentType(MediaType.APPLICATION_JSON).content(emptyNameEmployee)) //
            .andExpect(status().isBadRequest());
  }

  @Test     // Test trying to save an existing employee
  public void testCreateEmployeeExisting() throws Exception {
    String employee1Updated = "{\"id\":\"1\",\"name\":\"John\",\"position\":\"CEO\",\"salary\":60000}";
    mockMvc.perform(MockMvcRequestBuilders.post("/employees").contentType(MediaType.APPLICATION_JSON).content(employee1Updated)) //
            .andExpect(status().isBadRequest());
  }

  @Test
  public void testGetEmployee() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/employees/1")).andExpect(status().isOk());
  }

  @Test
  public void testUpdateEmployee() throws Exception {
    String employee1Updated = "{\"id\":\"1\",\"name\":\"John\",\"position\":\"CEO\",\"salary\":60000}";
    mockMvc.perform(MockMvcRequestBuilders.put("/employees/1").contentType(MediaType.APPLICATION_JSON).content(employee1Updated)) //
            .andExpect(status().isOk()).andExpect(content().string("Employee updated successfully"));
  }

  @Test
  public void testDeleteEmployee() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.delete("/employees/1")) //
            .andExpect(status().isOk()).andExpect(content().string("Employee deleted successfully"));
  }
}
