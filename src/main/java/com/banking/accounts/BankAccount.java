package com.banking.accounts;

import jakarta.persistence.*;

import java.util.*;

@Entity
@Table
public class BankAccount {
    @Id
    @SequenceGenerator(
            name = "bankAccount_sequence",
            sequenceName = "bankAccount_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "bankAccount_sequence"
    )
    private Long id;
    private String name;
    private String password;
    private double balance;
    public BankAccount(){
        this.name = "";
        this.balance = 0.0;
    }
    public BankAccount(Long id, String name, String password, double balance){
        this.id = id;
        this.name = name;
        this.password = password;
        this.balance = balance;
    }

    public BankAccount(String name, String password, double balance) {
        this.name = name;
        this.password = password;
        this.balance = balance;
    }

    public BankAccount (String name, double balance){
        this.name = name;
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void deposit(double amount) {
        this.balance += amount;
    }
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            this.balance -= amount;
        } else {
            System.out.println("Operation declined");
        }
    }
    public void printBalance() {
        System.out.println("Current balance: " + balance);
    }
    public void transfer(BankAccount recipient, String name, double amount, Map<String, BankAccount> bankAccounts) {
        if (amount > 0 && amount <= balance) {
            boolean found = false;
            if (!bankAccounts.isEmpty()) {
                for (String key : bankAccounts.keySet()) {
                    BankAccount instance = bankAccounts.get(key);
                    if (name.equals(instance.getName())) {
                        found = true;
                        instance.deposit(amount);
                        break;
                    }
                }
                if (!found) {
                    recipient.setName(name);
                    recipient.deposit(amount);
                    bankAccounts.put(recipient.getName(), recipient);
                }
            }
            this.withdraw(amount);
            System.out.println(amount + " transferred successfully.");
        } else {
            System.out.println("Invalid amount for transfer or insufficient balance.");
        }
    }
}