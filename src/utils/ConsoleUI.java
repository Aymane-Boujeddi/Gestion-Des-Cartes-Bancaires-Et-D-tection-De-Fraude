package utils;

import entity.BankClient;
import exception.EmailAlreadyExistException;
import service.AlerteFraudeService;
import service.CarteService;
import service.ClientService;
import service.OperationService;

import java.util.Scanner;

public class ConsoleUI {
    private Scanner scanner;
    private Validation validation;
    private AlerteFraudeService alerteFraudeService;
    private CarteService carteService;
    private OperationService operationService;
    private ClientService clientService;

    public ConsoleUI(Scanner scanner, Validation validation, AlerteFraudeService alerteFraudeService, CarteService carteService, OperationService operationService, ClientService clientService) {
        this.scanner = scanner;
        this.validation = validation;
        this.alerteFraudeService = alerteFraudeService;
        this.carteService = carteService;
        this.operationService = operationService;
        this.clientService = clientService;
    }

    public void start(){
        while(true){
            System.out.println("\n===== Gestion Des Cartes Bancaires =====");
            System.out.println("1. Créer un client");
            System.out.println("2. Émettre une carte (débit, crédit, prépayée)");
            System.out.println("3. Effectuer une opération (achat, retrait, paiement en ligne)");
            System.out.println("4. Consulter l'historique d'une carte");
            System.out.println("5. Lancer une analyse des fraudes");
            System.out.println("6. Bloquer une carte");
            System.out.println("7. Suspendre une carte");
            System.out.println("8. Tout les Clients");

            System.out.println("0. Quitter");
            System.out.print("Votre choix: ");

            try {

                 int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice){
                    case 1:
                        createClient();
                        break;
                    case 2:
                        issueCard();
                        break;
                    case 3:
                        addOperation();
                        break;
                    case 4:
                        displayCardHistory();
                        break;
                    case 5:
                        runFraudAnalyse();
                        break;
                    case 6:
                        bloqueCard();
                        break;
                    case 7:
                        suspendCard();
                        break;
                    case 8:
                        getAllClients();
                        break;
                    case 0:
                        System.out.println("Au revoir!");
                        return;
                    default:
                        System.out.println("Choix invalide. Veuillez réessayer.");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Erreur: Entrée invalide. Veuillez entrer un nombre.");
                scanner.nextLine();
            }
        }

    }

    private void getAllClients(){
        try {
            System.out.println("\n===== Liste de tous les clients =====");
            clientService.getAllClients().stream().forEach(bankClient -> System.out.println(bankClient));

        } catch (Exception e) {
            System.out.println("Erreur lors de la récupération des clients : " + e.getMessage());
        }
    }

    private void createClient(){
    try{
        System.out.println("\n===== Creation d'un nouveau Client =====");
        System.out.println("Entrez le nom du nouveau client : ");
        String clientName = scanner.next();
        validation.validationString(clientName);
        System.out.println("Entrez l'email du nouveau client :");
        String clientEmail = scanner.next();
        validation.validationString(clientEmail);
        System.out.println("Entrez le numero de telephone du nouveau client : ");
        String clientPhoneNumber = scanner.next();
        validation.validationString(clientPhoneNumber);
        clientService.createNewClient(clientName,clientEmail,clientPhoneNumber);
    }catch(IllegalArgumentException | EmailAlreadyExistException e){
        System.out.println(e.getMessage());
    }



    }
    private void issueCard(){
        try {
            System.out.println("\n===== Émission d'une nouvelle carte =====");

            System.out.println("Entrez l'email du client pour associer la carte :");
            String clientEmail = scanner.next();
            scanner.nextLine();
            validation.validationString(clientEmail);

            System.out.println("\nChoisissez le type de carte :");
            System.out.println("1. Carte de Débit");
            System.out.println("2. Carte de Crédit");
            System.out.println("3. Carte Prépayée");
            System.out.print("Votre choix : ");

            int cardTypeChoice = scanner.nextInt();
            scanner.nextLine();

            switch (cardTypeChoice) {
                case 1:
                    createDebitCard(clientEmail);
                    break;
                case 2:
                    createCreditCard(clientEmail);
                    break;
                case 3:
                    createPrepaidCard(clientEmail);
                    break;
                default:
                    System.out.println("Choix invalide. Opération annulée.");
            }
        } catch (Exception e) {
            System.out.println("Erreur lors de l'émission de la carte : " + e.getMessage());
            scanner.nextLine();
        }
    }

