package no.ntnu.appdevapi.DTO;

import java.time.LocalDateTime;
import no.ntnu.appdevapi.entities.PermissionLevel;
import no.ntnu.appdevapi.entities.User;
import no.ntnu.appdevapi.entities.UserAddress;

/**
 * Data transfer object for a user
 */
public class UserDto {

  //The first name of a user
  private String firstName;
  //The last name of a user
  private String lastName;
  //The email of a user
  private String email;
  //The users password
  private String password;
  //The new password of a user
  private String newPassword;
  //A users permission-level
  private String permissionLevel;
  //When the user was created
  private LocalDateTime createdAt;
  //The last time the user information was updated
  private LocalDateTime updatedAt;
  //Whether the user is enabled or not
  private boolean enabled;
  //The users main address
  private String addressLine1;
  //The users secondary address
  private String addressLine2;
  //The users postal code
  private int postalCode;
  //The users city of residence
  private String city;
  //The users country of residence
  private String country;
  //The users phone number
  private String phone;

  //Empty constructor
  public UserDto() {
  }

  /**
   * Creates an instance of a UserDto. Several constructors are provided for when different data is available.
   *
   * @param firstName    The first name of a user
   * @param lastName     The last name of a user
   * @param email        The email of a user
   * @param password     The users password
   * @param addressLine1 The users main address
   * @param addressLine2 The users secondary address
   * @param postalCode   The users postal code
   * @param city         The users city of residence
   * @param country      The users country of residence
   * @param phone        The users phone number
   */
  public UserDto(String firstName, String lastName, String email,
                 String password, String addressLine1, String addressLine2,
                 String postalCode, String city, String country, String phone) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.password = password;
    this.createdAt = LocalDateTime.now();
    this.updatedAt = null;
    this.permissionLevel = "user";
    this.addressLine1 = addressLine1;
    this.addressLine2 = addressLine2;
    this.postalCode = Integer.parseInt(postalCode);
    this.city = city;
    this.country = country;
    this.phone = phone;
  }

  public UserDto(String firstName, String lastName, String email,
                 String password, String addressLine1, String postalCode,
                 String city, String country, String phone) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.password = password;
    this.createdAt = LocalDateTime.now();
    this.updatedAt = null;
    this.permissionLevel = "user";
    this.addressLine1 = addressLine1;
    this.addressLine2 = null;
    this.postalCode = Integer.parseInt(postalCode);
    this.city = city;
    this.country = country;
    this.phone = phone;
  }

  public UserDto(String firstName, String lastName, String email, String password,
                 String permissionLevel) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.password = password;
    this.createdAt = LocalDateTime.now();
    this.updatedAt = null;
    this.permissionLevel = permissionLevel;
  }

  public UserDto(String firstName, String lastName, String email, String password) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.password = password;
    this.createdAt = LocalDateTime.now();
    this.updatedAt = null;
    this.permissionLevel = "user";
  }

  /**
   * Creates and returns a User-object from the DTO
   *
   * @return a User-object
   */
  public User getUserFromDto() {
    User user = new User();
    user.setFirstName(firstName);
    user.setLastName(lastName);
    user.setEmail(email);
    user.setPassword(password);
    user.setCreatedAt(createdAt);
    user.setUpdatedAt(updatedAt);

    PermissionLevel p = new PermissionLevel(permissionLevel, updatedAt);
    user.setPermissionLevel(p);

    return user;
  }

  /**
   * Creates and returns a UserAddress-Object containing information about a user's residency.
   *
   * @return a UserAddress-object
   */
  public UserAddress getAddressFromDto() {
    if (null != addressLine1) {
      UserAddress address = new UserAddress();
      address.setAddressLine1(addressLine1);
      address.setAddressLine2(addressLine2);
      address.setPostalCode(postalCode);
      address.setCity(city);
      address.setCountry(country);
      address.setPhone(phone);
      return address;
    }
    return null;
  }

  //Getters and setters:

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getNewPassword() {
    return newPassword;
  }

  public void setNewPassword(String newPassword) {
    this.newPassword = newPassword;
  }

  public String getPermissionLevel() {
    return permissionLevel;
  }

  public void setPermissionLevel(String permissionLevel) {
    this.permissionLevel = permissionLevel;
  }

  public String getAddressLine1() {
    return addressLine1;
  }

  public void setAddressLine1(String addressLine1) {
    this.addressLine1 = addressLine1;
  }

  public String getAddressLine2() {
    return addressLine2;
  }

  public void setAddressLine2(String addressLine2) {
    this.addressLine2 = addressLine2;
  }

  public int getPostalCode() {
    return postalCode;
  }

  public void setPostalCode(int postalCode) {
    this.postalCode = postalCode;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }
}
