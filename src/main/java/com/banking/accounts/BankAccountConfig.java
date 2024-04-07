package com.banking.accounts;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class BankAccountConfig {

    @Bean
    CommandLineRunner commandLineRunner(BankAccountRepository bankAccountRepository) {
        return args -> {
            BankAccount tamara = new BankAccount("Tamara", null,  500);
            BankAccount jojo = new BankAccount("Jojo", null, 600);
//            bankAccountRepository.saveAll(List.of(tamara, jojo));
        };
    }
}