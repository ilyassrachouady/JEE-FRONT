package com.example.projectebank;

import com.example.projectebank.entities.*;
import com.example.projectebank.enums.AccountStatus;
import com.example.projectebank.enums.OperationType;
import com.example.projectebank.exceptions.BankAccountNotFound;
import com.example.projectebank.exceptions.ClientNotFoundException;
import com.example.projectebank.exceptions.InsufficientBalanceException;
import com.example.projectebank.repositories.AccountOperationRepository;
import com.example.projectebank.repositories.BankAccountRepository;
import com.example.projectebank.repositories.ClientRepository;
import com.example.projectebank.sevices.BankAccountService;
import com.example.projectebank.sevices.BankService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class ProjectEbankApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectEbankApplication.class, args);
    }
    @Bean
    CommandLineRunner commandLineRunner(BankAccountService bankAccountService){
        return args -> {
            Stream.of("Alex", "Clyde", "Peter").forEach(name -> {
                Client client = new Client();
                client.setName(name);
                client.setEmail(name+"@gmail.com");
                bankAccountService.saveClient(client);
            });

            bankAccountService.listClients().forEach(client -> {
                try {
                    bankAccountService.saveCurrentBankAccount(Math.random() * 10000, 1000, client.getId());
                    bankAccountService.saveSavingBankAccount(Math.random() * 10000, 4.5, client.getId());
                    List<BankAccount> bankAccounts = bankAccountService.ListBankAccounts();
                    for (BankAccount bankAccount : bankAccounts) {
                        for (int i = 0; i < 10; i++) {
                            bankAccountService.credit(bankAccount.getId(), 1000 + Math.random()*100000, "Credit");
                        }

                        for (int i = 0; i < 10; i++) {
                            bankAccountService.debit(bankAccount.getId(), 1000 + Math.random()*1000, "Debit");
                        }
                    }
                } catch (ClientNotFoundException e) {
                    e.printStackTrace();
                } catch (InsufficientBalanceException | BankAccountNotFound e) {
                    e.printStackTrace();
                }
            });

        };
    }
    //@Bean
    CommandLineRunner start(ClientRepository clientRepository,
                            BankAccountRepository bankAccountRepository,
                            AccountOperationRepository accountOperationRepository) {
        return args -> {
            Stream.of("Laura", "Theo", "Francesca").forEach(name -> {
                Client client = new Client();
                client.setName(name);
                client.setEmail(name + "@gmail.com");
                clientRepository.save(client);
            });
            clientRepository.findAll().forEach(cust -> {
                CurrentAccount currentAccount = new CurrentAccount();
                currentAccount.setId(UUID.randomUUID().toString());
                currentAccount.setBalance(Math.random() * 100000);
                currentAccount.setCreateAcc(new Date());
                currentAccount.setStatus(AccountStatus.CREATED);
                currentAccount.setClient(cust);
                currentAccount.setOverDraft(1000);
                bankAccountRepository.save(currentAccount);

                SavingAccount savingAccount = new SavingAccount();
                savingAccount.setId(UUID.randomUUID().toString());
                savingAccount.setBalance(Math.random() * 100000);
                savingAccount.setCreateAcc(new Date());
                savingAccount.setStatus(AccountStatus.CREATED);
                savingAccount.setClient(cust);
                savingAccount.setInterestRate(5.5);
                bankAccountRepository.save(savingAccount);
            });
        };
    }

}
