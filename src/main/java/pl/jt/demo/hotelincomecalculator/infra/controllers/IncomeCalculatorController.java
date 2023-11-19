package pl.jt.demo.hotelincomecalculator.infra.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.jt.demo.hotelincomecalculator.infra.requests.CalculationRequest;
import pl.jt.demo.hotelincomecalculator.infra.responses.OccupationAndIncomeResponse;
import pl.jt.demo.hotelincomecalculator.infra.services.IncomeCalculationService;

@RestController
@RequestMapping("/hotel-income/v1")
public class IncomeCalculatorController {

  private IncomeCalculationService incomeCalculationService;

  IncomeCalculatorController(IncomeCalculationService incomeCalculationService) {
    this.incomeCalculationService = incomeCalculationService;
  }

  @PostMapping("/calculate")
  public ResponseEntity<OccupationAndIncomeResponse> calculateRoomOccupationAndIncome(@RequestBody CalculationRequest request) {
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(OccupationAndIncomeResponse
            .from(incomeCalculationService
                .calculateRoomOccupationAndIncome(request.premiumRoomsAvailable(), request.economyRoomsAvailable(), request.willingnessToPayList())));
  }
}
