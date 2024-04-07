package com.banking.accounts;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class BankAccountService {
    private final BankAccountRepository bankAccountRepository;

    @Autowired
    public BankAccountService(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    public List<BankAccount> getBankAccounts() {
        return bankAccountRepository.findAll();
    }

    public void addNewBankAccount(BankAccount bankAccount) {
        Optional<BankAccount> accountOptional = bankAccountRepository.findBankAccountByName(bankAccount.getName());
        if(accountOptional.isPresent()){
            throw new IllegalStateException("name exists");
        }
        bankAccountRepository.save(bankAccount);
        System.out.println(bankAccount.getName());
    }

    public void deleteBankAccount(Long bankAccountId) {
        if(!bankAccountRepository.existsById(bankAccountId)){
            throw new IllegalStateException("Account with id = " + bankAccountId + " not exists");
        }
        bankAccountRepository.deleteById(bankAccountId);
    }

    @Transactional
    public void updateBankAccount(Long bankAccountId, String name, String password, String balance) {
        BankAccount bankAccount = bankAccountRepository.findById(bankAccountId).orElseThrow(() -> new IllegalStateException("Account with id = " + bankAccountId + " not exists"));
        if(name != null && !name.isEmpty() && !Objects.equals(bankAccount.getName(), name)) {
            bankAccount.setName(name);
        }
        if(password != null && !password.isEmpty() && !Objects.equals(bankAccount.getPassword(), password)) {
            bankAccount.setPassword(password);
        }
        if(balance != null && Double.parseDouble(balance) > 0 && !Objects.equals(bankAccount.getBalance(), Double.parseDouble(balance))) {
            bankAccount.setBalance(Double.parseDouble(balance));
        }
    }
}