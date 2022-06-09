package no.ntnu.appdevapi.DTO;

/**
 * Data transfer object for ratings.
 */
public class RatingDto {

  /**
   * This rating's displayed name.
   */
  private final String displayName;

  /**
   * This rating's user's email address.
   */
  private final String email;

  /**
   * This rating's rating.
   */
  private final int rating;

  /**
   * This rating's comment.
   */
  private final String comment;

  /**
   * Creates a DTO of rating.
   *
   * @param displayName the displayed name of the user's rating.
   * @param email       the user's email address.
   * @param rating      the int value of the rating.
   * @param comment     the comment of this rating.
   */
  public RatingDto(String displayName, String email, int rating, String comment) {
    this.displayName = displayName;
    this.email = email;
    this.rating = rating;
    this.comment = comment;
  }

  /**
   * Returns the displayed name of this rating.
   *
   * @return displayed name of this rating.
   */
  public String getDisplayName() {
    return displayName;
  }

  /**
   * Returns this rating's user's email-address.
   *
   * @return this rating's user's email-address.
   */
  public String getEmail() {
    return email;
  }

  /**
   * Returns this ratings rating.
   *
   * @return this ratings rating.
   */
  public int getRating() {
    return rating;
  }

  /**
   * Returns this ratings comment.
   *
   * @return this ratings comment.
   */
  public String getComment() {
    return comment;
  }
}
