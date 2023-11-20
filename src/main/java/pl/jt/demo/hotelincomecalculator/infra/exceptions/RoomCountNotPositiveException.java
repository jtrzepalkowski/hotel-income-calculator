package pl.jt.demo.hotelincomecalculator.infra.exceptions;

public class RoomCountNotPositiveException extends RuntimeException{

  private static final String MESSAGE_TEMPLATE = "Error: Number of rooms provided must be a positive integer. Data received: %d";

  public RoomCountNotPositiveException(Integer i) {
    super(MESSAGE_TEMPLATE.formatted(i));
  }
}
