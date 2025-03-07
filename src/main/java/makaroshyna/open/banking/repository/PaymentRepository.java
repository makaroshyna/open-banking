package makaroshyna.open.banking.repository;

import makaroshyna.open.banking.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
