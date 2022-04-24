package no.ntnu.appdevapi.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import no.ntnu.appdevapi.DTO.UserDto;
import no.ntnu.appdevapi.entities.User;
import no.ntnu.appdevapi.security.JwtUtil;
import no.ntnu.appdevapi.services.UserAddressService;
import no.ntnu.appdevapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Controller
@RequestMapping()
public class UserController {

  @Autowired
  private JwtUtil jwtUtil;

  @Autowired
  private UserService userService;

  @Autowired
  private UserAddressService userAddressService;

  /**
   * Returns all users in the store.
   *
   * @return List of all users.
   */
  @RequestMapping(value = "/users", method = RequestMethod.GET, produces = "application/json")
  @ApiOperation(value = "Get all users.")
  @ResponseBody
  public List<User> getAll() {
    return userService.findAll();
  }

  /**
   * Get a specific user.
   *
   * @param email The email of the user.
   * @return The user matching the email, null otherwise.
   */
  @GetMapping("/users/{email}")
  @ApiOperation(value = "Get a specific user.", notes = "Returns the user or null when email is invalid.")
  public ResponseEntity<User> get(@ApiParam("email of the user.") @RequestHeader MultiValueMap<String, String> headers, @PathVariable String email) {
    ResponseEntity<User> response = new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    User user = userService.findOneByEmail(email);

    String token = headers.get("authorization").get(0).replace("Bearer ", "");
    String username = jwtUtil.getUsernameFromToken(token);
    String permissionLevel = jwtUtil.getAdminTypeFromToken(token);

    if (null != user) {
      if (email.equals(username) || permissionLevel.equals("admin") || permissionLevel.equals("owner")) {
        response = new ResponseEntity<>(user, HttpStatus.OK);
      }
    } else {
      if (permissionLevel.equals("admin") || permissionLevel.equals("owner")) {
        response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
    }
    return response;
  }

  @GetMapping("/user")
  public String getUser(Model model, @RequestHeader MultiValueMap<String, String> headers) {
    String returnString = "not-logged-in-error-page";

    String token = headers.get("authorization").get(0).replace("Bearer ", "");
    String username = jwtUtil.getUsernameFromToken(token);
    User user = userService.findOneByEmail(username);
    if (null != user) {
      returnString = "user";
      model.addAllAttributes(user.getUserMap());
    }
    return returnString;
  }

  /**
   * Add a user to the store.
   *
   * @param user The user to add.
   * @return 200 when added, 400 on error.
   */
  @PostMapping("/users")
  @ApiOperation(value = "Add a new user.", notes = "Status 200 when added, 400 on error.")
  public ResponseEntity<String> add(@RequestBody UserDto user) {
    ResponseEntity<String> response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    if (null != user && userService.findOneByEmail(user.getEmail()) == null) {
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
  @DeleteMapping("/users/{email}")
  @ApiIgnore
  public ResponseEntity<String> delete(@PathVariable String email) {
    ResponseEntity<String> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
    if (null != userService.findOneByEmail(email)) {
      userService.deleteUser(email);
      response = new ResponseEntity<>(HttpStatus.OK);
    }
    return response;
  }
}