package no.ntnu.appdevapi.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import no.ntnu.appdevapi.DTO.UserDto;
import no.ntnu.appdevapi.entities.PermissionLevel;
import no.ntnu.appdevapi.entities.User;
import no.ntnu.appdevapi.security.JwtUtil;
import no.ntnu.appdevapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

@RestController
@RequestMapping()
public class UserController {

  @Autowired
  private JwtUtil jwtUtil;

  @Autowired
  private UserService userService;

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

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
   * Returns the five most recently created users.
   *
   * @return List of the five newest users.
   */
  @RequestMapping(value = "/users/new", method = RequestMethod.GET, produces = "application/json")
  @ApiOperation(value = "Get newly created users.")
  @ResponseBody
  public List<User> getNew() {
    List<User> users = userService.findAll();
    users.sort(Comparator.comparing(User::getCreatedAt).reversed());
    int k = users.size();
    if (k > 5) {
      users.subList(5,k).clear();
    }
    return users;
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

        boolean adminLevelAuth = actingUser.getPermissionLevels().stream().anyMatch(isAdmin.or(isOwner));

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
   * Updates the user with the given user id with the given user dto object.
   *
   * @param userId the user id of the user to be updated.
   * @param userDto the user dto to update to.
   * @return 200 Ok if updated, 401 if not.
   */
  @PutMapping("/users/{userId}")
  public ResponseEntity<?> update(@PathVariable long userId, @RequestBody UserDto userDto, HttpServletResponse response) throws IOException {
    System.out.println("tullball");
    User currentUser = userService.getSessionUser();
    User userToUpdate = userService.findOneByID(userId);

    Predicate<PermissionLevel> isAdmin = pl -> pl.getAdminType().equals("admin");
    Predicate<PermissionLevel> isOwner = pl -> pl.getAdminType().equals("owner");
    boolean adminLevelAuth = currentUser.getPermissionLevels().stream().anyMatch(isAdmin.or(isOwner));

    if ((currentUser.getId() == userId  || adminLevelAuth) && userToUpdate != null) {
      if (userDto.getNewPassword() != null && !bCryptPasswordEncoder.matches(userDto.getPassword(), userToUpdate.getPassword())) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }
      if (userDto.getEmail() != null && userToUpdate.getId() == currentUser.getId()) {
        if (bCryptPasswordEncoder.matches(userDto.getPassword(), currentUser.getPassword())) {

          userService.updateWithUserDto(userToUpdate.getId(), userDto);
          Cookie cookie = deleteCookie();
          response.addCookie(cookie);
          return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
      }
      if (!userDto.isEnabled()) {
        if (adminLevelAuth || (currentUser.getId() == userId && bCryptPasswordEncoder.matches(userDto.getPassword(), currentUser.getPassword()))) {
          userService.updateWithUserDto(userToUpdate.getId(), userDto);
          Cookie cookie = deleteCookie();
          response.addCookie(cookie);
          return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
      }

      userService.updateWithUserDto(userToUpdate.getId(), userDto);
      return new ResponseEntity<>(HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
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

  /**
   * Creates a "token" cookie which expires instantly.
   *
   * @return "token" cookie which expires instantly.
   */
  private Cookie deleteCookie() {
    Cookie cookie = new Cookie("token", null);
    cookie.setMaxAge(0);
    cookie.setSecure(true);
    cookie.setHttpOnly(true);
    cookie.setPath("/");

    return cookie;
  }
}