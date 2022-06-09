package no.ntnu.appdevapi.controllers.mvc;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * MVC-controller for error page.
 */
@Controller
public class ErrorController extends PageController
        implements org.springframework.boot.web.servlet.error.ErrorController {

  /**
   * Returns the error page with necessary info.
   *
   * @param model   model for error page.
   * @param request request to get error status of.
   * @return error page displaying error message according to error status.
   */
  @GetMapping("/error")
  public String error(Model model, HttpServletRequest request) {
    addPermissionLevelToModel(model);
    Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

    if (status != null) {
      int statusCode = Integer.parseInt(status.toString());
      model.addAttribute("status", statusCode);
    }
    return "error";
  }
}
