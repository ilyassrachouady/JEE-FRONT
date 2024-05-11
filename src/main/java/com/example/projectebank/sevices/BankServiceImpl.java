package com.example.projectebank.sevices;

import com.example.projectebank.entities.BankAccount;
import com.example.projectebank.entities.Client;
import com.example.projectebank.entities.CurrentAccount;
import com.example.projectebank.entities.SavingAccount;
import com.example.projectebank.exceptions.ClientNotFoundException;
import com.example.projectebank.repositories.AccountOperationRepository;
import com.example.projectebank.repositories.BankAccountRepository;
import com.example.projectebank.repositories.ClientRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service @Transactional @AllArgsConstructor @Slf4j
public class BankServiceImpl implements BankAccountService{
    private BankAccountRepository bankAccountRepository;
    private ClientRepository clientRepository;
    private AccountOperationRepository accountOperationRepository;

    @Override
    public Client saveClient(Client client) {
        log.info("Saving new client");
        return clientRepository.save(client);
    }

    @Override
    public CurrentAccount saveCurrentBankAccount(double initialBalance, double overDraft, Long clientID) throws ClientNotFoundException {
        Client client = clientRepository.findById(clientID).orElse(null);
        if (client == null) {
            throw new ClientNotFoundException("Client not found");
        }
        CurrentAccount bankAccount = new CurrentAccount();
        bankAccount.setId(UUID.randomUUID().toString());
        bankAccount.setBalance(initialBalance);
        bankAccount.setOverDraft(overDraft);
        bankAccount.setClient(client);
        return bankAccountRepository.save(bankAccount);
    }

    @Override
    public SavingAccount saveSavingBankAccount(double initialBalance, double interestRate, Long clientID) throws ClientNotFoundException {
        Client client = clientRepository.findById(clientID).orElse(null);
        if (client == null) {
            throw new ClientNotFoundException("Client not found");
        }
        SavingAccount bankAccount = new SavingAccount();
        bankAccount.setId(UUID.randomUUID().toString());
        bankAccount.setBalance(initialBalance);
        bankAccount.setInterestRate(interestRate);
        bankAccount.setClient(client);
        return bankAccountRepository.save(bankAccount);
    }


    @Override
    public List<Client> listClients() {
        return clientRepository.findAll();
    }

    @Override
    public BankAccount getBankAccount(String accountID) {
        BankAccount bankAccount = bankAccountRepository.findById(accountID).orElseThrow(
                () ->  new BankAccountNotFound("Bank Account not found")
        );
        return null;
    }

    @Override
    public void debit(String accountID, double amount, String description) {

    }

    @Override
    public void credit(String accountID, double amount, String description) {

    }

    @Override
    public void transfer(String fromAccountID, String toAccountID, double amount, String description) {

    }
}
