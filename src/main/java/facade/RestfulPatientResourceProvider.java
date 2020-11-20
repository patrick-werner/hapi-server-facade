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

public class RestfulPatientResourceProvider implements IResourceProvider {


  private final DbService dbService;

  public RestfulPatientResourceProvider(DbService dbService) {
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


    //CREATE FHIR Patient
    Patient patient = new Patient();

    return patient;
  }

  @Update
  public MethodOutcome update(@IdParam IdType theId, @ResourceParam Patient thePatient) {
    String resourceId = theId.getIdPart();

    //create PatientDAO
    PatientDAO patientDAO = new PatientDAO();


    dbService.addPatient(patientDAO);

    return new MethodOutcome();

  }
}
