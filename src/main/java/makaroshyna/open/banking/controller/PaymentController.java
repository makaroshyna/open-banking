package makaroshyna.open.banking.controller;

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
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/initiate")
    public ResponseEntity<Payment> initiatePayment(@RequestBody Map<String, Object> request) {
        String fromIban = (String) request.get("fromIban");
        String toIban = (String) request.get("toIban");
        BigDecimal amount = new BigDecimal(request.get("amount").toString());
        String currency = (String) request.get("currency");

        Payment payment = paymentService.initiatePayment(fromIban, toIban, amount, currency);
        return ResponseEntity.ok(payment);
    }
}
