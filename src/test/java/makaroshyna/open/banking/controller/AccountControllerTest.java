package makaroshyna.open.banking.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import makaroshyna.open.banking.service.AccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;


@ExtendWith(SpringExtension.class)
@WebMvcTest(AccountController.class)
public class AccountControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private AccountService accountService;

    @Test
    @WithMockUser(username = "testUser")
    void getAccountBalance_ShouldReturnBalance() throws Exception {
        when(accountService.getAccountBalance("DE89370400440532013000"))
                .thenReturn(new BigDecimal("5000.00"));

        mockMvc.perform(get("/api/accounts/DE89370400440532013000/balance"))
                .andExpect(status().isOk())
                .andExpect(content().string("5000.00"));    }
}
