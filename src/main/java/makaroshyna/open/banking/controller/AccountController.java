package makaroshyna.open.banking.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.math.BigDecimal;
import java.util.List;
import makaroshyna.open.banking.model.Transaction;
import makaroshyna.open.banking.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/accounts")
@Tag(name = "Accounts", description = "Endpoints for account balance and transactions")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @Operation(summary = "Get Account Balance", description = "Retrieves the current balance of an account")
    @GetMapping("/{iban}/balance")
    public ResponseEntity<BigDecimal> getAccountBalance(
            @Parameter(description = "IBAN of the account", required = true)
            @PathVariable String iban) {
        BigDecimal balance = accountService.getAccountBalance(iban);
        return ResponseEntity.ok(balance);
    }

    @Operation(summary = "Get Recent Transactions", description = "Retrieves the last 10 transactions of an account")
    @GetMapping("/{iban}/transactions")
    public ResponseEntity<List<Transaction>> getRecentTransactions(
            @Parameter(description = "IBAN of the account", required = true)
            @PathVariable String iban) {
        List<Transaction> transactions = accountService.getRecentTransactions(iban);
        return ResponseEntity.ok(transactions);
    }
}
