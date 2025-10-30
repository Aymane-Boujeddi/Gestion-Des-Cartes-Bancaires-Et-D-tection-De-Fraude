package entity;

import java.time.LocalDate;

public final class CartePrepayee extends Carte{

    // Use this if  you want to get the card from DB , because you will need to assign the carteId with the id from the Db
    public CartePrepayee(int carteId,String carteNumber, LocalDate expirationDate, StatutCarte statutCarte, int clientId, Double soldeDisponible) {
        super(carteId,carteNumber, expirationDate, statutCarte, clientId, soldeDisponible);
    }

    // Use this if you want to create a new card because the carteId will be assigned when creating the new column in Db with auto_increment
    public CartePrepayee(String carteNumber,LocalDate expirationDate, StatutCarte statutCarte, int clientId, Double soldeDisponible) {
        super(carteNumber,expirationDate, statutCarte, clientId, soldeDisponible);
    }



    @Override
    public String toString() {
        return "CartePrepayee{" +
                ", carteId=" + carteId +
                ", carteNumber='" + carteNumber + '\'' +
                ", expirationDate=" + expirationDate +
                ", statutCarte=" + statutCarte +
                "soldeDisponible=" + soldeDisponible +
                ", clientId=" + clientId +
                '}';
    }
}
