package com.banking.accounts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/accounts")
public class BankAccountController {
    private final BankAccountService bankAccountService;

    @Autowired
    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @GetMapping()
    public List<BankAccount> getBankAccounts() {
        return bankAccountService.getBankAccounts();
    }

    @GetMapping("/home")
    public String getStartingPage() {
        return "Welcome!\n Please, log in or register account";
    }

    @PostMapping
    public void registerNewBankAccount(@RequestBody BankAccount bankAccount) {
        bankAccountService.addNewBankAccount(bankAccount);
    }

    @DeleteMapping(path = "{bankAccountId}")
    public void deleteBankAccount(@PathVariable("bankAccountId") Long bankAccountId) {
        bankAccountService.deleteBankAccount(bankAccountId);
    }

    @PutMapping(path = "{bankAccountId}")
    public void updateBankAccount(@PathVariable("bankAccountId") Long bankAccountId, @RequestParam(required = false) String name, @RequestParam(required = false) String password, @RequestParam(required = false) String balance) {
        bankAccountService.updateBankAccount(bankAccountId, name, password, balance);
    }
}