package no.ntnu.appdevapi.controllers;

import no.ntnu.appdevapi.entities.User;
import no.ntnu.appdevapi.security.JwtUtil;
import no.ntnu.appdevapi.services.UserAddressServiceImpl;
import no.ntnu.appdevapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
@RequestMapping("/admin")
public class AdminController {

  @Autowired
  private JwtUtil jwtUtil;

  @Autowired
  private UserService userService;

  @Autowired
  private UserAddressServiceImpl userAddressService;

  @GetMapping("/customers")
  String getCustomers(Model model, @RequestHeader MultiValueMap<String, String> headers) {
    String token = headers.get("authorization").get(0);
    model.addAttribute("token", token);

    List<User> users = userService.findAll();
    int i = 0;
    for (User user : users) {

    }
    return "admin/customers";
  }
}
