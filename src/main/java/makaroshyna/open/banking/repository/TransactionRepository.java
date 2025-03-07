package makaroshyna.open.banking.repository;

import java.util.List;
import makaroshyna.open.banking.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findTop10ByAccountIbanOrderByTimestampDesc(String accountIban);
}
