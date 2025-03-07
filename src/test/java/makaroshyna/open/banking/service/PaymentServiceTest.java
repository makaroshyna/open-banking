package makaroshyna.open.banking.service;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import makaroshyna.open.banking.model.Payment;
import makaroshyna.open.banking.model.PaymentStatus;
import makaroshyna.open.banking.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {
    @Mock
    private PaymentRepository paymentRepository;
    @Mock
    private AccountService accountService;
    private PaymentService paymentService;

    @BeforeEach
    void setUp() {
        paymentService = new PaymentService(paymentRepository, accountService);
        lenient().when(accountService.getAccountBalance("DE89370400440532013000"))
                .thenReturn(new BigDecimal("5000.00"));
    }

    @Test
    void initiatePayment_ShouldSucceed_WhenSufficientBalance() {
        Payment mockPayment = new Payment(1L, "DE89370400440532013000", "FR1420041010050500013M02606",
                new BigDecimal("100.00"), "EUR", PaymentStatus.PENDING, null);

        when(paymentRepository.save(any(Payment.class))).thenReturn(mockPayment);

        Payment payment = paymentService.initiatePayment(
                "DE89370400440532013000", "FR1420041010050500013M02606", new BigDecimal("100.00"), "EUR");

        assertEquals(PaymentStatus.COMPLETED, payment.getStatus());
        verify(paymentRepository, times(2)).save(any(Payment.class)); // Once for PENDING, once for COMPLETED
    }

    @Test
    void initiatePayment_ShouldFail_WhenInsufficientBalance() {
        when(accountService.getAccountBalance("DE89370400440532013000"))
                .thenReturn(new BigDecimal("50.00"));

        Exception exception = assertThrows(RuntimeException.class, () ->
                paymentService.initiatePayment("DE89370400440532013000", "FR1420041010050500013M02606",
                        new BigDecimal("100.00"), "EUR")
        );

        assertEquals("Insufficient funds", exception.getMessage());
    }
}
