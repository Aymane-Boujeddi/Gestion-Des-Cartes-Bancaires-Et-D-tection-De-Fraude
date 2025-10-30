package dao;

import entity.AlerteFraude;
import entity.NiveauAlerte;

import java.util.List;

public interface AlerteDAO {

    void createAlert(AlerteFraude alert);
    List<AlerteFraude> getAllAlerts();
    List<AlerteFraude> getAlertsByLevel(NiveauAlerte niveau);

}
