package no.ntnu.appdevapi.DTO;

/**
 * Data transfer object for user authentication.
 */
public class LoginUser {
  //The email (username) of a user
  private String email;
  //The password of a user
  private String password;


  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }
}
