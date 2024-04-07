package com.banking;

import com.banking.accounts.BankAccount;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;

@SpringBootApplication
public class App {
    private static final Logger logger = LogManager.getLogger(App.class);
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
        DataHandler dataHandler = new DataHandler("database.txt");
        String[] database = dataHandler.readFromFile().split(",\\s*");
        Map<String, BankAccount> bankAccounts = new HashMap<>();
        BankAccount myAccount = new BankAccount();
        BankAccount recipient = new BankAccount();
        Scanner scan = new Scanner(System.in);
        double amount;

        if(!database[0].isEmpty()) {
            for (int i = 0, j = i + 1, a = 1000; i < database.length; i += 2, j += 2, a++) {
                BankAccount account = new BankAccount(database[i], Double.parseDouble(database[j]));
                bankAccounts.put("id_" + a, account);
            }
        }

        System.out.println("Welcome!");
        while (myAccount.getName().isEmpty()) {
            System.out.println(" 1. Log in;\n 2. Create account");
            switch (scan.next()) {
                case "1":
                    boolean found = false;
                    System.out.println("Enter user name");
                    String name = scan.next();
                    if(!bankAccounts.isEmpty()) {
                        for(String key : bankAccounts.keySet()) {
                            BankAccount instance = bankAccounts.get(key);
                            if(name.equals(instance.getName())) {
                                found = true;
                                System.out.println("Logging successful");
                                myAccount.setName(instance.getName());
                                myAccount.setBalance(instance.getBalance());
                                logger.info("Welcome back " + myAccount.getName());
                                break;
                            }
                        }
                        if(!found) {
                            System.out.println("No user found");
                            break;
                        }
                    } else {
                        System.out.println("No user found");
                    }
                    break;
                case "2":
                    System.out.println("Enter your name");
                    String newName = scan.next();
                    System.out.println("Make first deposit");
                    double newDeposit = scan.nextDouble();
                    myAccount.setName(newName);
                    myAccount.setBalance(newDeposit);
                    bankAccounts.put("myAccount", myAccount);
                    break;
                default:
                    System.out.println("Wrong input");
                    logger.error("Failed to process. Wrong input");
            }
        }

        while (true) {
            System.out.println("Choose operation:\n 1. Deposit;\n 2. Withdraw;\n 3. Balance;\n 4. Transfer;\n 5. Exit");
            switch (scan.next()) {
                case "1":
                    System.out.println("Enter the amount to deposit");
                    amount = scan.nextDouble();
                    myAccount.deposit(amount);
                    break;
                case "2":
                    System.out.println("Enter the amount to withdraw");
                    amount = scan.nextDouble();
                    myAccount.withdraw(amount);
                    break;
                case "3":
                    myAccount.printBalance();
                    break;
                case "4":
                    System.out.println("Enter the recipient name");
                    String recipientName = scan.next();
                    System.out.println("Enter the amount to transfer");
                    amount = scan.nextDouble();
                    myAccount.transfer(recipient, recipientName, amount, bankAccounts);
                    break;
                case "5":
                    for (String key : bankAccounts.keySet()){
                        BankAccount instance = bankAccounts.get(key);
                        if(instance.getName().equals(myAccount.getName())){
                            instance.setBalance(myAccount.getBalance());
                        }
                    }
                    if(database[0].isEmpty()) {
                        ArrayList<String> data = new ArrayList<>();
                        for (String key : bankAccounts.keySet()) {
                            BankAccount instance = bankAccounts.get(key);
                            data.add(instance.getName());
                            data.add(String.valueOf(instance.getBalance()));
                        }
                        dataHandler.writeToFile(data.toString().replace("[", "").replace("]", ""));
                    }
                    if(!database[0].isEmpty()) {
                        ArrayList<String> newData = new ArrayList<>();
                        Collections.addAll(newData, database);
                        for(String key : bankAccounts.keySet()) {
                            BankAccount instance = bankAccounts.get(key);
                            if (newData.contains(instance.getName())) {
                                newData.set(newData.indexOf(instance.getName()) + 1, String.valueOf(instance.getBalance()));
                            } else {
                                newData.add(instance.getName());
                                newData.add(String.valueOf(instance.getBalance()));
                            }
                        }
                        dataHandler.writeToFile(newData.toString().replace("[", "").replace("]", ""));

                    }
                    System.out.println("Exiting...");
                    logger.info("Goodbye!");
                    scan.close();
                    logger.info("New data added to db");
                    return;
                default:
                    System.out.println("Wrong operation");
            }
        }
    }
}
