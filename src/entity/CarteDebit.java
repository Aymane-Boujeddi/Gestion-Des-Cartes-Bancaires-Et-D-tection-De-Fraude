package entity;

import java.time.LocalDate;

public final class CarteDebit extends Carte{

    private Double plafondJournalier;


    // Use this if  you want to get the card from DB , because you will need to assign the carteId with the id from the Db
    public CarteDebit(int carteId,String carteNumber, LocalDate expirationDate, StatutCarte statutCarte, int clientId, Double soldeDisponible, Double plafondJournalier) {
        super(carteId,carteNumber, expirationDate, statutCarte, clientId, soldeDisponible);
        this.plafondJournalier = plafondJournalier;
    }


    // Use this if you want to create a new card because the carteId will be assigned when creating the new column in Db with auto_increment
    public CarteDebit(String carteNumber,LocalDate expirationDate, StatutCarte statutCarte, int clientId, Double soldeDisponible, Double plafondJournalier) {
        super(carteNumber,expirationDate, statutCarte, clientId, soldeDisponible);
        this.plafondJournalier = plafondJournalier;
    }

    public Double getPlafondJournalier() {
        return plafondJournalier;
    }

    public void setPlafondJournalier(Double plafondJournalier) {
        this.plafondJournalier = plafondJournalier;
    }

    @Override
    public String toString() {
        return "CarteDebit{" +
                ", carteId=" + carteId +
                ", carteNumber='" + carteNumber + '\'' +
                ", soldeDisponible=" + soldeDisponible +
                ", expirationDate=" + expirationDate +
                ", statutCarte=" + statutCarte +
                "plafondJournalier=" + plafondJournalier +
                ", clientId=" + clientId +
                '}';
    }
}
