package dao.impl;

import dao.AlerteDAO;
import entity.AlerteFraude;
import entity.NiveauAlerte;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AlerteDAOImpl implements AlerteDAO {

    private Connection connection;

    public AlerteDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void createAlert(AlerteFraude alert) {
        String sql = "INSERT INTO alerts(description, niveauAlerte) VALUES(?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, alert.description());
            stmt.setString(2, alert.niveauAlerte().toString());
            
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Alert creation failed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<AlerteFraude> getAllAlerts() {
        List<AlerteFraude> alerts = new ArrayList<>();
        String sql = "SELECT * FROM alerts ORDER BY alerteId DESC";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int alerteId = rs.getInt("alerteId");
                    String description = rs.getString("description");
                    NiveauAlerte niveau = NiveauAlerte.valueOf(rs.getString("niveauAlerte"));
                    
                    AlerteFraude alert = new AlerteFraude(alerteId, description, niveau);
                    alerts.add(alert);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return alerts;
    }

    @Override
    public List<AlerteFraude> getAlertsByLevel(NiveauAlerte niveau) {
        List<AlerteFraude> alerts = new ArrayList<>();
        String sql = "SELECT * FROM alerts WHERE niveauAlerte = ? ORDER BY alerteId DESC";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, niveau.toString());
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int alerteId = rs.getInt("alerteId");
                    String description = rs.getString("description");
                    
                    AlerteFraude alert = new AlerteFraude(alerteId, description, niveau);
                    alerts.add(alert);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return alerts;
    }
}
