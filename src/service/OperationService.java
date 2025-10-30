package service;

import dao.impl.OperationDAOImpl;
import entity.OperationCarte;
import entity.OperationType;
import utils.FraudDetection;

import java.time.LocalDate;
import java.util.List;

public class OperationService {
    private OperationDAOImpl operationDAO;

    public OperationService(OperationDAOImpl operationDAO) {
        this.operationDAO = operationDAO;
    }

    public void createNewOperation(Double montant, String lieu, OperationType operationType, int cardId){
        // Check for fraud before creating the operation
        boolean isFraud = FraudDetection.isFraudulentTransaction(montant, lieu, LocalDate.now(), cardId);
        
        if (isFraud) {
            System.out.println("Operation rejected: Fraud detected for card " + cardId);
            return;
        }
        
        // Create and save the operation if no fraud detected
        OperationCarte newOperation = new OperationCarte(LocalDate.now(), montant, operationType, lieu, cardId);
        operationDAO.createNewOperation(newOperation);
        System.out.println("Operation created successfully for card " + cardId);
    }

    public List<OperationCarte> getCardHistory(int cardId) {
        return operationDAO.getCardHistory(cardId);
    }

    public void displayCardHistory(int cardId) {
        List<OperationCarte> operations = getCardHistory(cardId);
        if (operations.isEmpty()) {
            System.out.println("No operations found for card ID: " + cardId);
            return;
        }
        
        System.out.println("Operation History for Card " + cardId + ":");
        System.out.println("Date | Amount | Type | Location");
        System.out.println("-----|--------|------|----------");
        
        for (OperationCarte operation : operations) {
            System.out.println(operation.dateOperation() + " | " + 
                             operation.montant() + " | " + 
                             operation.operationType() + " | " + 
                             operation.lieu());
        }
    }
}
