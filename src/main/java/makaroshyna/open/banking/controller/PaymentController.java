package makaroshyna.open.banking.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.math.BigDecimal;
import java.util.Map;
import makaroshyna.open.banking.model.Payment;
import makaroshyna.open.banking.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
@Tag(name = "Payments", description = "Endpoints for initiating payments")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Operation(
            summary = "Initiate a Payment",
            description = "Transfers money from one IBAN to another",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Payment successfully initiated"),
                    @ApiResponse(responseCode = "400", description = "Insufficient funds", content = @Content)
            }
    )
    @PostMapping("/initiate")
    public ResponseEntity<Payment> initiatePayment(
            @Parameter(description = "Payment request details", required = true, schema = @Schema(
                    example = "{ \"fromIban\": \"DE89370400440532013000\", \"toIban\": " +
                              "\"FR1420041010050500013M02606\", \"amount\": 100.00, \"currency\": \"EUR\" }"))
            @RequestBody Map<String, Object> request) {
        String fromIban = (String) request.get("fromIban");
        String toIban = (String) request.get("toIban");
        BigDecimal amount = new BigDecimal(request.get("amount").toString());
        String currency = (String) request.get("currency");

        Payment payment = paymentService.initiatePayment(fromIban, toIban, amount, currency);
        return ResponseEntity.ok(payment);
    }
}
