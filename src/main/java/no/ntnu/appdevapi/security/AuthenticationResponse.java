package no.ntnu.appdevapi.security;

/**
 * Represents authentication response for successful login.
 */
public class AuthenticationResponse {

    private final String jwt;

    public AuthenticationResponse(String jwt) {
        this.jwt = jwt;
    };

    public String getJwt() {
        return jwt;
    }
}
