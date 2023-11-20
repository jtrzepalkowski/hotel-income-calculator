package pl.jt.demo.hotelincomecalculator.infra.services;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.jt.demo.hotelincomecalculator.domain.RoomOccupationAndIncome;

@Service
public class IncomeCalculationService {

  private final CustomInputValidationService validationService;
  private final int maxUpgradeQualifications;

  public IncomeCalculationService(final CustomInputValidationService validationService,
                                  @Value("${calculation.max-upgrade-qualifications}") int maxUpgradeQualifications) {
    this.validationService = validationService;
    this.maxUpgradeQualifications = maxUpgradeQualifications;
  }

  public RoomOccupationAndIncome calculateRoomOccupationAndIncome(int premiumRooms, int economyRooms, List<Double> willingnessToPayData) {

    validationService.validateInput(premiumRooms, economyRooms, willingnessToPayData);

    List<BigDecimal> willingnessToPayConverted = willingnessToPayData.stream().map(BigDecimal::new).toList();

    Map<Boolean, List<BigDecimal>> customerPartition = willingnessToPayConverted.stream()
        .collect(Collectors.partitioningBy(elem -> elem.doubleValue() >= 100.0));

    List<BigDecimal> premiumCustomers = customerPartition.get(true);
    List<BigDecimal> economyCustomers = customerPartition.get(false);

    premiumCustomers.sort(Comparator.reverseOrder());
    economyCustomers.sort(Comparator.reverseOrder());

    RoomOccupationAndIncome roomOccupationAndIncome = new RoomOccupationAndIncome();

    roomOccupationAndIncome.calculatePremiumOccupationAndProfit(premiumRooms, premiumCustomers);
    roomOccupationAndIncome.calculateEconomyOccupationAndProfitIncludingPossibleUpgrades(premiumRooms, economyRooms,
        economyCustomers, maxUpgradeQualifications);

    return roomOccupationAndIncome;

  }
}
