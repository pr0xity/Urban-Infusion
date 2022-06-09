package no.ntnu.appdevapi.controllers.mvc;

import no.ntnu.appdevapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

  @Autowired
  private UserService userService;

  @GetMapping("")
  String getDashboard(Model model) {
    model.addAttribute("user", userService.getSessionUser());
    return "admin/dashboard";
  }

  @GetMapping("/customers")
  String getCustomers(Model model) {
    model.addAttribute("user", userService.getSessionUser());
    return "admin/customers";
  }

  @GetMapping("/orders")
  String getOrders(Model model) {
    model.addAttribute("user", userService.getSessionUser());
    return "admin/orders";
  }

  @GetMapping("/products")
  String getProducts(Model model) {
    model.addAttribute("user", userService.getSessionUser());
    return "admin/products";
  }

  @GetMapping("/reviews")
  String getReviews(Model model) {
    model.addAttribute("user", userService.getSessionUser());
    return "admin/reviews";
  }
}
