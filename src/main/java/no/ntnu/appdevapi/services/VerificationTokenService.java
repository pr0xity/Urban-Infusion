package no.ntnu.appdevapi.services;

import no.ntnu.appdevapi.entities.User;
import no.ntnu.appdevapi.entities.VerificationToken;

/**
 * Business logic for verification tokens.
 */
public interface VerificationTokenService {

    /**
     * Returns the token from the newest verification token for the given user.
     *
     * @param user the user to find the newest token for.
     * @return the token.
     */
     String getTokenFromUser(User user);

    /**
     * Creates a verification token out of the given user and token and adds it.
     *
     * @param user the user to make verification token for.
     */
    void addVerificationToken(User user);

    /**
     * Returns the verification token object which contains the given token.
     *
     * @param token the token to find the verification token object of.
     * @return the verification token object which has the given token, null if not found.
     */
    VerificationToken getVerificationTokenByToken(String token);
}
