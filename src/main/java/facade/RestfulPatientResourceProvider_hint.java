package facade;

import ca.uhn.fhir.rest.annotation.IdParam;
import ca.uhn.fhir.rest.annotation.Read;
import ca.uhn.fhir.rest.annotation.ResourceParam;
import ca.uhn.fhir.rest.annotation.Update;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.server.IResourceProvider;
import ca.uhn.fhir.rest.server.exceptions.ResourceNotFoundException;
import dao.PatientDAO;
import dbService.DbService;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.Enumerations.AdministrativeGender;
import org.hl7.fhir.r4.model.IdType;
import org.hl7.fhir.r4.model.Patient;

public class RestfulPatientResourceProvider_hint implements IResourceProvider {


  private final DbService dbService;

  public RestfulPatientResourceProvider_hint(DbService dbService) {
    this.dbService = dbService;
  }

  @Override
  public Class<? extends IBaseResource> getResourceType() {
    return Patient.class;
  }

  /**
   * Simple implementation of the "read" method
   */
  @Read(version = false)
  public Patient read(@IdParam IdType theId) {

    //get the PatientDAO from the DB
    PatientDAO patientDAO = dbService.getPatientById(theId.getIdPart());
    if (patientDAO == null) {
      throw new ResourceNotFoundException(theId);
    }

    //CREATE FHIR Patient
    Patient patient = new Patient();
    patient.setId(patientDAO.getId());
    patient.addName().addGiven(patientDAO.getGiven()).setFamily(patientDAO.getName());
    patient.setGender(AdministrativeGender.fromCode(patientDAO.getGender()));

    return patient;
  }

  @Update
  public MethodOutcome update(@IdParam IdType theId, @ResourceParam Patient thePatient) {
    String resourceId = theId.getIdPart();

    //create PatientDAO
    PatientDAO patientDAO = new PatientDAO();

    //GenderToString
    String gender = "";
    switch (thePatient.getGender()) {
      case MALE:
        gender = "male";
        break;
      case FEMALE:
        gender = "female";
        break;
      case OTHER:
        gender = "other";
        break;
      case UNKNOWN:
        gender = "unknown";
        break;
    }
    patientDAO.setGender(gender);
    patientDAO.setId(resourceId);
    patientDAO.setGiven(thePatient.getNameFirstRep().getGivenAsSingleString());
    patientDAO.setName(thePatient.getNameFirstRep().getFamily());

    // ... perform the update ...
    dbService.addPatient(patientDAO);

    return new MethodOutcome();

  }
}
