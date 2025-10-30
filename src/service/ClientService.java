package service;

import dao.ClientDAO;
import dao.impl.ClientDAOImpl;
import entity.BankClient;
import exception.EmailAlreadyExistException;
import utils.Validation;

import java.util.List;
import java.util.Optional;

public class ClientService {
    private ClientDAOImpl clientDAO;

    public ClientService( ClientDAOImpl clientDAO) {
        this.clientDAO = clientDAO;
    }

    public void createNewClient(String clientName,String clientEmail,String clientPhoneNumber) throws EmailAlreadyExistException{

            isEmailExisting(clientEmail);
            BankClient newClient = new BankClient(clientName,clientEmail,clientPhoneNumber);
            clientDAO.createClient(newClient);

    }
    public void isEmailExisting(String email) throws EmailAlreadyExistException {
        if(clientDAO.getClientByEmail(email).isPresent()){
            throw new EmailAlreadyExistException("This email is already assigned to client");
        }

    }

    public List<BankClient> getAllClients() throws Exception {
        // the clientDao.getAllClients() will return Optional<List<BankClient>> print them if present if not throw exception
        Optional<List<BankClient>> clients = clientDAO.getAllClients();

        if(clients.isEmpty()){
            throw new Exception("There are no clients ");
        }

        return clients.get();


    }
}
