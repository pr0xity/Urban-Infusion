package no.ntnu.appdevapi.user;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;
  @ApiModelProperty("First name of the user")
  private String firstName;
  @ApiModelProperty("Last name of the user")
  private String lastName;
  @ApiModelProperty("The users email-address")
  private String email;
  @ApiModelProperty("Password of the user")
  private String password;
  @ApiModelProperty("Date and time of the creation of the user")
  private LocalDateTime createdAt;
  @ApiModelProperty("Date and time of the last update of user info")
  private LocalDateTime updatedAt;
  @ApiModelProperty("The permission-ID of the user")
  private Integer permissionID;
  @ApiModelProperty("If the user is enabled or not")
  private boolean enabled;

  public User(String firstName, String lastName, String email, String password) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.password = password;
    this.createdAt = LocalDateTime.now();
    this.updatedAt = LocalDateTime.now();
    this.permissionID = 0;
    this.enabled = true;
  }

  public User(String firstName, String lastName, String email, String password,
              Integer permissionID) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.password = password;
    this.permissionID = permissionID;
    this.createdAt = LocalDateTime.now();
    this.updatedAt = LocalDateTime.now();
    this.enabled = true;
  }

  public User() {}

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
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

  public Integer getPermissionID() {
    return permissionID;
  }

  public void setPermissionID(Integer permissionID) {
    this.permissionID = permissionID;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }
}
