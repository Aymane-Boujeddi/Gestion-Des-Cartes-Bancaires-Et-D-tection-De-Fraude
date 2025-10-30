package dao.impl;

import dao.OperationDAO;
import entity.OperationCarte;
import entity.OperationType;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OperationDAOImpl implements OperationDAO {
    private Connection connection;

    public OperationDAOImpl(Connection connection) {
        this.connection = connection;
    }
    
    public Connection getConnection() {
        return connection;
    }

    public void createNewOperation(OperationCarte newOperation){
        String sql = "INSERT INTO operations(dateOperation,montant,operationType,lieuOperation,cardId) values(?,?,?,?,?)";

        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setDate(1,Date.valueOf(newOperation.dateOperation()));
            stmt.setDouble(2, newOperation.montant());
            stmt.setString(3, newOperation.operationType().toString());
            stmt.setString(4,newOperation.lieu());
            stmt.setInt(5,newOperation.cardId());
            int rowsAffected = stmt.executeUpdate();
            if(rowsAffected == 0 ){
                throw new SQLException("Operation creation failed");
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public double getDailySpending(int cardId, LocalDate date) {
        String sql = "SELECT SUM(montant) FROM operations WHERE cardId = ? AND DATE(dateOperation) = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, cardId);
            stmt.setDate(2, Date.valueOf(date));
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    @Override
    public double getMonthlySpending(int cardId, LocalDate date) {
        String sql = "SELECT SUM(montant) FROM operations WHERE cardId = ? AND YEAR(dateOperation) = ? AND MONTH(dateOperation) = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, cardId);
            stmt.setInt(2, date.getYear());
            stmt.setInt(3, date.getMonthValue());
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    @Override
    public List<OperationCarte> getCardHistory(int cardId) {
        List<OperationCarte> operations = new ArrayList<>();
        String sql = "SELECT * FROM operations WHERE cardId = ? ORDER BY dateOperation DESC";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, cardId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    LocalDate dateOperation = rs.getDate("dateOperation").toLocalDate();
                    double montant = rs.getDouble("montant");
                    OperationType operationType = OperationType.valueOf(rs.getString("operationType"));
                    String lieu = rs.getString("lieuOperation");
                    
                    OperationCarte operation = new OperationCarte(dateOperation, montant, operationType, lieu, cardId);
                    operations.add(operation);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return operations;
    }
}
