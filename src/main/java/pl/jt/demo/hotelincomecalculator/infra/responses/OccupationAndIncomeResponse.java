package pl.jt.demo.hotelincomecalculator.infra.responses;

import pl.jt.demo.hotelincomecalculator.domain.RoomOccupationAndIncome;

public record OccupationAndIncomeResponse(int occupiedPremiumRooms,
                                          int occupiedEconomyRooms,
                                          double profitFromPremiumRooms,
                                          double profitFromEconomyRooms) {

  public static OccupationAndIncomeResponse from(RoomOccupationAndIncome roomOccupationAndIncome){
    return new OccupationAndIncomeResponse(roomOccupationAndIncome.getOccupiedPremium(),
        roomOccupationAndIncome.getOccupiedEconomy(),
        roomOccupationAndIncome.getPremiumProfit().doubleValue(),
        roomOccupationAndIncome.getEconomyProfit().doubleValue());
  }
}
