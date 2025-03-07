package makaroshyna.open.banking.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import makaroshyna.open.banking.model.Payment;
import makaroshyna.open.banking.model.PaymentStatus;
import makaroshyna.open.banking.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final AccountService accountService;

    public PaymentService(PaymentRepository paymentRepository, AccountService accountService) {
        this.paymentRepository = paymentRepository;
        this.accountService = accountService;
    }

    @Transactional
    public Payment initiatePayment(String fromIban, String toIban, BigDecimal amount, String currency) {
        BigDecimal senderBalance = accountService.getAccountBalance(fromIban);
        if (senderBalance.compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient funds");
        }

        Payment payment = Payment.builder()
                .fromIban(fromIban)
                .toIban(toIban)
                .amount(amount)
                .currency(currency)
                .status(PaymentStatus.PENDING)
                .timestamp(LocalDateTime.now())
                .build();
        payment = paymentRepository.save(payment);

        boolean success = processExternalPayment(payment);
        payment.setStatus(success ? PaymentStatus.COMPLETED : PaymentStatus.FAILED);

        return paymentRepository.save(payment);
    }

    private boolean processExternalPayment(Payment payment) {
        return true;
    }
}
