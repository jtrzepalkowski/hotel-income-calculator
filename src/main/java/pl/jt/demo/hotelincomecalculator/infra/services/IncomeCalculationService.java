package pl.jt.demo.hotelincomecalculator.infra.services;

import java.util.Comparator;
import java.util.List;
import org.springframework.stereotype.Service;
import pl.jt.demo.hotelincomecalculator.domain.RoomOccupationAndIncome;

@Service
public class IncomeCalculationService {

  private final CustomInputValidationService validationService;

  public IncomeCalculationService(CustomInputValidationService validationService) {
    this.validationService = validationService;
  }

  public RoomOccupationAndIncome calculateRoomOccupationAndIncome(int premiumRooms, int economyRooms, List<Integer> willingnessToPayData) {

    validationService.validateInput(premiumRooms, economyRooms, willingnessToPayData);

    List<Integer> meantForPremium = willingnessToPayData.stream()
        .filter(elem -> elem >= 100)
        .sorted(Comparator.reverseOrder())
        .toList();
    List<Integer> meantForEconomy = willingnessToPayData.stream()
        .filter(elem -> !meantForPremium.contains(elem))
        .sorted(Comparator.reverseOrder())
        .toList();

    int usedPremium = 0, usedEconomy = 0, profitPremium = 0, profitEconomy = 0;

    for (Integer value : meantForPremium) {
      if (usedPremium == premiumRooms) {
        break;
      }
      profitPremium += value;
      usedPremium += 1;
    }


    if (meantForEconomy.size() > economyRooms && usedPremium < premiumRooms) {
      //commented out for-loop due to assumption that only one highest paying from economy gets the free upgrade
      //for (int i = meantForEconomy.size(); i > economyRooms; i = meantForEconomy.size()) {
        profitPremium += meantForEconomy.get(0);
        usedPremium += 1;
        meantForEconomy = meantForEconomy.subList(1, meantForEconomy.size());
      //}
    }

    for (Integer integer : meantForEconomy) {
      if (usedEconomy == economyRooms) {
        break;
      }
      profitEconomy += integer;
      usedEconomy += 1;
    }

    return new RoomOccupationAndIncome(usedPremium, usedEconomy, profitPremium, profitEconomy);

  }
}
