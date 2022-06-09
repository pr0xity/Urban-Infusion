package no.ntnu.appdevapi.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

public class SecurityServletFilter extends OncePerRequestFilter {

  @Value("${jwt.cookie.name}")
  public String TOKEN;

  @Resource(name = "userService")
  private UserDetailsService userDetailsService;

  @Autowired
  private JwtUtil jwtTokenUtil;

  @Override
  protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res,
                                  FilterChain chain) throws IOException, ServletException {
    String token = null;
    if (null != req.getCookies()) {
      //Attempt to extract jwt token from cookie:
      token = extractToken(req);
    }
    String username = null;
    //if cookie was found and jwt token was successfully extracted:
    if (null != token) {
      try {
        username = jwtTokenUtil.getUsernameFromToken(token);
      } catch (IllegalArgumentException e) {
        logger.error("An error occurred while fetching Username from Token", e);
      } catch (ExpiredJwtException e) {
        logger.warn("The token has expired", e);
      } catch (SignatureException e) {
        logger.error("Authentication Failed. Username or Password not valid.");
      }
    } else {
      logger.warn("Couldn't find cookie, anonymous user");
    }

    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

      UserDetails userDetails = userDetailsService.loadUserByUsername(username);

      if (jwtTokenUtil.validateToken(token, userDetails)) {
        UsernamePasswordAuthenticationToken authentication =
                jwtTokenUtil.getAuthenticationToken(
                        token, SecurityContextHolder.getContext().getAuthentication(), userDetails);
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
        logger.info("authenticated user " + username + ", setting security context");
        logger.info("email: " + userDetails.getUsername());
        logger.info("password: " + userDetails.getPassword());
        userDetails.getAuthorities()
                .forEach(authority -> logger.info("Permission level: " + authority));
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    }
    chain.doFilter(req, res);
  }

  private String extractToken(HttpServletRequest request) {
    Optional<String> optionalToken =
            Arrays.stream(request.getCookies()).filter(cookie -> TOKEN.equals(cookie.getName()))
                    .map(Cookie::getValue).findAny();
    return optionalToken.orElse(null);
  }
}