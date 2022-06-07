package no.ntnu.appdevapi.controllers.mvc;

import no.ntnu.appdevapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
public class ErrorController extends PageController implements org.springframework.boot.web.servlet.error.ErrorController {

    @GetMapping("/error")
    public String error(Model model, HttpServletRequest request) throws IOException {
        model.addAttribute("user", getUser());
        addPermissionLevelToModel(model);
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            model.addAttribute("status", statusCode);
        }
        return "error";
    }
}
