package com.employee.empSys.controllers;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.employee.exceptions.CustomExceptions;
import com.employee.model.Employee;
import com.employee.storage.FileStorage;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This class tests the {@code EmployeeController} class.
 */
@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerTest {

  /**
   * The base URL for all employee requests.
   */
  private static final String BASE_URL = "/employees";

  /**
   * Represents an employee with a dynamically generated ID for testing purposes.
   * The ID is not hardcoded to allow its use in dynamic contexts, such as in
   * URLs.
   */
  private static final String ID_N = "265";

  /**
   * Represents an employee with a dynamically generated ID for testing purposes.
   */
  private static final String EMPLOYEE_N_STRINGIFIED = "{\"id\":\"" + ID_N
      + "\",\"name\":\"Alice Smith\",\"position\":\"Analyst\",\"salary\":40000}";

  /**
   * Provides invalid employee data for parameterized tests.
   * This method must be static to be used as a source in {@code @MethodSource}.
   */
  static Stream<String> invalidEmployeeProvider() {
    return Stream
        .of(EmployeeArrays.invalidIdEmployees, EmployeeArrays.invalidNameEmployees,
            EmployeeArrays.invalidPositionEmployees, EmployeeArrays.invalidSalaryEmployees)
        .flatMap(Stream::of);
  }

  /**
   * The {@code MockMvc} instance is used to perform HTTP requests to the server.
   */
  @Autowired
  private MockMvc mockMvc;

  /**
   * The {@code ObjectMapper} instance is used to convert objects to JSON strings
   * and vice versa.
   */
  @Autowired
  private ObjectMapper OBJECT_MAPPER;

  /**
   * Set up the test directory before all tests.
   */
  @BeforeAll
  static void setUpAll() {
    FileStorage.DIRECTORY_NAME = "test_employees";
  }

  /**
   * Save valid employees to the test directory before each test.
   * @throws IOException
   */
  @BeforeEach
  void setUp() throws IOException {
    for (Employee employee : EmployeeArrays.validEmployees) {
      FileStorage.saveEmployee(employee, true);
    }
  }

  /**
   * Delete all employees from the test directory after each test.
   * @throws IOException
   */
  @AfterEach
  void tearDown() throws IOException {
    Employee[] employees = FileStorage.readAllEmployees();
    for (Employee employee : employees) {
      FileStorage.deleteEmployee(employee.getId());
    }
  }

  /**
   * This class contains tests for creating employees.
   */
  @Nested
  class CreateEmployeeTests {

    /**
     * Does not work without declaring the method in the nested class.
     */
    static Stream<String> invalidEmployeeProvider() {
      return EmployeeControllerTest.invalidEmployeeProvider();
    }

    /**
     * Test saving a new employee.
     * @throws Exception
     */
    @Test
    void givenNewEmployee_whenCreate_thenStatusIsCreated() throws Exception {
      performRequest("POST", BASE_URL, EMPLOYEE_N_STRINGIFIED).andExpect(status().isCreated());
    }

    /**
     * Test saving an employee with an invalid field.
     * @param invalidEmployee the invalid employee to save
     * @throws Exception
     */
    @ParameterizedTest
    @MethodSource("invalidEmployeeProvider")
    void givenInvalidEmployee_whenCreate_thenStatusIsBadRequest(String invalidEmployee) throws Exception {
      performRequest("POST", BASE_URL, invalidEmployee).andExpect(status().isBadRequest())
          .andExpect(content().string(containsString(CustomExceptions.VALIDATION_MESSAGE)));
    }

    /**
     * Test saving an employee with an existing ID.
     * @throws Exception
     */
    @Test
    void givenExistingIdEmployee_whenCreate_thenStatusIsBadRequest() throws Exception {
      performRequest("POST", BASE_URL, EmployeeArrays.validEmployees[0].toString()).andExpect(status().isBadRequest())
          .andExpect(content().string(containsString(CustomExceptions.DUPLICATION_MESSAGE)));
    }
  }

  @Nested
  class GetEmployeeTests {

    // Test getting all employees
    @Test
    void whenGetAllEmployees_thenStatusIsOk() throws Exception {
      MvcResult mvcResult = performRequest("GET", BASE_URL, null).andExpect(status().isOk()).andReturn();
      String content = mvcResult.getResponse().getContentAsString();
      for (Employee employee : EmployeeArrays.validEmployees) {
        String employeeString = OBJECT_MAPPER.writeValueAsString(employee);
        assert content.contains(employeeString);
      }
    }

    // Test getting one employee
    @Test
    void givenEmployeeId_whenGetEmployee_thenStatusIsOk() throws Exception {
      MvcResult mvcResult = performRequest("GET", BASE_URL + "/1", null).andExpect(status().isOk()).andReturn();
      String content = mvcResult.getResponse().getContentAsString();
      String employeeString = EmployeeArrays.validEmployees[0].toString();
      assert content.contains(employeeString);
    }

    // Test trying to get a non-existing employee
    @Test
    void givenNonExistingEmployeeId_whenGetEmployee_thenStatusIsNotFound() throws Exception {
      performRequest("GET", BASE_URL + "/" + ID_N, null).andExpect(status().isNotFound())
          .andExpect(content().string(containsString(CustomExceptions.NOT_FOUND_MESSAGE)));
    }
  }

  @Nested
  class UpdateEmployeeTests {

    /**
     * Does not work without declaring the method in the nested class.
     */
    static Stream<String> invalidEmployeeProvider() {
      return EmployeeControllerTest.invalidEmployeeProvider();
    }

    // Test updating an employee
    @Test
    void givenEmployee_whenUpdateEmployee_thenStatusIsOk() throws Exception {
      performRequest("PUT", BASE_URL + "/1", EmployeeArrays.validEmployees[0].toString()).andExpect(status().isOk());
    }

    // Test trying to update a non-existing employee
    @Test
    void givenNonExistingEmployee_whenUpdateEmployee_thenStatusIsNotFound() throws Exception {
      performRequest("PUT", BASE_URL + "/" + ID_N, EMPLOYEE_N_STRINGIFIED).andExpect(status().isNotFound())
          .andExpect(content().string(containsString(CustomExceptions.NOT_FOUND_MESSAGE)));
    }

    // Test trying to update an employee with an invalid field
    @ParameterizedTest
    @MethodSource("invalidEmployeeProvider")
    void givenInvalidEmployee_whenUpdateEmployee_thenStatusIsBadRequest(String invalidEmployee) throws Exception {
      performRequest("PUT", BASE_URL + "/1", invalidEmployee).andExpect(status().isBadRequest())
          .andExpect(content().string(containsString(CustomExceptions.VALIDATION_MESSAGE)));
    }

    // Test trying to update an employee with a different ID
    @Test
    void givenDifferentIdEmployee_whenUpdateEmployee_thenStatusIsBadRequest() throws Exception {
      performRequest("PUT", BASE_URL + "/1", EMPLOYEE_N_STRINGIFIED).andExpect(status().isBadRequest());
    }
  }

  @Nested
  class DeleteEmployeeTests {

    // Test deleting an employee
    @Test
    void givenExistingId_whenDeleteEmployee_thenStatusIsOk() throws Exception {
      performRequest("DELETE", BASE_URL + "/1", null).andExpect(status().isOk());
    }

    // Test trying to delete a non-existing employee
    @Test
    void givenNonExistingId_whenDeleteEmployee_thenStatusIsNotFound() throws Exception {
      performRequest("DELETE", BASE_URL + "/" + ID_N, null).andExpect(status().isNotFound())
          .andExpect(content().string(containsString(CustomExceptions.NOT_FOUND_MESSAGE)));
    }
  }

  /**
   * Perform an HTTP request.
   * @param method the HTTP method
   * @param url the URL
   * @param content the content
   * @return a {@link ResultActions} instance
   * @throws Exception if an error occurs
   */
  private ResultActions performRequest(String method, String url, String content) throws Exception {
    return mockMvc.perform(requestBuilder(method, url, content));
  }

  /**
   * Create a request builder.
   * @param method 
   * @param url: 
   * @param content:
   * @return a {@code MockHttpServletRequestBuilder} instance
   */
  private MockHttpServletRequestBuilder requestBuilder(String method, String url, String content) {
    switch (method.toUpperCase()) {
      case "POST":
        return MockMvcRequestBuilders.post(url).contentType(MediaType.APPLICATION_JSON).content(content);
      case "GET":
        return MockMvcRequestBuilders.get(url);
      case "PUT":
        return MockMvcRequestBuilders.put(url).contentType(MediaType.APPLICATION_JSON).content(content);
      case "DELETE":
        return MockMvcRequestBuilders.delete(url);
      default:
        throw new IllegalArgumentException("Invalid HTTP method: " + method);
    }
  }
}
