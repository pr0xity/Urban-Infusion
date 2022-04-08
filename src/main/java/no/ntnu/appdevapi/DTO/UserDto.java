package no.ntnu.appdevapi.DTO;

import no.ntnu.appdevapi.entities.PermissionLevel;
import no.ntnu.appdevapi.entities.User;

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

  public UserDto() {
  }

  public UserDto(String fname, String lname, String email, String password) {
    this.firstName = fname;
    this.lastName = lname;
    this.email = email;
    this.password = password;
    this.createdAt = LocalDateTime.now();
    this.updatedAt = null;
    this.enabled = true;
    this.permissionLevel = "user";

  }
  public UserDto(String fname, String lname, String email, String password, String permissionLevel) {
    this.firstName = fname;
    this.lastName = lname;
    this.email = email;
    this.password = password;
    this.createdAt = LocalDateTime.now();
    this.updatedAt = null;
    this.enabled = true;
    this.permissionLevel = permissionLevel;

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

    PermissionLevel p = new PermissionLevel(1, permissionLevel, 1, updatedAt);
    user.setPermissionLevel(p);

    return user;
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

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public String getPermissionLevel() {
    return permissionLevel;
  }

  public void setPermissionLevel(String permissionLevel) {
    this.permissionLevel = permissionLevel;
  }
}
