package no.ntnu.appdevapi.events;

import no.ntnu.appdevapi.entities.User;
import org.springframework.context.ApplicationEvent;

/**
 * Event for completing registration.
 */
public class CompleteRegistrationEvent extends ApplicationEvent {

    /**
     * User of this event.
     */
    private User user;

    /**
     * Creates an instance of complete registration event.
     *
     * @param user the user of this event.
     */
    public CompleteRegistrationEvent(User user) {
        super(user);
        this.user = user;
    }

    /**
     * Returns the user of this complete registration event.
     *
     * @return the user of this complete registration event.
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user of this complete registration event.
     *
     * @param user the user to be set for this complete registration event.
     */
    public void setUser(User user) {
        this.user = user;
    }
}
