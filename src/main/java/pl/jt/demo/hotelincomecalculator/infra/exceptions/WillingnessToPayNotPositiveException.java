package pl.jt.demo.hotelincomecalculator.infra.exceptions;

public class WillingnessToPayNotPositiveException extends RuntimeException{

  private static final String MESSAGE_TEMPLATE = "Error: %d is not usable. Willingness to pay with positive integers only can be used.";

  public WillingnessToPayNotPositiveException(Integer i) {
    super(MESSAGE_TEMPLATE.formatted(i));
  }
}