    private void createDebitCard(String clientEmail) {
        try {
            System.out.println("\n=== Configuration Carte de Débit ===");
            System.out.println("Entrez le solde initial à charger :");
            Double soldeInitial = scanner.nextDouble();
            System.out.println("Entrez le plafond de retrait journalier :");
            Double plafondRetrait = scanner.nextDouble();
            scanner.nextLine();
            carteService.createDebitCard(clientEmail, plafondRetrait,soldeInitial);
            System.out.println("Carte de Débit émise avec succès!");
        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }

    private void createCreditCard(String clientEmail) {
        try {
            System.out.println("\n=== Configuration Carte de Crédit ===");

            System.out.println("Entrez le solde initial à charger :");
            Double soldeInitial = scanner.nextDouble();

            System.out.println("Entrez le plafond mensuel :");
            Double plafondMensuel = scanner.nextDouble();
            scanner.nextLine();

            System.out.println("Entrez le taux d'intérêt (%) :");
            float tauxInteret = scanner.nextFloat();
            scanner.nextLine();

            carteService.createCreditCard(clientEmail, plafondMensuel, tauxInteret,soldeInitial);
            System.out.println("Carte de Crédit émise avec succès!");
        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }

    private void createPrepaidCard(String clientEmail) {
        try {
            System.out.println("\n=== Configuration Carte Prépayée ===");

            System.out.println("Entrez le montant initial à charger :");
            Double montantInitial = scanner.nextDouble();
            scanner.nextLine();

            carteService.createPrepaidCard(clientEmail, montantInitial);
            System.out.println("Carte Prépayée émise avec succès!");
        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }

    private void addOperation() {
        try {
            System.out.println("\n===== Effectuer une opération =====");

            System.out.println("Entrez l'email du client :");
            String clientEmail = scanner.nextLine();
            validation.validationString(clientEmail);
            
            System.out.println("\nCartes disponibles pour ce client :");
            carteService.getClientsCards(clientEmail);
            
            System.out.println("\nEntrez l'ID de la carte à utiliser :");
            int cardId = scanner.nextInt();
            scanner.nextLine();
            
            System.out.println("\nEntrez le montant de l'opération :");
            double montant = scanner.nextDouble();
            scanner.nextLine();
            
            System.out.println("\nEntrez le lieu de l'opération :");
            String lieu = scanner.nextLine();
            validation.validationString(lieu);
            
            System.out.println("\nChoisissez le type d'opération :");
            System.out.println("1. ACHAT");
            System.out.println("2. RETRAIT");
            System.out.println("3. PAIEMENTENLIGNE");
            System.out.print("Votre choix : ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            entity.OperationType operationType;
            switch (choice) {
                case 1:
                    operationType = entity.OperationType.ACHAT;
                    break;
                case 2:
                    operationType = entity.OperationType.RETRAIT;
                    break;
                case 3:
                    operationType = entity.OperationType.PAIEMENTENLIGNE;
                    break;
                default:
                    System.out.println("Type d'opération invalide.");
                    return;
            }
            
            operationService.createNewOperation(montant, lieu, operationType, cardId);

        } catch (IllegalArgumentException e) {
            System.out.println("Erreur de validation : " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erreur lors de l'opération : " + e.getMessage());
        }
    }

    private void displayCardHistory() {
        try {
            System.out.println("\n===== Consulter l'historique d'une carte =====");
            System.out.println("Entrez l'email du client :");
            String clientEmail = scanner.nextLine();
            validation.validationString(clientEmail);
            
            System.out.println("\nCartes disponibles pour ce client :");
            carteService.getClientsCards(clientEmail);
            
            System.out.println("\nEntrez l'ID de la carte :");
            int cardId = scanner.nextInt();
            scanner.nextLine();
            
            operationService.displayCardHistory(cardId);
            
        } catch (Exception e) {
            System.out.println("Erreur lors de la consultation de l'historique : " + e.getMessage());
        }
    }
    
    private void runFraudAnalyse(){
        try {
            System.out.println("\n===== Analyse des fraudes =====");
            alerteFraudeService.runFraudAnalysis();
            
            System.out.println("\nVoulez-vous voir les alertes par niveau? (oui/non)");
            String response = scanner.nextLine();
            
            if ("oui".equalsIgnoreCase(response)) {
                System.out.println("Choisissez le niveau :");
                System.out.println("1. INFO");
                System.out.println("2. AVERTISSEMENT");
                System.out.println("3. CRITIQUE");
                System.out.print("Votre choix : ");
                
                int choice = scanner.nextInt();
                scanner.nextLine();
                
                switch (choice) {
                    case 1:
                        alerteFraudeService.displayAlertsByLevel(entity.NiveauAlerte.INFO);
                        break;
                    case 2:
                        alerteFraudeService.displayAlertsByLevel(entity.NiveauAlerte.AVERTISSEMENT);
                        break;
                    case 3:
                        alerteFraudeService.displayAlertsByLevel(entity.NiveauAlerte.CRITIQUE);
                        break;
                    default:
                        System.out.println("Choix invalide");
                }
            }
            
        } catch (Exception e) {
            System.out.println("Erreur lors de l'analyse : " + e.getMessage());
        }
    }
    
    private void bloqueCard(){
        try {
            System.out.println("\n===== Bloquer une carte =====");
            System.out.println("Entrez l'email du client :");
            String clientEmail = scanner.nextLine();
            validation.validationString(clientEmail);
            
            System.out.println("\nCartes disponibles pour ce client :");
            carteService.getClientsCards(clientEmail);
            
            System.out.println("\nEntrez l'ID de la carte à bloquer :");
            int cardId = scanner.nextInt();
            scanner.nextLine();
            
            carteService.blockCard(cardId);
            
        } catch (Exception e) {
            System.out.println("Erreur lors du blocage : " + e.getMessage());
        }

    }
    
    private void suspendCard() {
        try {
            System.out.println("\n===== Suspendre une carte =====");
            System.out.println("Entrez l'email du client :");
            String clientEmail = scanner.nextLine();
            validation.validationString(clientEmail);
            
            System.out.println("\nCartes disponibles pour ce client :");
            carteService.getClientsCards(clientEmail);
            
            System.out.println("\nEntrez l'ID de la carte à suspendre :");
            int cardId = scanner.nextInt();
            scanner.nextLine();
            
            carteService.suspendCard(cardId);
            
        } catch (Exception e) {
            System.out.println("Erreur lors de la suspension : " + e.getMessage());
        }
    }
}
