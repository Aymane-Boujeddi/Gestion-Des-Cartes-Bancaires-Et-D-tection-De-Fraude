import config.DatabaseConfig;
import dao.impl.AlerteDAOImpl;
import dao.impl.CarteDAOImpl;
import dao.impl.ClientDAOImpl;
import dao.impl.OperationDAOImpl;
import service.AlerteFraudeService;
import service.CarteService;
import service.ClientService;
import service.OperationService;
import utils.ConsoleUI;
import utils.Validation;

import java.sql.*;
import java.util.Scanner;

public class Main {
    public  static void main (String[] args){

        try{
            Scanner scanner = new Scanner(System.in);
            Validation validation = new Validation();
            Connection connection = DatabaseConfig.getConnection();

            AlerteDAOImpl alerteDAO = new AlerteDAOImpl(connection);
            CarteDAOImpl carteDAO = new CarteDAOImpl(connection);
            ClientDAOImpl clientDAO = new ClientDAOImpl(connection);
            OperationDAOImpl operationDAO = new OperationDAOImpl(connection);

            AlerteFraudeService alerteFraudeService = new AlerteFraudeService(alerteDAO);
            CarteService carteService = new CarteService(carteDAO,clientDAO);
            OperationService operationService = new OperationService(operationDAO);
            ClientService clientService = new ClientService(clientDAO);

            ConsoleUI consoleUI = new ConsoleUI(scanner,validation,alerteFraudeService,carteService,operationService,clientService);
            consoleUI.start();


        }catch (SQLException e){
            e.printStackTrace();
        }
        }
    }

