package pl.jt.demo.hotelincomecalculator.domain;

import java.math.BigDecimal;
import java.util.List;
import lombok.Getter;

@Getter
public class RoomOccupationAndIncome {

  int occupiedPremium;
  int occupiedEconomy;
  BigDecimal premiumProfit = new BigDecimal(0);
  BigDecimal economyProfit = new BigDecimal(0);

  public void calculatePremiumOccupationAndProfit(int availableRooms, List<BigDecimal> customers) {
    CalculationResult result = performCalculation(availableRooms, occupiedPremium, customers, premiumProfit);
    this.occupiedPremium = result.occupiedRooms;
    this.premiumProfit = result.profit;
  }

  public void calculateEconomyOccupationAndProfitIncludingPossibleUpgrades(int premiumRooms, int economyRooms, List<BigDecimal> economyCustomers,
                                                                            int maxUpgradeQualifications) {
    if (economyCustomers.size() > economyRooms && occupiedPremium < premiumRooms) {
      int enocomyCustomersUpgraded = 0;

      for (int i = economyCustomers.size();
           i > economyRooms && enocomyCustomersUpgraded < maxUpgradeQualifications;
           i = economyCustomers.size()) {
        this.premiumProfit = premiumProfit.add(economyCustomers.get(0));
        this.occupiedPremium += 1;
        economyCustomers = economyCustomers.subList(1, economyCustomers.size());
        enocomyCustomersUpgraded++;
      }
    }

    CalculationResult result = performCalculation(economyRooms, occupiedEconomy, economyCustomers, economyProfit);
    this.occupiedEconomy = result.occupiedRooms;
    this.economyProfit = result.profit;
  }

  private CalculationResult performCalculation(int availableRooms, int occupiedRooms, List<BigDecimal> willingnessToPay, BigDecimal profit) {
    for (BigDecimal payment : willingnessToPay) {
      if (occupiedRooms == availableRooms) {
        break;
      }
      profit = profit.add(payment);
      occupiedRooms += 1;
    }

    return new CalculationResult(occupiedRooms, profit);
  }

  private record CalculationResult(int occupiedRooms, BigDecimal profit) {
  }

}
