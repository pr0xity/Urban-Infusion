package no.ntnu.appdevapi.controllers.rest;

import java.io.IOException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import no.ntnu.appdevapi.DTO.LoginUser;
import no.ntnu.appdevapi.DTO.UserDto;
import no.ntnu.appdevapi.entities.ShoppingSession;
import no.ntnu.appdevapi.entities.User;
import no.ntnu.appdevapi.entities.VerificationToken;
import no.ntnu.appdevapi.entities.Wishlist;
import no.ntnu.appdevapi.events.CompleteRegistrationEvent;
import no.ntnu.appdevapi.events.ForgottenPasswordEvent;
import no.ntnu.appdevapi.security.JwtUtil;
import no.ntnu.appdevapi.services.ShoppingSessionService;
import no.ntnu.appdevapi.services.UserService;
import no.ntnu.appdevapi.services.VerificationTokenService;
import no.ntnu.appdevapi.services.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Endpoint controller for user authentication.
 */
@CrossOrigin
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

  @Autowired
  private ShoppingSessionService shoppingSessionService;

  @Autowired
  private WishlistService wishlistService;

  @Autowired
  private ApplicationEventPublisher applicationEventPublisher;

  @Autowired
  private VerificationTokenService verificationTokenService;

  /**
   * Authenticates existing user
   *
   * @param loginUser LoginUser DTO containing login info (email & password)
   * @return ResponseEntity containing the jwt token in a cookie and HttpStatus ok on success,
   * or HttpStatus not found on fail.
   */
  @RequestMapping(value = "api/login", method = RequestMethod.POST)
  public ResponseEntity<?> generateToken(@RequestBody LoginUser loginUser) {
    User user = userService.findOneByEmail(loginUser.getEmail());
    if (user == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    if (!user.isEnabled()) {
      return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
    return authenticate(loginUser.getEmail(), loginUser.getPassword());
  }

  /**
   * Logs off the user.
   *
   * @param response response with cookie which expires instantly and redirects user to frontpage.
   */
  @RequestMapping(value = "api/logout", method = RequestMethod.GET)
  public void deleteTokenCookie(HttpServletResponse response) throws IOException {
    Cookie cookie = new Cookie("token", null);
    cookie.setMaxAge(0);
    cookie.setSecure(true);
    cookie.setHttpOnly(true);
    cookie.setPath("/");
    cookie.setDomain(host);
    response.addCookie(cookie);
    response.sendRedirect("/");
  }

  /**
   * Registers and send registration confirmation email to the registered user.
   *
   * @param nUser UserDto DTO containing necessary information about the new user.
   * @return ResponseEntity containing the jwt token in a cookie and HttpStatus ok on success,
   * or HttpStatus not found on fail.
   */
  @RequestMapping(value = "/api/register", method = RequestMethod.POST)
  public ResponseEntity<String> registerUser(@RequestBody UserDto nUser) {
    // If registration data is missing.
    if (nUser == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    // If user already exists.
    if (userService.findOneByEmail(nUser.getEmail()) != null) {
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    nUser.setPermissionLevel("user");
    nUser.setEnabled(false);
    User user = userService.save(nUser);
    shoppingSessionService.addShoppingSession(new ShoppingSession(user));
    wishlistService.addWishlist(new Wishlist(user));
    applicationEventPublisher.publishEvent(new CompleteRegistrationEvent(user));

    //verification token is only placed here for testing outside of email
    return new ResponseEntity<>(verificationTokenService.getTokenFromUser(user), HttpStatus.OK);
  }

  /**
   * Sends an email to the user given.
   *
   * @param userDto the user who has forgotten password.
   * @return 200 Ok on success, 404 not found if user was not found.
   */
  @RequestMapping(value = "api/forgotten-password", method = RequestMethod.POST)
  public ResponseEntity<?> forgottenPassword(@RequestBody UserDto userDto) {
    if (userDto == null || userDto.getEmail() == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    User user = userService.findOneByEmail(userDto.getEmail());
    if (user != null) {
      applicationEventPublisher.publishEvent(new ForgottenPasswordEvent(user));
      return new ResponseEntity<>(HttpStatus.OK);
    }

    return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
  }

  /**
   * Checks if given verification token is valid and enables and redirect user accordingly.
   *
   * @param token the verification token.
   * @return redirect user to home page and authenticates the user.
   */
  @RequestMapping(value = "api/confirm-registration", method = RequestMethod.GET)
  public ResponseEntity<?> confirmRegistration(HttpServletResponse response,
                                               @RequestParam("token") String token)
          throws IOException {
    VerificationToken verificationToken =
            verificationTokenService.getVerificationTokenByToken(token);

    if (verificationToken == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    if (verificationToken.hasTokenExpired()) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    User user = verificationToken.getUser();
    user.setEnabled(true);
    userService.updateWithUser(user.getId(), user);
    response.sendRedirect("/");
    return new ResponseEntity<>(HttpStatus.OK);
  }

  /**
   * Authenticates a user with given credentials.
   *
   * @param email    the email of the user.
   * @param password the password of the user.
   * @return HttpResponseCode OK with jwt cookie included if successful.
   */
  private ResponseEntity<?> authenticate(String email, String password) {
    final Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    email,
                    password));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    final String token = jwtUtil.generateToken(authentication);

    ResponseCookie springCookie = ResponseCookie.from(cookie, token)
            .httpOnly(true)
            .secure(true)
            .sameSite("None")
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
