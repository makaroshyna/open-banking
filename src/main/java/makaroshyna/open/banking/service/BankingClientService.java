package makaroshyna.open.banking.service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import makaroshyna.open.banking.model.Transaction;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BankingClientService {
    private final RestTemplate restTemplate;
    private final String MOCK_BANKING_API_URL = "http://localhost:8080/mock-api/accounts/";

    public BankingClientService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public BigDecimal getExternalAccountBalance(String iban) {
        return restTemplate.getForObject(MOCK_BANKING_API_URL + iban + "/balance", BigDecimal.class);
    }

    public List<Transaction> getExternalTransactions(String iban) {
        Transaction[] transactions = restTemplate.getForObject(MOCK_BANKING_API_URL + iban + "/transactions", Transaction[].class);
        return transactions != null ? Arrays.asList(transactions) : List.of();
    }
}
