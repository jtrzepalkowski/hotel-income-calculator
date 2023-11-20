package pl.jt.demo.hotelincomecalculator.infra.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.jt.demo.hotelincomecalculator.infra.requests.CalculationRequest;
import pl.jt.demo.hotelincomecalculator.infra.responses.GenericErrorResponse;
import pl.jt.demo.hotelincomecalculator.infra.responses.OccupationAndIncomeResponse;
import pl.jt.demo.hotelincomecalculator.infra.services.IncomeCalculationService;

@RestController
@RequestMapping("/hotel-income/v1")
public class IncomeCalculatorController {

  private final IncomeCalculationService incomeCalculationService;

  IncomeCalculatorController(final IncomeCalculationService incomeCalculationService) {
    this.incomeCalculationService = incomeCalculationService;
  }

  @Operation(summary = "Calculate profit based on given data")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Calculation successful",
      content = { @Content(mediaType = "application/json",
          schema = @Schema(implementation = OccupationAndIncomeResponse.class)) }),
    @ApiResponse(responseCode = "400", description = "Malformed request",
      content = { @Content(mediaType = "application/json",
          schema = @Schema(implementation = GenericErrorResponse.class)) }),
    @ApiResponse(responseCode = "500", description = "Internal server error",
        content = { @Content(mediaType = "application/json",
            schema = @Schema(implementation = GenericErrorResponse.class)) }),
  })
  @PostMapping("/calculate")
  public ResponseEntity<OccupationAndIncomeResponse> calculateRoomOccupationAndIncome(@RequestBody CalculationRequest request) {
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(OccupationAndIncomeResponse
            .from(incomeCalculationService
                .calculateRoomOccupationAndIncome(request.premiumRoomsAvailable(), request.economyRoomsAvailable(), request.willingnessToPayList())));
  }
}
