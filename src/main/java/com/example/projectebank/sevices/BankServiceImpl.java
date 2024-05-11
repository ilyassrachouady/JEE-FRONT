package com.example.projectebank.sevices;

import com.example.projectebank.entities.*;
import com.example.projectebank.enums.OperationType;
import com.example.projectebank.exceptions.BankAccountNotFound;
import com.example.projectebank.exceptions.ClientNotFoundException;
import com.example.projectebank.exceptions.InsufficientBalanceException;
import com.example.projectebank.repositories.AccountOperationRepository;
import com.example.projectebank.repositories.BankAccountRepository;
import com.example.projectebank.repositories.ClientRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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
    public BankAccount getBankAccount(String accountID) throws BankAccountNotFound {
        return bankAccountRepository.findById(accountID).orElseThrow(
                () ->  new BankAccountNotFound("Bank Account not found")
        );
    }

    @Override
    public void debit(String accountID, double amount, String description) throws BankAccountNotFound, InsufficientBalanceException {
        BankAccount bankAccount = getBankAccount(accountID);
        if (bankAccount.getBalance() < amount){
            throw new InsufficientBalanceException("Insufficient funds to conduct transaction");
        }
        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setType(OperationType.DEBIT);
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setOperationDate(new Date());
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepository.save(accountOperation);

        bankAccount.setBalance(bankAccount.getBalance() - amount);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void credit(String accountID, double amount, String description) throws BankAccountNotFound {
        BankAccount bankAccount = getBankAccount(accountID);
        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setType(OperationType.CREDIT);
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setOperationDate(new Date());
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepository.save(accountOperation);

        bankAccount.setBalance(bankAccount.getBalance() + amount);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void transfer(String fromAccountID, String toAccountID, double amount) throws InsufficientBalanceException, BankAccountNotFound {
        debit(fromAccountID, amount, "Transfer to " + toAccountID);
        credit(fromAccountID, amount, "Transfer from " + fromAccountID);
    }

    @Override
    public List<BankAccount> ListBankAccounts() {
        return bankAccountRepository.findAll();
    }
}
