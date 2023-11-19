package pl.jt.demo.hotelincomecalculator.infra.services;

import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Service;
import pl.jt.demo.hotelincomecalculator.infra.exceptions.NumberNotPositiveException;

@Service
public class CustomInputValidationService {

  public void validateInput(int premiumRooms, int economyRooms, List<Integer> willingnessToPayData) {
    if (premiumRooms < 0) throw new NumberNotPositiveException(premiumRooms);
    if (economyRooms < 0) throw new NumberNotPositiveException(economyRooms);

    willingnessToPayData.forEach(elem -> {
      if (Objects.isNull(elem) || elem <= 0) throw new NumberNotPositiveException(elem);
    });
  }
}
