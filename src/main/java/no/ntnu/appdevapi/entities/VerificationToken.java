package no.ntnu.appdevapi.entities;

import io.swagger.annotations.ApiModelProperty;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * Represents a verification token.
 */
@Entity
public class VerificationToken {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @ApiModelProperty("The registered user to verify")
  @OnDelete(action = OnDeleteAction.CASCADE)
  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "user_id")
  private User user;

  @ApiModelProperty("The verification token")
  private String token;

  @ApiModelProperty("When the verification token expires")
  private Date expirationDate;

  /**
   * Creates an instance of verification token, sets the user, generates token and
   * sets expiration date to 2 days.
   *
   * @param user the user to make verification token for.
   */
  public VerificationToken(User user) {
    this.user = user;
    this.token = generateToken();
    this.expirationDate = calculateExpirationDate(60 * 48);
  }

  /**
   * Empty constructor.
   */
  public VerificationToken() {

  }

  /**
   * Calculates the expiration date with the given minutes from the current date.
   *
   * @param expiringTimeInMinutes the time until the token expires.
   * @return the date the token expires.
   */
  private Date calculateExpirationDate(int expiringTimeInMinutes) {
    // From baeldung tutorial
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(new Timestamp(calendar.getTime().getTime()));
    calendar.add(Calendar.MINUTE, expiringTimeInMinutes);
    return new Date(calendar.getTime().getTime());
  }

  /**
   * Returns the id of this verification token.
   *
   * @return id of this verification token.
   */
  public long getId() {
    return id;
  }

  /**
   * Returns the user of this verification token.
   *
   * @return user of this verification token.
   */
  public User getUser() {
    return user;
  }

  /**
   * Generates a unique token.
   *
   * @return the token.
   */
  public String generateToken() {
    return UUID.randomUUID().toString();
  }

  /**
   * Returns this verification token object's token.
   *
   * @return the token of this verification token object.
   */
  public String getToken() {
    return token;
  }

  /**
   * Returns true if the token has expired, false if not.
   *
   * @return true if the token has expired, false if not.
   */
  public boolean hasTokenExpired() {
    return expirationDate.before(new Date(System.currentTimeMillis()));
  }
}
