package dbService;

import dao.PatientDAO;
import java.util.HashMap;
import java.util.Map;

public class DbService {

  private Map<String, PatientDAO> patients = new HashMap<>();

  public PatientDAO getPatientById(String id) {
    return patients.get(id);
  }

  public void addPatient(PatientDAO pat) {
    String id = pat.getId();
    patients.put(id, pat);
  }
}
