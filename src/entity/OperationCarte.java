package entity;

import java.time.LocalDate;

public record OperationCarte(Integer operationId,LocalDate dateOperation,Double montant,OperationType operationType, String lieu , int cardId ) {
    // if you are creating a new Operation use the construct without id because the Db is using an auto_increment for id , and if you try to retrieve it from the DB use the construct with the id so that the Object is complete
    public OperationCarte (LocalDate dateOperation,Double montant,OperationType operationType,String lieu , int cardId){
        this(null,dateOperation,montant,operationType,lieu,cardId);
    }
}
