package com.kcm.msp.dev.app.development.prototype.microservice.exception;

import com.kcm.msp.dev.app.development.prototype.microservice.models.Error;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(value = {ItemNotFoundException.class})
  public final ResponseEntity<Error> handleItemNotFoundException(final ItemNotFoundException ex) {
    final Error error =
        new Error().code(HttpStatus.NOT_FOUND.getReasonPhrase()).message(ex.getLocalizedMessage());
    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
  }

  /**
   * handles spring validation exception due to constraint violation eg: This method can handle
   * invalid email in request
   *
   * @param ex Exception thrown by constraint violation
   * @return ResponseEntity
   */
  @ExceptionHandler(value = ConstraintViolationException.class)
  public final ResponseEntity<Error> handleConstraintViolationException(
      final ConstraintViolationException ex) {
    final String errorMessage =
        CollectionUtils.emptyIfNull(ex.getConstraintViolations()).stream()
            .map(
                value ->
                    value.getPropertyPath()
                        + "["
                        + value.getInvalidValue()
                        + "] "
                        + value.getMessage())
            .collect(Collectors.joining());
    final Error error =
        new Error().code(HttpStatus.BAD_REQUEST.getReasonPhrase()).message(errorMessage);
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  /**
   * handles spring validation exception due to type mismatch eg: This method can handle invalid
   * date in request
   *
   * @param ex This exception is thrown when method argument is not the expected type
   * @return ResponseEntity
   */
  @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
  public final ResponseEntity<Error> handleMethodArgumentTypeMismatchException(
      final MethodArgumentTypeMismatchException ex) {
    final String propertyName = ex.getPropertyName() != null ? ex.getPropertyName() : ex.getName();
    final String errorMessage = "Mismatch value [%s] for %s".formatted(ex.getValue(), propertyName);
    final Error error =
        new Error().code(HttpStatus.BAD_REQUEST.getReasonPhrase()).message(errorMessage);
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = {Exception.class})
  public final ResponseEntity<Error> handleGenericException(
      final Exception ex, final WebRequest request) {
    LOG.error("Error in the execution api {}", request.getContextPath(), ex);
    final Error error =
        new Error()
            .code(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
            .message(ex.getLocalizedMessage());
    return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
