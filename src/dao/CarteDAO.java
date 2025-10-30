package dao;

import entity.Carte;
import entity.CarteCredit;
import entity.CarteDebit;
import entity.CartePrepayee;

import java.sql.Connection;
import java.util.List;

public interface CarteDAO {

    public void createNewCreditCard(CarteCredit newCard);
    public void createNewDebitCard(CarteDebit newCard);
    public void createNewPrepaidCard(CartePrepayee newCard);
    public List<Carte> getClientsCards(int clientId);
    public void blockCard(int cardId);
    public void suspendCard(int cardId);
    public Carte getCardById(int cardId);

}
