package service;

import dao.impl.CarteDAOImpl;
import dao.impl.ClientDAOImpl;
import entity.*;
import exception.NoCardsFoundException;
import exception.UserNotFoundException;
import utils.CardInformationGenerator;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class CarteService {

    private final CarteDAOImpl carteDAO;
    private final ClientDAOImpl clientDAO;

    public CarteService(CarteDAOImpl carteDAO, ClientDAOImpl clientDAO) {
        this.carteDAO = carteDAO;
        this.clientDAO = clientDAO;
    }


    public void createCreditCard(String clientEmail, Double plafondMensuel, float tauxInteret,Double solde) throws UserNotFoundException, SQLException {
        if (clientDAO.getClientId(clientEmail).isEmpty()) {
            throw new UserNotFoundException("This email does not belong to any user");
        }
        LocalDate expirationdate = CardInformationGenerator.generateCardExpirationDate("credit");
        StatutCarte statutCarte = StatutCarte.ACTIVE;
        int clienId = clientDAO.getClientId(clientEmail).get();
        String cardNumber = CardInformationGenerator.generateCardNumber();

        CarteCredit newCreditCard = new CarteCredit(cardNumber, expirationdate, statutCarte, clienId, plafondMensuel, tauxInteret,solde);
        carteDAO.createNewCreditCard(newCreditCard);
    }

    public void createDebitCard(String clientEmail, Double plafonJournalier,Double solde) throws UserNotFoundException {
        if (clientDAO.getClientId(clientEmail).isEmpty()) {
            throw new UserNotFoundException("This email does not belong to any user");
        }
        LocalDate expirationdate = CardInformationGenerator.generateCardExpirationDate("debit");
        StatutCarte statutCarte = StatutCarte.ACTIVE;
        int clienId = clientDAO.getClientId(clientEmail).get();
        String cardNumber = CardInformationGenerator.generateCardNumber();
        CarteDebit newDebitCard = new CarteDebit(cardNumber, expirationdate, statutCarte, clienId, plafonJournalier,solde);
        carteDAO.createNewDebitCard(newDebitCard);
    }

    public void createPrepaidCard(String clientEmail, Double soldeDisponible) throws UserNotFoundException {
        if (clientDAO.getClientId(clientEmail).isEmpty()) {
            throw new UserNotFoundException("This email does not belong to any user");
        }
        LocalDate expirationdate = CardInformationGenerator.generateCardExpirationDate("prepaid");
        StatutCarte statutCarte = StatutCarte.ACTIVE;
        int clienId = clientDAO.getClientId(clientEmail).get();
        String cardNumber = CardInformationGenerator.generateCardNumber();
        CartePrepayee newPrepaidCard = new CartePrepayee(cardNumber, expirationdate, statutCarte, clienId, soldeDisponible);
        carteDAO.createNewPrepaidCard(newPrepaidCard);

    }

    public void getClientsCards(String clientEmail) throws UserNotFoundException, NoCardsFoundException {
        Optional<Integer> clientIdOpt = clientDAO.getClientId(clientEmail);
        if (clientIdOpt.isEmpty()) {
            throw new UserNotFoundException("This email does not belong to any user");
        }
        int clientId = clientIdOpt.get();
        List<Carte> clientsCards = carteDAO.getClientsCards(clientId);
        if (clientsCards.isEmpty()) {
            throw new NoCardsFoundException("This client has no cards");
        }
        for (Carte carte : clientsCards){
            System.out.println(carte.toString());
        }
    }

    public void blockCard(int cardId) {
        carteDAO.blockCard(cardId);
        System.out.println("Card " + cardId + " has been blocked successfully");
    }

    public void suspendCard(int cardId) {
        carteDAO.suspendCard(cardId);
        System.out.println("Card " + cardId + " has been suspended successfully");
    }

    public Carte getCardById(int cardId) {
        return carteDAO.getCardById(cardId);
    }
}

