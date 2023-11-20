package pl.jt.demo.hotelincomecalculator.infra.services;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.jt.demo.hotelincomecalculator.domain.RoomOccupationAndIncome;

@Service
@Slf4j
public class IncomeCalculationService {

  private final CustomInputValidationService validationService;
  private final int maxUpgradeQualifications;

  public IncomeCalculationService(final CustomInputValidationService validationService,
                                  @Value("${calculation.max-upgrade-qualifications}") int maxUpgradeQualifications) {
    this.validationService = validationService;
    this.maxUpgradeQualifications = maxUpgradeQualifications;
  }

  public RoomOccupationAndIncome calculateRoomOccupationAndIncome(int premiumRooms, int economyRooms, List<Integer> willingnessToPayData) {

    validationService.validateInput(premiumRooms, economyRooms, willingnessToPayData);

    log.debug("commencing calculation");
    Map<Boolean, List<Integer>> customerPartition = willingnessToPayData.stream()
        .collect(Collectors.partitioningBy(elem -> elem >= 100));

    List<Integer> premiumCustomers = customerPartition.get(true);
    List<Integer> economyCustomers = customerPartition.get(false);

    premiumCustomers.sort(Comparator.reverseOrder());
    economyCustomers.sort(Comparator.reverseOrder());

    int usedPremium = 0, usedEconomy = 0, profitPremium = 0, profitEconomy = 0;

    for (Integer value : premiumCustomers) {
      if (usedPremium == premiumRooms) {
        break;
      }
      profitPremium += value;
      usedPremium += 1;
    }

    if (economyCustomers.size() > economyRooms && usedPremium < premiumRooms) {
      int numberOfIterations = 0;
      for (int i = economyCustomers.size(); i > economyRooms && numberOfIterations < maxUpgradeQualifications; i = economyCustomers.size()) {
        profitPremium += economyCustomers.get(0);
        usedPremium += 1;
        economyCustomers = economyCustomers.subList(1, economyCustomers.size());
        numberOfIterations++;
      }
    }

    for (Integer integer : economyCustomers) {
      if (usedEconomy == economyRooms) {
        break;
      }
      profitEconomy += integer;
      usedEconomy += 1;
    }

    log.debug("finished calculation");

    return new RoomOccupationAndIncome(usedPremium, usedEconomy, profitPremium, profitEconomy);

  }
}
