package no.ntnu.appdevapi.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import no.ntnu.appdevapi.entities.PermissionLevel;
import no.ntnu.appdevapi.entities.User;
import no.ntnu.appdevapi.security.JwtUtil;
import no.ntnu.appdevapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.function.Predicate;

@Controller
@RequestMapping()
public class UserController {

  @Autowired
  private JwtUtil jwtUtil;

  @Autowired
  private UserService userService;

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
   * Get a specific user. All users can view themselves. Admins and owners can view all users.
   *
   * @param email The email of the user.
   * @return The user matching the email, null otherwise.
   */
  @GetMapping("/users/{email}")
  @ApiOperation(value = "Get a specific user.", notes = "Returns the user or null when email is invalid.")
  public ResponseEntity<User> get(@ApiParam("email of the user.") @PathVariable String email,
                                  @CookieValue(name = "token", defaultValue = "anonymous") String userToken) {

    // The default response when not logged in or attempting to view another user as a regular user.
    ResponseEntity<User> response = new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    // Attempts to read the jwt token embedded in the cookie:
    if (!userToken.equals("anonymous")) {
      // Authenticates the acting user:
      User actingUser = userService.findOneByEmail(jwtUtil.getUsernameFromToken(userToken));
      if (null != actingUser) {
        //Check if the acting user is an admin or an owner:
        Predicate<PermissionLevel> isAdmin = pl -> pl.getAdminType().equals("admin");
        Predicate<PermissionLevel> isOwner = pl -> pl.getAdminType().equals("owner");
        boolean adminLevelAuth = actingUser.getPermissionLevel().stream().anyMatch(isAdmin.or(isOwner));

        if (actingUser.getEmail().equals(email) || adminLevelAuth) {
          // Searches the database for the requested user:
          User requestedUser = userService.findOneByEmail(email);
          if (requestedUser != null) {
            // Successfully authorized the request, and returning the requested user.
            response = new ResponseEntity<>(requestedUser, HttpStatus.OK);
          } else {
            // Successfully authorized the request, but requested user does not exist.
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
          }
        }
      }
    }
    return response;
  }

  /**
   * Gets the info of the logged-in user.
   */
  @GetMapping("/user")
  public String getUser(Model model, @CookieValue(name = "token", defaultValue = "anonymous") String userToken) {
    String returnString = "not-logged-in-error-page";

    //Attempts to read the jwt token embedded in the cookie.
    if (!userToken.equals("anonymous")) {
      String username = jwtUtil.getUsernameFromToken(userToken);
      User user = userService.findOneByEmail(username);
      if (null != user) {
        returnString = "user";
        model.addAllAttributes(user.generateUserMap());
      }
    }
    return returnString;
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
    User requestedUser = userService.findOneByEmail(email);
    if (null != requestedUser) {
      userService.deleteUser(email);
      response = new ResponseEntity<>(HttpStatus.OK);
    }
    return response;
  }
}