package utils;

import java.time.LocalDate;
import java.util.Random;

public class CardInformationGenerator {
    public static String generateCardNumber(){
        Random random = new Random();
        StringBuilder cardNumber = new StringBuilder();

        for(int  i=0; i<16; i++){
            cardNumber.append(random.nextInt(10));
        }

        return cardNumber.toString();

    }

    public static LocalDate generateCardExpirationDate(String cardType){
        LocalDate expirationDate = LocalDate.now();

        switch (cardType.toLowerCase()){
            case "credit":
                expirationDate = expirationDate.plusYears(3);
                break;
            case "debit":
                expirationDate = expirationDate.plusYears(4);
                break;
            case "prepayee":
                expirationDate = expirationDate.plusYears(2);
                break;
            default:
                System.out.println("This choice is not a valid Cardtype");
        }
        return expirationDate;
    }


}
