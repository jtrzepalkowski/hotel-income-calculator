package pl.jt.demo.hotelincomecalculator.infra.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.jt.demo.hotelincomecalculator.infra.responses.GenericErrorResponse;

@RestControllerAdvice
@Slf4j
public class IncomeCalculatorControllerAdvice {

  @ExceptionHandler({RoomCountNotPositiveException.class, WillingnessToPayNotPositiveException.class})
  public ResponseEntity<GenericErrorResponse> handleRequestMalformationExceptions(Exception e) {
    log.error("An error occurred", e);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new GenericErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<GenericErrorResponse> handleOtherExceptions(Exception e) {
    log.error("An error occurred", e);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new GenericErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
        e.getMessage()));
  }
}
//TODO Dockerize, add integrationTests