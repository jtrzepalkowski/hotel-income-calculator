package pl.jt.demo.hotelincomecalculator.infra.exceptions;

public class NumberNotPositiveException extends RuntimeException{

  private static final String MESSAGE_TEMPLATE = "Error: %d is not usable. Only data with positive integers can be used.";

  public NumberNotPositiveException(Integer i) {
    super(MESSAGE_TEMPLATE.formatted(i));
  }
}
