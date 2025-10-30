package dao.impl;

import com.mysql.cj.xdevapi.Client;
import com.mysql.cj.xdevapi.PreparableStatement;
import dao.ClientDAO;
import entity.BankClient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientDAOImpl implements ClientDAO {
    private Connection connection;

    public ClientDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void createClient(BankClient newClient) {
        String sql = "Insert into clients(name,email,phone_number) values(?,?,?)";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1, newClient.name());
            stmt.setString(2, newClient.email());
            stmt.setString(3, newClient.phoneNumber());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Creating client failed.");
            }

        }catch(SQLException e){
            e.printStackTrace();

        }
    }

    @Override
    public Optional<BankClient> getClientByEmail(String email) {
        String sql = "SELECT * FROM clients WHERE email = ?";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1, email);
            try(ResultSet result = stmt.executeQuery()){
                if(result.next()){
                    BankClient client = new BankClient(
                            result.getInt("id"),
                            result.getString("name"),
                            result.getString("email"),
                            result.getString("phone_number")
                    );
                    return Optional.of(client);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<BankClient> getClientByName(String name) {
        String sql = "SELECT * from clients WHERE name = ?";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1 , name);
            try(ResultSet result = stmt.executeQuery()){
                BankClient client = new BankClient(
                        result.getInt("id"),
                        result.getString("name"),
                        result.getString("email"),
                        result.getString("phone_number")
                );
                return Optional.of(client);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return Optional.empty();
    }


    @Override
    public void updateClientName(String newName,String clientEmail) {
        String sql = "UPDATE clients SET name = ? WHERE email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, newName);
            stmt.setString(2, clientEmail);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Updating client name failed, no client found with email: " + clientEmail);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void updateClientEmail(String newEmail, String clientEmail) {
        String sql = "UPDATE clients SET email = ? WHERE email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, newEmail);
            stmt.setString(2, clientEmail);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Updating client email failed, no client found with email: " + clientEmail);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateClientPhoneNumber(String newPhoneNumber, String clientEmail) {
        String sql = "UPDATE clients SET phone_number = ? WHERE email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, newPhoneNumber);
            stmt.setString(2, clientEmail);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Updating client phoneNumber failed, no client found with email: " + clientEmail);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Integer> getClientId(String clientEmail) {
        String sql = "SELECT clientId from clients where email = ?";

        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1, clientEmail);
            try(ResultSet result = stmt.executeQuery()){
                if(result.next()){
                    return Optional.of(result.getInt("clientId"));
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return Optional.empty();
    }


    public Optional<List<BankClient>> getAllClients() {
        String sql = "SELECT * FROM clients";
        List<BankClient> clients = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            try (ResultSet result = stmt.executeQuery()) {
                while (result.next()) {
                    BankClient client = new BankClient(
                            result.getInt("clientId"),
                            result.getString("name"),
                            result.getString("email"),
                            result.getString("phone_number")
                    );
                    clients.add(client);
                }
                return Optional.of(clients);
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return Optional.empty();
    }
}
