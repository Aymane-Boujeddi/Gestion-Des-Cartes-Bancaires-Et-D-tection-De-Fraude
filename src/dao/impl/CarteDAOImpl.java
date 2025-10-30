package dao.impl;

import dao.CarteDAO;
import entity.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CarteDAOImpl implements CarteDAO {
    private Connection connection;

    public CarteDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void createNewCreditCard(CarteCredit newCard) {
        String sql = "INSERT into cards(cardNumber,expirationDate,cardStatus,cardType,clientId,plafondMensuel,tauxInteret,solde) values(?,?,?,?,?,?,?,?)";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1,newCard.getCarteNumber());
            stmt.setDate(2, Date.valueOf(newCard.getExpirationDate()));
            stmt.setString(3,newCard.getStatutCarte().name());
            stmt.setString(4,"CREDIT");
            stmt.setInt(5,newCard.getClientId());
            stmt.setDouble(6,newCard.getPlafondMensuel());
            stmt.setFloat(7,newCard.getTauxInteret());
            stmt.setDouble(8, newCard.getSoldeDisponible());
            int rowsAffected = stmt.executeUpdate();
            if(rowsAffected == 0){
                throw new SQLException("CreditCard creation failed");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void createNewDebitCard(CarteDebit newCard) {
        String sql = "INSERT into cards(cardNumber,expirationDate,cardStatus,cardType,clientId,plafondJournalier,solde) values(?,?,?,?,?,?,?)";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1,newCard.getCarteNumber());
            stmt.setDate(2, Date.valueOf(newCard.getExpirationDate()));
            stmt.setString(3,newCard.getStatutCarte().name());
            stmt.setString(4,"DEBIT");
            stmt.setInt(5,newCard.getClientId());
            stmt.setDouble(6,newCard.getPlafondJournalier());
            stmt.setDouble(7, newCard.getSoldeDisponible());
            int rowsAffected = stmt.executeUpdate();
            if(rowsAffected == 0){
                throw new SQLException("DebitCard creation failed");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void createNewPrepaidCard(CartePrepayee newCard) {
        String sql = "INSERT into cards(cardNumber,expirationDate,cardStatus,cardType,clientId,solde) values(?,?,?,?,?,?)";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1,newCard.getCarteNumber());
            stmt.setDate(2, Date.valueOf(newCard.getExpirationDate()));
            stmt.setString(3,newCard.getStatutCarte().name());
            stmt.setString(4,"PREPAYEE");
            stmt.setInt(5,newCard.getClientId());
            stmt.setDouble(6,newCard.getSoldeDisponible());
            int rowsAffected = stmt.executeUpdate();
            if(rowsAffected == 0){
                throw new SQLException("PrepaidCard creation failed");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }


    public List<Carte> getClientsCards(int clientId) {
        String sql = "SELECT * FROM cards WHERE clientId = ?";
        List<Carte> clientsCards = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, clientId);

            try (ResultSet result = stmt.executeQuery()) {
                while (result.next()) {
                    Optional<Carte> carte = createCarteFromResultSet(result, clientId);
                    carte.ifPresent(clientsCards::add);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clientsCards;
    }

    private Optional<Carte> createCarteFromResultSet(ResultSet result, int clientId) {
        try {
            int cardId = result.getInt("cardId");
            String cardNumber = result.getString("cardNumber");
            LocalDate expirationDate = result.getDate("expirationDate").toLocalDate();
            StatutCarte cardStatus = StatutCarte.valueOf(result.getString("cardStatus"));
            String cardType = result.getString("cardType");
            Double solde = result.getDouble("solde");

            switch (cardType) {
                case "CREDIT":
                    Double plafondMensuel = result.getDouble("plafondMensuel");
                    Float tauxInteret = result.getFloat("tauxInteret");
                    return Optional.of(new CarteCredit(cardId, cardNumber, expirationDate, cardStatus, clientId, plafondMensuel, tauxInteret,solde));

                case "DEBIT":
                    Double plafondJournalier = result.getDouble("plafondJournalier");
                    return Optional.of(new CarteDebit(cardId, cardNumber, expirationDate, cardStatus, clientId, plafondJournalier,solde));

                case "PREPAYEE":
                    return Optional.of(new CartePrepayee(cardId, cardNumber, expirationDate, cardStatus, clientId, solde));

                default:
                    return Optional.empty();
            }
        } catch (SQLException e) {
            System.err.println("Error creating card from ResultSet: " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public void blockCard(int cardId) {
        String sql = "UPDATE cards SET cardStatus = 'BLOQUE' WHERE cardId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, cardId);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Card blocking failed, no card found with ID: " + cardId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void suspendCard(int cardId) {
        String sql = "UPDATE cards SET cardStatus = 'SUSPENDUE' WHERE cardId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, cardId);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Card suspension failed, no card found with ID: " + cardId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Carte getCardById(int cardId) {
        String sql = "SELECT * FROM cards WHERE cardId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, cardId);
            try (ResultSet result = stmt.executeQuery()) {
                if (result.next()) {
                    Optional<Carte> carte = createCarteFromResultSet(result, result.getInt("clientId"));
                    return carte.orElse(null);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
