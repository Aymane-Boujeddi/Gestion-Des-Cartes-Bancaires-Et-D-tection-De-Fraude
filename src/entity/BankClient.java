package entity;

public record BankClient(Integer ClientId, String name, String email, String phoneNumber) {

    public BankClient(String name,String email,String phoneNumber){
        this(null, name, email, phoneNumber);

    }

}
