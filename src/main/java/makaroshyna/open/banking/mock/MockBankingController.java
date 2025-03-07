package makaroshyna.open.banking.mock;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import makaroshyna.open.banking.model.Transaction;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mock-api")
public class MockBankingController {
    private static final Map<String, BigDecimal> accountBalances = Map.of(
            "DE89370400440532013000", new BigDecimal("5000.00"),
            "FR1420041010050500013M02606", new BigDecimal("2500.00")
    );

    private static final Map<String, List<Transaction>> transactions = Map.of(
            "DE89370400440532013000", List.of(
                    new Transaction(1L, "DE89370400440532013000", new BigDecimal("100.50"), "EUR", LocalDateTime.now().minusDays(1), "Grocery shopping"),
                    new Transaction(2L, "DE89370400440532013000", new BigDecimal("250.00"), "EUR", LocalDateTime.now().minusDays(2), "Online purchase")
            ),
            "FR1420041010050500013M02606", List.of(
                    new Transaction(3L, "FR1420041010050500013M02606", new BigDecimal("50.00"), "EUR", LocalDateTime.now().minusDays(3), "Coffee shop")
            )
    );

    @GetMapping("/accounts/{iban}/balance")
    public BigDecimal getMockAccountBalance(@PathVariable String iban) {
        return accountBalances.getOrDefault(iban, BigDecimal.ZERO);
    }

    @GetMapping("/accounts/{iban}/transactions")
    public List<Transaction> getMockTransactions(@PathVariable String iban) {
        return transactions.getOrDefault(iban, List.of());
    }
}
