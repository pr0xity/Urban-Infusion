package no.ntnu.appdevapi.controllers;

import no.ntnu.appdevapi.DTO.AuthToken;
import no.ntnu.appdevapi.DTO.LoginUser;
import no.ntnu.appdevapi.DTO.UserDto;
import no.ntnu.appdevapi.security.JwtUtil;
import no.ntnu.appdevapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;

@RestController
public class LoginController {

  @Autowired
  private JwtUtil jwtUtil;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private UserService userService;

  @RequestMapping(value = "/login", method = RequestMethod.POST)
  public ResponseEntity<?> generateToken(@RequestBody LoginUser loginUser) throws AuthenticationException {
    final Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    loginUser.getEmail(),
                    loginUser.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    final String token = jwtUtil.generateToken(authentication);
    return ResponseEntity.ok(new AuthToken(token));
  }

  @RequestMapping(value = "/register", method = RequestMethod.POST)
  public ResponseEntity<?> registerUser(@RequestBody UserDto nUser) {
    if (null != nUser && userService.findOne(nUser.getEmail()) == null) {
      nUser.setPermissionLevel("user");
      userService.save(nUser);
      final Authentication authentication = authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(
                      nUser.getEmail(),
                      nUser.getPassword()));
      SecurityContextHolder.getContext().setAuthentication(authentication);
      final String token = jwtUtil.generateToken(authentication);
      return ResponseEntity.ok(new AuthToken(token));
    }
    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
  }
}
