package no.ntnu.appdevapi.services;

import no.ntnu.appdevapi.DAO.VerificationTokenRepository;
import no.ntnu.appdevapi.entities.User;
import no.ntnu.appdevapi.entities.VerificationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerificationTokenServiceImpl implements VerificationTokenService {

  @Autowired
  private VerificationTokenRepository verificationTokenRepository;

  /**
   * Returns the most recent verification token belonging to given user.
   *
   * @param user the user to find the newest token for.
   * @return {@code String} most recent verification token belonging to given user.
   */
  @Override
  public String getTokenFromUser(User user) {
    return verificationTokenRepository.findFirstByUser(user).getToken();
  }

  /**
   * Creates a new verification token for the given user, and adds it to the database.
   *
   * @param user the user to make verification token for.
   */
  @Override
  public void addVerificationToken(User user) {
    VerificationToken verificationToken = new VerificationToken(user);
    verificationTokenRepository.save(verificationToken);
  }

  /**
   * Gets the VerificationToken object with the given token string.
   *
   * @param verificationToken {@code String} to search for.
   * @return {@code VerificationToken} with given token string, or null if no match is found.
   */
  @Override
  public VerificationToken getVerificationTokenByToken(String verificationToken) {
    return verificationTokenRepository.findByToken(verificationToken);
  }
}
