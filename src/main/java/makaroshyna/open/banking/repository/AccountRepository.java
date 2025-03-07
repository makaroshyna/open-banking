package makaroshyna.open.banking.repository;

import makaroshyna.open.banking.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {
}
