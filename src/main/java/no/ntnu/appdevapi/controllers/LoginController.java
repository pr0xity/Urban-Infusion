package no.ntnu.appdevapi.controllers;

import no.ntnu.appdevapi.DTO.LoginUser;
import no.ntnu.appdevapi.DTO.UserDto;
import no.ntnu.appdevapi.security.JwtUtil;
import no.ntnu.appdevapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

  @Value("${domain.name}")
  private String host;

  @Value("${jwt.cookie.name}")
  private String cookie;

  @Autowired
  private JwtUtil jwtUtil;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private UserService userService;

  @RequestMapping(value = "/login", method = RequestMethod.POST)
  public ResponseEntity<?> generateToken(@RequestBody LoginUser loginUser) {
    return authenticate(loginUser.getEmail(), loginUser.getPassword());
  }

  @RequestMapping(value = "/register", method = RequestMethod.POST)
  public ResponseEntity<?> registerUser(@RequestBody UserDto nUser) {
    if (null != nUser && userService.findOneByEmail(nUser.getEmail()) == null) {
      nUser.setPermissionLevel("user");
      userService.save(nUser);
      return authenticate(nUser.getEmail(), nUser.getPassword());
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  private ResponseEntity<?> authenticate(String email, String password) {
    final Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    email,
                    password));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    final String token = jwtUtil.generateToken(authentication);

    ResponseCookie springCookie = ResponseCookie.from(cookie, token)
            .httpOnly(true)
            .secure(false)
            .path("/")
            .maxAge(12000)
            .domain(host)
            .build();

    return ResponseEntity
            .ok()
            .header(HttpHeaders.SET_COOKIE, springCookie.toString())
            .build();
  }
}
