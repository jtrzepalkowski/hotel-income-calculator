package pl.jt.demo.hotelincomecalculator.infra.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.jt.demo.hotelincomecalculator.config.BaseTestClass;
import pl.jt.demo.hotelincomecalculator.domain.RoomOccupationAndIncome;

@SpringBootTest
public class IncomeCalculationServiceTest extends BaseTestClass {

  @Autowired
  private IncomeCalculationService calculationService;

  @ParameterizedTest
  @MethodSource("providePositiveTestData")
  void testCalculationOutcomeWithPositiveData(int premiumRooms, int economyRooms,
                                              int premiumOccupied, BigDecimal premiumProfit, int economyOccupied, BigDecimal economyProfit){

    RoomOccupationAndIncome result =
        calculationService.calculateRoomOccupationAndIncome(premiumRooms, economyRooms, willingnessToPay);

    assertThat(result.getOccupiedPremium()).isEqualTo(premiumOccupied);
    assertThat(result.getPremiumProfit()).isEqualTo(premiumProfit);
    assertThat(result.getOccupiedEconomy()).isEqualTo(economyOccupied);
    assertThat(result.getEconomyProfit()).isEqualTo(economyProfit);

  }

  private static Stream<Arguments> providePositiveTestData() {
    return Stream.of(
        Arguments.of(3, 3,
                               3, new BigDecimal(738),    3, new BigDecimal(167)),
        Arguments.of(7, 5,
                               6, new BigDecimal(1054),   4, new BigDecimal(189)),
        Arguments.of(2, 7,
                               2, new BigDecimal(583),    4, new BigDecimal(189)),
        Arguments.of(10, 1,
                               7, new BigDecimal(1153),   1, new BigDecimal(45)),
        Arguments.of(0, 2,
                               0, new BigDecimal(0),      2, new BigDecimal(144)),
        Arguments.of(3, 10,
                               3, new BigDecimal(738),    4, new BigDecimal(189)),
        Arguments.of(1, 0,
                               1, new BigDecimal(374),    0, new BigDecimal(0)),
        Arguments.of(8, 3,
                               7, new BigDecimal(1153),   3, new BigDecimal(90)),
        Arguments.of(0, 0,
                               0, new BigDecimal(0),      0, new BigDecimal(0))
    );
  }
}
