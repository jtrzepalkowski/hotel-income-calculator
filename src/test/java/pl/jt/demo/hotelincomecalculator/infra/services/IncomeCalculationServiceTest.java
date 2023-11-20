package pl.jt.demo.hotelincomecalculator.infra.services;

import static org.assertj.core.api.Assertions.assertThat;

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
                                              int premiumOccupied, int premiumProfit, int economyOccupied, int economyProfit){

    RoomOccupationAndIncome result =
        calculationService.calculateRoomOccupationAndIncome(premiumRooms, economyRooms, willingnessToPay);

    assertThat(result.occupiedPremium()).isEqualTo(premiumOccupied);
    assertThat(result.premiumProfit()).isEqualTo(premiumProfit);
    assertThat(result.occupiedEconomy()).isEqualTo(economyOccupied);
    assertThat(result.economyProfit()).isEqualTo(economyProfit);

  }

  private static Stream<Arguments> providePositiveTestData() {
    return Stream.of(
        Arguments.of(3, 3,
                               3, 738, 3, 167),
        Arguments.of(7, 5,
                               6, 1054, 4, 189),
        Arguments.of(2, 7,
                               2, 583, 4, 189),
        Arguments.of(10, 1,
                               7, 1153, 1, 45),
        Arguments.of(0, 2,
                               0, 0, 2, 144),
        Arguments.of(3, 10,
                               3, 738, 4, 189),
        Arguments.of(1, 0,
                               1, 374, 0, 0),
        Arguments.of(8, 3,
                               7, 1153, 3, 90),
        Arguments.of(0, 0,
                               0, 0, 0, 0)
    );
  }
}