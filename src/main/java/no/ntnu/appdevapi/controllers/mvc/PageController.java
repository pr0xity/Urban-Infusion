package no.ntnu.appdevapi.controllers.mvc;

import no.ntnu.appdevapi.entities.User;
import no.ntnu.appdevapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

/**
 * Abstract class for general methods used in page controllers.
 */
public abstract class PageController {

    @Autowired
    private UserService userService;

    /**
     * Retrieves permission level and adds to the given model.
     *
     * @param model model to add permission level to.
     */
    protected void addPermissionLevelToModel(Model model) {
        if(this.getUser() != null) {
            model.addAttribute("permission", this.getUser().getPermissionLevel().getAdminType());
        } else {
            model.addAttribute("permission", "NoUser");
        }
    }

    /**
     * Returns the user of this session.
     *
     * @return user of this session.
     */
    protected User getUser() {
        return this.userService.getSessionUser();
    }

}
