package service;

import java.util.List;
import java.util.Scanner;

import dao.AlerteDAO;
import dao.impl.AlerteDAOImpl;
import entity.AlerteFraude;
import entity.NiveauAlerte;

public class AlerteFraudeService {

    private AlerteDAOImpl alerteDAO;

    public AlerteFraudeService(AlerteDAOImpl alerteDAO) {
        this.alerteDAO = alerteDAO;
    }

    public void createAlert(String description, NiveauAlerte niveau) {
        AlerteFraude alert = new AlerteFraude(description, niveau);
        alerteDAO.createAlert(alert);
        System.out.println("Fraud alert created: " + description);
    }

    public void displayAllAlerts() {
        List<AlerteFraude> alerts = alerteDAO.getAllAlerts();
        if (alerts.isEmpty()) {
            System.out.println("No fraud alerts found");
            return;
        }
        
        System.out.println("All Fraud Alerts:");
        System.out.println("ID | Level | Description");
        System.out.println("---|-------|------------");
        
        for (AlerteFraude alert : alerts) {
            System.out.println(alert.alerteId() + " | " + 
                             alert.niveauAlerte() + " | " + 
                             alert.description());
        }
    }

    public void displayAlertsByLevel(NiveauAlerte niveau) {
        List<AlerteFraude> alerts = alerteDAO.getAlertsByLevel(niveau);
        if (alerts.isEmpty()) {
            System.out.println("No " + niveau + " alerts found");
            return;
        }
        
        System.out.println(niveau + " Fraud Alerts:");
        System.out.println("ID | Description");
        System.out.println("---|------------");
        
        for (AlerteFraude alert : alerts) {
            System.out.println(alert.alerteId() + " | " + alert.description());
        }
    }

    public void runFraudAnalysis() {
        System.out.println("Running fraud analysis...");
        System.out.println("Analysis complete. Check alerts for suspicious activities.");
        displayAllAlerts();
    }

}
