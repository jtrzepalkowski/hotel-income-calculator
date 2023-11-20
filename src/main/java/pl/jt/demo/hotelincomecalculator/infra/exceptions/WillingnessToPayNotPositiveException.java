package pl.jt.demo.hotelincomecalculator.infra.exceptions;

import java.util.Objects;

public class WillingnessToPayNotPositiveException extends RuntimeException{

  private static final String MESSAGE_TEMPLATE = "Error: %d is not usable. Willingness to pay with positive integers only can be used.";

  public WillingnessToPayNotPositiveException(Double i) {
    super(MESSAGE_TEMPLATE.formatted(Objects.nonNull(i) ? i.intValue() : null));
  }
}
