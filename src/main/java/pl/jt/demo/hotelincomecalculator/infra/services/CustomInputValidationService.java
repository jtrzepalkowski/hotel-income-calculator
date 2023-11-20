package pl.jt.demo.hotelincomecalculator.infra.services;

import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Service;
import pl.jt.demo.hotelincomecalculator.infra.exceptions.RoomCountNotPositiveException;
import pl.jt.demo.hotelincomecalculator.infra.exceptions.WillingnessToPayNotPositiveException;

@Service
public class CustomInputValidationService {

  public void validateInput(int premiumRooms, int economyRooms, List<Double> willingnessToPayData) {
    if (premiumRooms < 0) throw new RoomCountNotPositiveException(premiumRooms);
    if (economyRooms < 0) throw new RoomCountNotPositiveException(economyRooms);

    willingnessToPayData.forEach(elem -> {
      if (Objects.isNull(elem) || elem <= 0) throw new WillingnessToPayNotPositiveException(elem);
    });
  }
}
