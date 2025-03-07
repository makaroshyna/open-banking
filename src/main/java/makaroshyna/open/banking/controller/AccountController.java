package makaroshyna.open.banking.controller;

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
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/{iban}/balance")
    public ResponseEntity<BigDecimal> getAccountBalance(@PathVariable String iban) {
        BigDecimal balance = accountService.getAccountBalance(iban);
        return ResponseEntity.ok(balance);
    }

    @GetMapping("/{iban}/transactions")
    public ResponseEntity<List<Transaction>> getRecentTransactions(@PathVariable String iban) {
        List<Transaction> transactions = accountService.getRecentTransactions(iban);
        return ResponseEntity.ok(transactions);
    }
}
