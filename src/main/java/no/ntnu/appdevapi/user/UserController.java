package no.ntnu.appdevapi.user;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;


@RestController
@RequestMapping("users")
public class UserController {

  @Autowired
  private UserService userService;


  /**
   * Returns all users in the store.
   *
   * @return List of all users.
   */
  @GetMapping
  @ApiOperation(value = "Get all users.")
  public List<User> getAll() {
    return userService.getAllUsers();
  }

  /**
   * Get a specific user.
   *
   * @param index The index of the user, starting from 0.
   * @return The user matching the index, null otherwise.
   */
  @GetMapping("/{index}")
  @ApiOperation(value = "Get a specific user.", notes = "Returns the user or null when index is invalid.")
  public ResponseEntity<User> get(@ApiParam("Index of the user.") @PathVariable int index) {
    ResponseEntity<User> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
    User user = userService.getUser(index);
    if (null != user) {
      response = new ResponseEntity<>(user, HttpStatus.OK);
    }
    return response;
  }

  /**
   * Add a user to the store.
   *
   * @param user The user to add.
   * @return 200 when added, 400 on error.
   */
  @PostMapping
  @ApiOperation(value = "Add a new user.", notes = "Status 200 when added, 400 on error.")
  public ResponseEntity<String> add(@RequestBody User user) {
    ResponseEntity<String> response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    if (null != user) {
      userService.addUser(user);
      response = new ResponseEntity<>(HttpStatus.OK);
    }
    return response;
  }

  /**
   * Delete a user from the store
   *
   * @param index Index of the user to delete.
   * @return 200 when deleted, 404 if not.
   */
  @DeleteMapping("/{index}")
  @ApiIgnore
  public ResponseEntity<String> delete(@PathVariable int index) {
    ResponseEntity<String> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
    if (null != userService.getUser(index)) {
      userService.deleteUser(index);
      response = new ResponseEntity<>(HttpStatus.OK);
    }
    return response;
  }
}
