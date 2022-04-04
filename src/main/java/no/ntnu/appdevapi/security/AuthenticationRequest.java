package no.ntnu.appdevapi.security;

/**
 * Represents authentication data a user will send for a login request.
 *
 * @version 04.04.2022
 */
public class AuthenticationRequest {

    /**
     * Emailaddress provided by a user for authentication.
     */
    private String email;

    /**
     * Password provided by a user for authentication.
     */
    private String password;

    /**
     * Creates an instance of authentication request.
     */
    public AuthenticationRequest() {
    }

    /**
     * Creates an instance of authentication request.
     */
    public AuthenticationRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }


    /**
     * Returns email used for authentication of user.
     *
     * @return email used for authentication of user.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email used for authentication of user.
     *
     * @param email the emails to be set for authentication of user.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns password used for authentication of users.
     *
     * @return password used for authentication of user.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets password used for authentication of user.
     *
     * @param password the password to be set for authentication of user.
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
