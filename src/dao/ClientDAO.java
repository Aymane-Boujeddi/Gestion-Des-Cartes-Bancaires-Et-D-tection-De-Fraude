package dao;

import entity.BankClient;

import java.sql.Connection;
import java.util.Optional;

public interface ClientDAO {

    public void createClient(BankClient newClient);
    public Optional<BankClient> getClientByEmail(String clientEmail);
    public Optional<BankClient> getClientByName(String clientEmail);
    public void updateClientName(String newName,String clientEmail);
    public void updateClientEmail(String newEmail,String clientEmail);
    public void updateClientPhoneNumber(String newPhoneNumber,String clientEmail);
    public Optional<Integer> getClientId(String clientEmail);
}
