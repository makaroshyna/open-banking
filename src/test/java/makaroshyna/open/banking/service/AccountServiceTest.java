package makaroshyna.open.banking.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.util.List;
import makaroshyna.open.banking.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {
    @Mock
    private BankingClientService bankingClientService;
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        accountService = new AccountService(bankingClientService);
        lenient().when(bankingClientService.getExternalAccountBalance("DE89370400440532013000"))
                .thenReturn(new BigDecimal("5000.00"));

        lenient().when(bankingClientService.getExternalTransactions("DE89370400440532013000"))
                .thenReturn(List.of(
                        new Transaction(1L, "DE89370400440532013000", new BigDecimal("100.00"), "EUR", null, "Shopping"),
                        new Transaction(2L, "DE89370400440532013000", new BigDecimal("50.00"), "EUR", null, "Coffee")
                ));
    }

    @Test
    void getAccountBalance_ShouldReturnBalance_WhenAccountExists() {
        BigDecimal balance = accountService.getAccountBalance("DE89370400440532013000");

        assertEquals(new BigDecimal("5000.00"), balance);
        verify(bankingClientService, times(1)).getExternalAccountBalance("DE89370400440532013000");
    }

    @Test
    void getRecentTransactions_ShouldReturnTransactions_WhenExists() {
        List<Transaction> transactions = accountService.getRecentTransactions("DE89370400440532013000");

        assertEquals(2, transactions.size());
        verify(bankingClientService, times(1)).getExternalTransactions("DE89370400440532013000");
    }
}
