package pl.jt.demo.hotelincomecalculator.infra.requests;

import java.util.List;

public record CalculationRequest(int premiumRoomsAvailable,
                                 int economyRoomsAvailable,
                                 List<Double> willingnessToPayList) {
}
