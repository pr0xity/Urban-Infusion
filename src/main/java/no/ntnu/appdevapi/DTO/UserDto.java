package no.ntnu.appdevapi.DTO;

import no.ntnu.appdevapi.entities.PermissionLevel;
import no.ntnu.appdevapi.entities.User;
import no.ntnu.appdevapi.entities.UserAddress;

import java.time.LocalDateTime;

public class UserDto {

  private String firstName;
  private String lastName;
  private String email;
  private String password;
  private String permissionLevel;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private boolean enabled;

  private String addressLine1;
  private String addressLine2;
  private int postalCode;
  private String city;
  private String country;
  private String phone;

  public UserDto() {
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
    this.enabled = false;
    this.permissionLevel = "user";

    this.addressLine1 = addressLine1;
    this.addressLine2 = null;
    this.postalCode = Integer.parseInt(postalCode);
    this.city = city;
    this.country = country;
    this.phone = phone;
  }
  public UserDto(String firstName, String lastName, String email,
                 String password, String addressLine1, String addressLine2,
                 String postalCode, String city, String country, String phone) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.password = password;
    this.createdAt = LocalDateTime.now();
    this.updatedAt = null;
    this.enabled = false;
    this.permissionLevel = "user";

    this.addressLine1 = addressLine1;
    this.addressLine2 = addressLine2;
    this.postalCode = Integer.parseInt(postalCode);
    this.city = city;
    this.country = country;
    this.phone = phone;
  }

  public UserDto(String firstName, String lastName, String email, String password, String permissionLevel) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.password = password;
    this.createdAt = LocalDateTime.now();
    this.updatedAt = null;
    this.enabled = false;
    this.permissionLevel = permissionLevel;
  }

  public UserDto(String firstName, String lastName, String email, String password) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.password = password;
    this.createdAt = LocalDateTime.now();
    this.updatedAt = null;
    this.enabled = false;
    this.permissionLevel = "user";
  }

  public User getUserFromDto() {
    User user = new User();
    user.setFirstName(firstName);
    user.setLastName(lastName);
    user.setEmail(email);
    user.setPassword(password);
    user.setCreatedAt(createdAt);
    user.setUpdatedAt(updatedAt);
    user.setEnabled(enabled);


    PermissionLevel p = new PermissionLevel(permissionLevel, updatedAt);
    user.setPermissionLevel(p);

    return user;
  }

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
}
