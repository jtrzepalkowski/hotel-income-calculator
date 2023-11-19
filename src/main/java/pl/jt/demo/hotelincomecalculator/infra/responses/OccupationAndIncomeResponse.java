package pl.jt.demo.hotelincomecalculator.infra.responses;

import pl.jt.demo.hotelincomecalculator.domain.RoomOccupationAndIncome;

public record OccupationAndIncomeResponse(int occupiedPremiumRooms,
                                          int occupiedEconomyRooms,
                                          int profitFromPremiumRooms,
                                          int profitFromEconomyRooms) {

  public static OccupationAndIncomeResponse from(RoomOccupationAndIncome roomOccupationAndIncome){
    return new OccupationAndIncomeResponse(roomOccupationAndIncome.occupiedPremium(),
        roomOccupationAndIncome.occupiedEconomy(),
        roomOccupationAndIncome.premiumProfit(),
        roomOccupationAndIncome.economyProfit());
  }
}
