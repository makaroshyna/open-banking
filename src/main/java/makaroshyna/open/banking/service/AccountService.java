package makaroshyna.open.banking.service;

import java.math.BigDecimal;
import java.util.List;
import makaroshyna.open.banking.model.Transaction;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    private final BankingClientService bankingClientService;

    public AccountService(BankingClientService bankingClientService) {
        this.bankingClientService = bankingClientService;
    }

    public BigDecimal getAccountBalance(String iban) {
        return bankingClientService.getExternalAccountBalance(iban);
    }

    public List<Transaction> getRecentTransactions(String iban) {
        return bankingClientService.getExternalTransactions(iban);
    }
}
