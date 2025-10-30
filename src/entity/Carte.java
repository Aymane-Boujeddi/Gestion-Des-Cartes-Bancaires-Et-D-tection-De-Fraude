package entity;

import java.time.LocalDate;

public sealed class Carte permits CarteCredit,CarteDebit,CartePrepayee {
    protected Integer carteId;
    protected  String carteNumber;
    protected LocalDate expirationDate;
    protected StatutCarte statutCarte;
    protected Integer clientId;
    protected Double soldeDisponible;



    public Carte(int carteId,String carteNumber,LocalDate expirationDate, StatutCarte statutCarte,int clientId, Double soldeDisponible) {
        this.carteId = carteId;
        this.carteNumber = carteNumber;
        this.expirationDate = expirationDate;
        this.statutCarte = statutCarte;
        this.clientId = clientId;
        this.soldeDisponible = soldeDisponible;
    }

    public Carte(String carteNumber,LocalDate expirationDate, StatutCarte statutCarte,int clientId, Double soldeDisponible) {
        this.carteId = null;
        this.carteNumber = carteNumber;
        this.expirationDate = expirationDate;
        this.statutCarte = statutCarte;
        this.clientId = clientId;
        this.soldeDisponible = soldeDisponible;
    }

    public Double getSoldeDisponible() {
        return soldeDisponible;
    }

    public void setSoldeDisponible(Double soldeDisponible) {
        this.soldeDisponible = soldeDisponible;
    }

    public void setCarteId(Integer carteId) {
        this.carteId = carteId;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public int getCarteId() {
        return carteId;
    }

    public void setCarteId(int carteId) {
        this.carteId = carteId;
    }

    public String getCarteNumber() {
        return carteNumber;
    }

    public void setCarteNumber(String carteNumber) {
        this.carteNumber = carteNumber;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public StatutCarte getStatutCarte() {
        return statutCarte;
    }

    public void setStatutCarte(StatutCarte statutCarte) {
        this.statutCarte = statutCarte;
    }


}
