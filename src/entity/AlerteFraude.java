package entity;

public record  AlerteFraude(Integer alerteId,String description,NiveauAlerte niveauAlerte) {
    public AlerteFraude(String description,NiveauAlerte niveauAlerte){
        this(null,description,niveauAlerte);
    }
}
