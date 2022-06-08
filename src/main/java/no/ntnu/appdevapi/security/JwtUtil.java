package no.ntnu.appdevapi.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * This class handles JWT-tokens by analyzing them, extracting data and creating new tokens
 */
@Component
public class JwtUtil {

  @Value("${jwt.token.validity}")
  public long TOKEN_VALIDITY;

  @Value("${jwt.signing.key}")
  public String SIGNING_KEY;

  @Value("${jwt.authorities.key}")
  public String AUTHORITIES_KEY;

  /**
   * Gets the username from the JWT token.
   * @param token the JWT token
   * @return the username as {@code String}
   */
  public String getUsernameFromToken(String token) {
    return getClaimFromToken(token, Claims::getSubject);
  }

  public String getAdminTypeFromToken(String token) {
    return getClaimFromToken(token, Claims::getSubject);
  }

  /**
   * Gets the expiration date from the JWT token.
   * @param token the JWT token
   * @return a date object with the expiration date
   */
  public Date getExpirationDateFromToken(String token) {
    return getClaimFromToken(token, Claims::getExpiration);
  }


  public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = getAllClaimsFromToken(token);
    return claimsResolver.apply(claims);
  }

  /**
   * Returns all claims in a JWT token
   * @param token the JWT token to get claims from
   * @return all claims in the token
   */
  private Claims getAllClaimsFromToken(String token) {
    return Jwts.parser()
      .setSigningKey(SIGNING_KEY)
      .parseClaimsJws(token)
      .getBody();
  }

  /**
   * Checks if a JWT token is expired.
   * @param token the JWT token to check.
   * @return {@code true} if token has expired, {@code false} if not.
   */
  private Boolean isTokenExpired(String token) {
    final Date expiration = getExpirationDateFromToken(token);
    return expiration.before(new Date());
  }

  public String generateToken(Authentication authentication) {
    String authorities = authentication.getAuthorities().stream()
      .map(GrantedAuthority::getAuthority)
      .collect(Collectors.joining(","));

    return Jwts.builder()
      .setSubject(authentication.getName())
      .claim(AUTHORITIES_KEY, authorities)
      .setIssuedAt(new Date(System.currentTimeMillis()))
      .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY*1000))
      .signWith(SignatureAlgorithm.HS256, SIGNING_KEY)
      .compact();
  }

  /**
   * Validate the given token with the given user.
   * @param token the JWT token to validate.
   * @param userDetails the details of the user.
   * @return {@code true} if the token is valid and matches the user.
   */
  public Boolean validateToken(String token, UserDetails userDetails) {
    final String username = getUsernameFromToken(token);
    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }

  public UsernamePasswordAuthenticationToken getAuthenticationToken(final String token,
                                                             final Authentication existingAuth,
                                                             final UserDetails userDetails) {

    final JwtParser jwtParser = Jwts.parser().setSigningKey(SIGNING_KEY);

    final Jws<Claims> claimsJws = jwtParser.parseClaimsJws(token);

    final Claims claims = claimsJws.getBody();

    final Collection<? extends GrantedAuthority> authorities =
        Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
          .map(SimpleGrantedAuthority::new)
          .collect(Collectors.toList());

    return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
  }

}

