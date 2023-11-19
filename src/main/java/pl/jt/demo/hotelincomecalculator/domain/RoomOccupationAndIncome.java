package pl.jt.demo.hotelincomecalculator.domain;

public record RoomOccupationAndIncome(int occupiedPremium,
                                      int occupiedEconomy,
                                      int premiumProfit,
                                      int economyProfit) {

}
