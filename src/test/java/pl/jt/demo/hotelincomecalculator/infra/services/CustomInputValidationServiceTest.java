package pl.jt.demo.hotelincomecalculator.infra.services;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import pl.jt.demo.hotelincomecalculator.infra.exceptions.RoomCountNotPositiveException;
import pl.jt.demo.hotelincomecalculator.infra.exceptions.WillingnessToPayNotPositiveException;

public class CustomInputValidationServiceTest {

  private final CustomInputValidationService validationService = new CustomInputValidationService();

  @Test
  void validateInput_shouldThrowRoomCountNotPositiveException_whenPremiumRoomsIsNegative() {
    assertThrows(RoomCountNotPositiveException.class,
        () -> validationService.validateInput(-1, 2, List.of(3.0, 4.0, 5.0)));
  }

  @Test
  void validateInput_shouldThrowRoomCountNotPositiveException_whenEconomyRoomsIsNegative() {
    assertThrows(RoomCountNotPositiveException.class,
        () -> validationService.validateInput(3, -2, List.of(3.0, 4.0, 5.0)));
  }

  @Test
  void validateInput_shouldThrowWillingnessToPayNotPositiveException_whenWillingnessToPayContainsNegativeValue() {
    assertThrows(WillingnessToPayNotPositiveException.class, () ->
        validationService.validateInput(3, 2, List.of(3.0, 4.0, -5.0)));
  }

  @Test
  void validateInput_shouldThrowWillingnessToPayNotPositiveException_whenWillingnessToPayContainsZero() {
    assertThrows(WillingnessToPayNotPositiveException.class, () ->
        validationService.validateInput(3, 2, List.of(3.0, 4.0, 0.0)));
  }

  @Test
  void validateInput_shouldThrowWillingnessToPayNotPositiveException_whenWillingnessToPayContainsNull() {
    assertThrows(WillingnessToPayNotPositiveException.class, () ->
        validationService.validateInput(3, 2, Arrays.asList(3.0, 4.0, null)));
  }

  @Test
  void validateInput_shouldNotThrowException_whenInputIsValid() {
    validationService.validateInput(3, 2, List.of(3.0, 4.0, 5.0));
  }

}
