package com.employee.model;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Employee {

  private String id;
  private String name;
  private String position;
  private double salary;

  // Constructor
  @JsonCreator
  public Employee(@JsonProperty("id") String id, @JsonProperty("name") String name, @JsonProperty("position") String position, @JsonProperty("salary") double salary) {
    this.id = validateString(id, 5, "ID");
    this.name = validateString(name, 25, "Name");
    this.position = validateString(position, 10, "Position");
    this.salary = validateNumber(salary, 1_000_000, "Salary");
  }

  private String validateString(String input, int maxLength, String fieldName) {
    if (input == null || input.isEmpty() || input.length() > maxLength) {
      throw new IllegalArgumentException("ValidationError:" + fieldName + " cannot be null, empty, or longer than " + maxLength + " characters");
    }
    return input;
  }

  private double validateNumber(double input, int maxNumber, String fieldName) {
    if (input < 0 || input >= maxNumber) {
      throw new IllegalArgumentException("ValidationError:" + fieldName + " must be between 0 and " + maxNumber);
    }
    return input;
  }
}
