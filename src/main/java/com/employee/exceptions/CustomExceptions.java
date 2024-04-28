package com.employee.exceptions;

public class CustomExceptions {
  private CustomExceptions() {
    // private constructor to hide the implicit public one
  }

  public static final String VALIDATION_MESSAGE = "ValidationException: ";
  public static final String DUPLICATION_MESSAGE = "DuplicationException: ";
  public static final String NOT_FOUND_MESSAGE = "NotFoundException: ";

  public static class ValidationException extends RuntimeException {
    public ValidationException(String message) {
      super(VALIDATION_MESSAGE + message);
    }
  }

  public static class DuplicationException extends RuntimeException {
    public DuplicationException(String message) {
      super(DUPLICATION_MESSAGE + message);
    }
  }

  public static class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
      super(NOT_FOUND_MESSAGE + message);
    }
  }
}
