package com.banking;

import static org.junit.jupiter.api.Assertions.*;

import com.banking.accounts.BankAccount;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class TestsTest {
    @Test
    void isFileRead() {
        DataHandler dataHandler = new DataHandler("database.txt");
        String[] database = dataHandler.readFromFile().split(",\\s*");
        for (String s : database) {
            assertNotNull(s);
        }
    }

    @Test
    void isWithdrawOperates() {
        BankAccount testing = new BankAccount(null, 500);
        testing.withdraw(250);
        assertEquals(String.valueOf(250.0), String.valueOf(testing.getBalance()));
        System.out.println(testing.getBalance());
        testing.withdraw(500);
        assertEquals(String.valueOf(250.0), String.valueOf(testing.getBalance()));
        System.out.println(testing.getBalance());
    }

    @Test
    void isTransferOperates() {
        BankAccount recipient = new BankAccount(null, 0);
        BankAccount myAccount = new BankAccount("my name", 500);
        Map<String, BankAccount> bankAccountMap = new HashMap<>();
        bankAccountMap.put(myAccount.getName(), myAccount);
        myAccount.transfer(recipient, "John", 100, bankAccountMap);
        BankAccount testing1 = bankAccountMap.get("John");
        assertAll(() -> assertEquals(recipient.getName(), testing1.getName()), () -> assertEquals(recipient.getBalance(), testing1.getBalance()));
    }
}
