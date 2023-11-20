package pl.jt.demo.hotelincomecalculator.infra.responses;

public record GenericErrorResponse(int responseStatus,
                                   String message) {
}
