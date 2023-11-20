package pl.jt.demo.hotelincomecalculator.infra.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.jt.demo.hotelincomecalculator.config.BaseTestClass;
import pl.jt.demo.hotelincomecalculator.infra.requests.CalculationRequest;
import pl.jt.demo.hotelincomecalculator.infra.responses.GenericErrorResponse;
import pl.jt.demo.hotelincomecalculator.infra.responses.OccupationAndIncomeResponse;
import pl.jt.demo.hotelincomecalculator.infra.services.IncomeCalculationService;

public class IncomeCalculatorControllerIT extends BaseTestClass {

  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate testRestTemplate;

  @SpyBean
  private IncomeCalculationService spyIncomeCalculationService;

  @Test
  void calculateRoomOccupationAndIncome_shouldReturn200OK_WhenProperDataProvided() {

    String url = "http://localhost:" + port + "/hotel-income/v1/calculate";
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "application/json");

    CalculationRequest request = new CalculationRequest(3, 3, willingnessToPay);

    HttpEntity<CalculationRequest> requestEntity = new HttpEntity<>(request, headers);
    ResponseEntity<OccupationAndIncomeResponse> responseEntity = testRestTemplate.postForEntity(url, requestEntity, OccupationAndIncomeResponse.class);
    OccupationAndIncomeResponse response = Objects.requireNonNull(responseEntity.getBody());

    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertEquals(response.occupiedPremiumRooms(), 3);
    assertEquals(response.occupiedEconomyRooms(), 3);
    assertEquals(response.profitFromPremiumRooms(), 738);
    assertEquals(response.profitFromEconomyRooms(), 167);
  }

  @Test
  void calculateRoomOccupationAndIncome_shouldReturn400BadRequest_WhenMalformedWillingnessToPayDataProvided() {

    String url = "http://localhost:" + port + "/hotel-income/v1/calculate";
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "application/json");

    CalculationRequest request = new CalculationRequest(3, 3, List.of(1, 2, -123));

    HttpEntity<CalculationRequest> requestEntity = new HttpEntity<>(request, headers);
    ResponseEntity<GenericErrorResponse> responseEntity = testRestTemplate.postForEntity(url, requestEntity, GenericErrorResponse.class);
    GenericErrorResponse response = Objects.requireNonNull(responseEntity.getBody());

    assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    assertEquals(400, response.responseStatus());
    assertEquals("Error: -123 is not usable. Willingness to pay with positive integers only can be used.", response.message());
  }

  @Test
  void calculateRoomOccupationAndIncome_shouldReturn400BadRequest_WhenMalformedRoomAvailabilityDataProvided() {

    String url = "http://localhost:" + port + "/hotel-income/v1/calculate";
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "application/json");

    CalculationRequest request = new CalculationRequest(3, -2, List.of(1, 2, 4));

    HttpEntity<CalculationRequest> requestEntity = new HttpEntity<>(request, headers);
    ResponseEntity<GenericErrorResponse> responseEntity = testRestTemplate.postForEntity(url, requestEntity, GenericErrorResponse.class);
    GenericErrorResponse response = Objects.requireNonNull(responseEntity.getBody());

    assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    assertEquals(400, response.responseStatus());
    assertEquals("Error: Number of rooms provided must be a positive integer. Data received: -2", response.message());
  }

  @Test
  void calculateRoomOccupationAndIncome_shouldReturn500InternalServerError_WhenRuntimeExceptionIsThrown() {

    String url = "http://localhost:" + port + "/hotel-income/v1/calculate";
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "application/json");

    CalculationRequest request = new CalculationRequest(3, 2, List.of(1, 2, 6));

    Mockito.when(spyIncomeCalculationService.calculateRoomOccupationAndIncome(3,2, List.of(1, 2, 6)))
        .thenThrow(new NullPointerException("test message"));

    HttpEntity<CalculationRequest> requestEntity = new HttpEntity<>(request, headers);
    ResponseEntity<GenericErrorResponse> responseEntity = testRestTemplate.postForEntity(url, requestEntity, GenericErrorResponse.class);
    GenericErrorResponse response = Objects.requireNonNull(responseEntity.getBody());

    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    assertEquals(500, response.responseStatus());
    assertEquals("test message", response.message());
  }
}
