package utils;

public class Validation {



    public void validationInteger(int integer) throws IllegalArgumentException{


    }
    public void validationString(String stringField) throws IllegalArgumentException {
        if(stringField.isEmpty()){
            throw new IllegalArgumentException("Ce champ est obligatoire");
        }
    }
}
