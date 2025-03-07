package makaroshyna.open.banking.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/mock-api/**").permitAll()
                        .requestMatchers(
                                "/api/payments/initiate",
                                "/api/accounts/**").authenticated()
                        .anyRequest().authenticated()
                )
                .httpBasic(withDefaults())
                .build();
    }
}
