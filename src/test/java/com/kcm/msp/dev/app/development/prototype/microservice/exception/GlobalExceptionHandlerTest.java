package com.kcm.msp.dev.app.development.prototype.microservice.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.kcm.msp.dev.app.development.prototype.microservice.models.Error;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Tag("UnitTest")
final class GlobalExceptionHandlerTest {
  GlobalExceptionHandler classUnderTest;

  @BeforeEach
  public void setUp() {
    classUnderTest = new GlobalExceptionHandler();
  }

  @Test
  public void handleGenericException() {
    final Exception exception = new RuntimeException("Error in application");
    final ResponseEntity<Error> errorResponseEntity =
        classUnderTest.handleGenericException(exception, mock(WebRequest.class));
    assertNotNull(errorResponseEntity);
    assertNotNull(errorResponseEntity.getBody());
    assertEquals(INTERNAL_SERVER_ERROR.getReasonPhrase(), errorResponseEntity.getBody().getCode());
    assertEquals("Error in application", errorResponseEntity.getBody().getMessage());
  }

  @Test
  public void handleItemNotFoundException() {
    final ItemNotFoundException exception = new ItemNotFoundException("item not found");
    final ResponseEntity<Error> errorResponseEntity =
        classUnderTest.handleItemNotFoundException(exception);
    assertNotNull(errorResponseEntity);
    assertNotNull(errorResponseEntity.getBody());
    assertEquals(NOT_FOUND.getReasonPhrase(), errorResponseEntity.getBody().getCode());
    assertEquals("item not found", errorResponseEntity.getBody().getMessage());
  }

  @Test
  public void handleConstraintViolationException() {
    final ConstraintViolation<?> constraintViolation = mock(ConstraintViolation.class);
    final ConstraintViolationException exception =
        new ConstraintViolationException(Set.of(constraintViolation));
    final ResponseEntity<Error> errorResponseEntity =
        classUnderTest.handleConstraintViolationException(exception);
    assertNotNull(errorResponseEntity);
    assertNotNull(errorResponseEntity.getBody());
    assertEquals(BAD_REQUEST.getReasonPhrase(), errorResponseEntity.getBody().getCode());
    assertNotNull(errorResponseEntity.getBody().getMessage());
  }

  @Test
  public void handleMethodArgumentTypeMismatchException() {
    final MethodArgumentTypeMismatchException exception =
        new MethodArgumentTypeMismatchException("value", null, "name", null, null);
    final ResponseEntity<Error> errorResponseEntity =
        classUnderTest.handleMethodArgumentTypeMismatchException(exception);
    assertNotNull(errorResponseEntity);
    assertNotNull(errorResponseEntity.getBody());
    assertEquals(BAD_REQUEST.getReasonPhrase(), errorResponseEntity.getBody().getCode());
    assertNotNull(errorResponseEntity.getBody().getMessage());
  }
}
