package com.example.projectebank.sevices;

import com.example.projectebank.entities.BankAccount;
import com.example.projectebank.entities.Client;
import com.example.projectebank.entities.CurrentAccount;
import com.example.projectebank.entities.SavingAccount;
import com.example.projectebank.exceptions.ClientNotFoundException;

import java.util.List;

public interface BankAccountService {
    Client saveClient(Client client);
    CurrentAccount saveCurrentBankAccount(double initialBalance, double overDraft , Long clientID) throws ClientNotFoundException;
    SavingAccount saveSavingBankAccount(double initialBalance, double interestRate , Long clientID) throws ClientNotFoundException;
    List<Client> listClients();
    BankAccount getBankAccount(String accountID);
    void debit(String accountID, double amount, String description);
    void credit(String accountID, double amount, String description);
    void transfer(String fromAccountID, String toAccountID, double amount, String description);

}
