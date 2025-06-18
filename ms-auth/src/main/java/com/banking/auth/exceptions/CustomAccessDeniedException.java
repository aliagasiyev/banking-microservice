package com.banking.auth.exceptions;

public class CustomAccessDeniedException extends RuntimeException {
  public CustomAccessDeniedException(String message) {
    super(message);
  }
}
