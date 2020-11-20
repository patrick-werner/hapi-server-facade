package dao;

public class PatientDAO {

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getGiven() {
    return given;
  }

  public void setGiven(String given) {
    this.given = given;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  private String id;
  private String given;
  private String name;
  private String gender;

}
