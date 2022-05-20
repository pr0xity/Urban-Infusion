package no.ntnu.appdevapi.events;

import no.ntnu.appdevapi.entities.User;
import org.springframework.context.ApplicationEvent;

/**
 * Event for when user has forgotten password.
 */
public class ForgottenPasswordEvent extends ApplicationEvent {

    /**
     * User who has forgotten password.
     */
    private User user;

    /**
     * Creates an instance of forgotten password event.
     *
     * @param user the user who has forgotten password.
     */
    public ForgottenPasswordEvent(User user) {
        super(user);
        this.user = user;
    }

    /**
     * Returns the user who has forgotten their password.
     *
     * @return the user who has forgotten their password.
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user who has forgotten their password.
     *
     * @param user the user to be set for this event.
     */
    public void setUser(User user) {
        this.user = user;
    }
}
