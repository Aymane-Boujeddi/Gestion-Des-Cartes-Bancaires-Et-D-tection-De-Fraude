package utils;

import java.time.LocalDate;

public class FraudDetection {

    public static boolean isFraudulentTransaction(double amount, String location, LocalDate date, int cardId) {
        
        // Check 1: Amount too high
        if (amount > 5000) {
            System.out.println("Fraud Alert: Amount too high - " + amount);
            return true;
        }
        
        // Check 2: Suspicious location
        if (location != null && location.toLowerCase().contains("suspicious")) {
            System.out.println("Fraud Alert: Suspicious location - " + location);
            return true;
        }
        
        // Check 3: Weekend transactions over 1000
        if (isWeekend(date) && amount > 1000) {
            System.out.println("Fraud Alert: High weekend transaction - " + amount);
            return true;
        }
        
        // All checks passed
        System.out.println("Transaction approved for card " + cardId);
        return false;
    }
    
    static boolean isWeekend(LocalDate date) {
        int dayOfWeek = date.getDayOfWeek().getValue();
        return dayOfWeek == 6 || dayOfWeek == 7; // Saturday or Sunday
    }
}
