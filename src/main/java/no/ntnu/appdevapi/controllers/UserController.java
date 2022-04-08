package no.ntnu.appdevapi.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import no.ntnu.appdevapi.DTO.AuthToken;
import no.ntnu.appdevapi.DTO.LoginUser;
import no.ntnu.appdevapi.DTO.UserDto;
import no.ntnu.appdevapi.entities.User;
import no.ntnu.appdevapi.security.JwtUtil;
import no.ntnu.appdevapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.naming.AuthenticationException;
import java.util.List;


@RestController
@RequestMapping("users")
public class UserController {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private JwtUtil jwtUtil;

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

  /**
   * Returns all users in the store.
   *
   * @return List of all users.
   */
  @GetMapping
  @ApiOperation(value = "Get all users.")
  public List<User> getAll() {
    return userService.findAll();
  }

  /**
   * Get a specific user.
   *
   * @param email The email of the user.
   * @return The user matching the email, null otherwise.
   */
  @GetMapping("/{email}")
  @ApiOperation(value = "Get a specific user.", notes = "Returns the user or null when email is invalid.")
  public ResponseEntity<User> get(@ApiParam("email of the user.") @RequestHeader MultiValueMap<String, String> headers, @PathVariable String email) {
    ResponseEntity<User> response = new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    User user = userService.findOne(email);
    // TODO: check requesting user
    String token = headers.get("authorization").get(0).replace("Bearer ", "");
    String username = jwtUtil.getUsernameFromToken(token);
    String permissionLevel = jwtUtil.getAdminTypeFromToken(token);

    if (null != user && user.getEmail().equals(username) || permissionLevel.equals("admin") || permissionLevel.equals("owner")) {
      response = new ResponseEntity<>(user, HttpStatus.OK);
    } else if (null == user) {
      response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return response;
  }

  /**
   * Add a user to the store.
   *
   * @param user The user to add.
   * @return 200 when added, 400 on error.
   */
  @PostMapping()
  @ApiOperation(value = "Add a new user.", notes = "Status 200 when added, 400 on error.")
  public ResponseEntity<String> add(@RequestBody UserDto user) {
    ResponseEntity<String> response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    if (null != user && userService.findOne(user.getEmail()) == null) {
      userService.save(user);
      response = new ResponseEntity<>(HttpStatus.OK);
    }
    return response;
  }

  /**
   * Delete a user from the store
   *
   * @param email Email of the user to delete.
   * @return 200 when deleted, 404 if not.
   */
  @DeleteMapping("/{email}")
  @ApiIgnore
  public ResponseEntity<String> delete(@PathVariable String email) {
    ResponseEntity<String> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
    if (null != userService.findOne(email)) {
      userService.deleteUser(email);
      response = new ResponseEntity<>(HttpStatus.OK);
    }
    return response;
  }
}