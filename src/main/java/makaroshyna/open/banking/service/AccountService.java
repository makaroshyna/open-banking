package makaroshyna.open.banking.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import makaroshyna.open.banking.model.Account;
import makaroshyna.open.banking.model.Transaction;
import makaroshyna.open.banking.repository.AccountRepository;
import makaroshyna.open.banking.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public AccountService(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional(readOnly = true)
    public BigDecimal getAccountBalance(String iban) {
        Optional<Account> account = accountRepository.findById(iban);
        return account.map(Account::getBalance).orElseThrow(() -> new RuntimeException("Account not found"));
    }

    @Transactional(readOnly = true)
    public List<Transaction> getRecentTransactions(String iban) {
        return transactionRepository.findTop10ByAccountIbanOrderByTimestampDesc(iban);
    }
}
