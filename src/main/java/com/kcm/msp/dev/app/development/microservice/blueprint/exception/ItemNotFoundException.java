package com.kcm.msp.dev.app.development.microservice.blueprint.exception;

// @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No item found exception")
public class ItemNotFoundException extends RuntimeException {
  public ItemNotFoundException(final String errorMessage) {
    super(errorMessage);
  }
}
