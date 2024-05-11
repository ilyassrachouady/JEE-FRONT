package com.example.projectebank;

import com.example.projectebank.entities.*;
import com.example.projectebank.enums.AccountStatus;
import com.example.projectebank.enums.OperationType;
import com.example.projectebank.repositories.AccountOperationRepository;
import com.example.projectebank.repositories.BankAccountRepository;
import com.example.projectebank.repositories.ClientRepository;
import com.example.projectebank.sevices.BankService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class ProjectEbankApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectEbankApplication.class, args);
    }
    @Bean
    CommandLineRunner commandLineRunner(BankService bankService){
        return args -> {
            bankService.consulter();
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
