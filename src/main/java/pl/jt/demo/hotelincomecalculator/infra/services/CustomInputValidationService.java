package pl.jt.demo.hotelincomecalculator.infra.services;

import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.jt.demo.hotelincomecalculator.infra.exceptions.RoomCountNotPositiveException;
import pl.jt.demo.hotelincomecalculator.infra.exceptions.WillingnessToPayNotPositiveException;

@Service
@Slf4j
public class CustomInputValidationService {

  public void validateInput(int premiumRooms, int economyRooms, List<Integer> willingnessToPayData) {
    log.debug("commencing input validation");
    if (premiumRooms < 0) throw new RoomCountNotPositiveException(premiumRooms);
    if (economyRooms < 0) throw new RoomCountNotPositiveException(economyRooms);

    willingnessToPayData.forEach(elem -> {
      if (Objects.isNull(elem) || elem <= 0) throw new WillingnessToPayNotPositiveException(elem);
    });
  }
}
