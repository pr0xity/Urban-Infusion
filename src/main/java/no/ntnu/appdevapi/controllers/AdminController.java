package no.ntnu.appdevapi.controllers;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

  @GetMapping("/customers")
  String getCustomers() {
    return "admin/customers";
  }

  @GetMapping("/dashboard")
  String getDashboard() {
    return "admin/dashboard";
  }

  @GetMapping("/orders")
  String getOrders() {
    return "admin/orders";
  }

  @GetMapping("/products")
  String getProducts() {
    return "admin/products";
  }
}
