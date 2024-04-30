package com.employee.model;

import com.employee.exceptions.CustomExceptions.ValidationException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class Employee {

  private final String id;
  private final String name;
  private final String position;
  private final double salary;

  // Constructor
  @JsonCreator
  public Employee(@JsonProperty("id") String id, @JsonProperty("name") String name,
      @JsonProperty("position") String position, @JsonProperty("salary") double salary) {
    this.id = validateString(id, 5, "ID");
    this.name = validateString(name, 25, "Name");
    this.position = validateString(position, 25, "Position");
    this.salary = validateNumber(salary, 1_000_000, "Salary");
  }

  // ObjectMapper for JSON serialization and deserialization
  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  private static String validateString(String input, int maxLength, String fieldName) {
    if (input == null || input.isEmpty() || input.length() > maxLength) {
      throw new ValidationException(fieldName + " must not be empty and must not exceed " + maxLength + " characters");
    }
    return input;
  }

  private static double validateNumber(double input, int maxNumber, String fieldName) {
    if (input <= 0 || input >= maxNumber) {
      throw new ValidationException(fieldName + " must be greater than 0 and less than " + maxNumber);
    }
    return input;
  }

  @Override
  public String toString() {
    try {
      return OBJECT_MAPPER.writeValueAsString(this);
    } catch (JsonProcessingException e) {
      // Handle the exception here
      e.printStackTrace();
      return "";
    }
  }
}
