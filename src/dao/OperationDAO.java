package dao;

import entity.OperationCarte;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

public interface OperationDAO {

    void createNewOperation(entity.OperationCarte newOperation);

    // Method to calculate total spending for a card on a specific date (for daily limits)
    double getDailySpending(int cardId, LocalDate date);

    // Method to calculate total spending for a card in current month (for monthly limits)
    double getMonthlySpending(int cardId, LocalDate date);
    
    // Method to get card operation history
    List<OperationCarte> getCardHistory(int cardId);
}
