package com.bootcamp.currentaccount.exception;

public class CurrentAccountValidationException extends Exception {

  private static final long serialVersionUID = 1L;

  public CurrentAccountValidationException(String message, Throwable cause) {
    super(message, cause);
  }

  public CurrentAccountValidationException(String message) {
    super(message);
  }

}
