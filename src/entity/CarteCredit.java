package entity;

import java.time.LocalDate;

public final class CarteCredit extends Carte{

    private Double plafondMensuel;
    private float tauxInteret;

        // Use this if  you want to get the card from DB , because you will need to assign the carteId with the id from the Db
    public CarteCredit(int carteId,String carteNumber, LocalDate expirationDate, StatutCarte statutCarte, int clientId, Double plafondMensuel, float tauxInteret, Double soldeDisponible) {
        super(carteId,carteNumber, expirationDate, statutCarte, clientId, soldeDisponible);
        this.plafondMensuel = plafondMensuel;
        this.tauxInteret = tauxInteret;
    }

    // Use this if you want to create a new card because the carteId will be assigned when creating the new column in Db with auto_increment
    public CarteCredit(String carteNumber, LocalDate expirationDate,StatutCarte statutCarte, int clientId, Double plafondMensuel, float tauxInteret, Double soldeDisponible) {
        super(carteNumber,expirationDate, statutCarte, clientId, soldeDisponible);
        this.plafondMensuel = plafondMensuel;
        this.tauxInteret = tauxInteret;
    }


    public Double getPlafondMensuel() {
        return plafondMensuel;
    }

    public void setPlafondMensuel(Double plafondMensuel) {
        this.plafondMensuel = plafondMensuel;
    }

    public float getTauxInteret() {
        return tauxInteret;
    }

    public void setTauxInteret(float tauxInteret) {
        this.tauxInteret = tauxInteret;
    }

    @Override
    public String toString() {
        return "CarteCredit{" +
                ", carteId=" + carteId +
                ", carteNumber='" + carteNumber + '\'' +
                ",solde Disponible" + soldeDisponible +
                ", expirationDate=" + expirationDate +
                ", statutCarte=" + statutCarte +
                "plafondMensuel=" + plafondMensuel +
                ", tauxInteret=" + tauxInteret +
                ", clientId=" + clientId +
                '}';
    }
}
