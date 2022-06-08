package no.ntnu.appdevapi.listeners;

import no.ntnu.appdevapi.entities.User;
import no.ntnu.appdevapi.events.CompleteRegistrationEvent;
import no.ntnu.appdevapi.services.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * Listener for complete registration event.
 */
@Component
public class CompleteRegistrationListener implements ApplicationListener<CompleteRegistrationEvent> {

    @Value("${spring.mail.username}")
    public String sender;

    @Autowired
    private VerificationTokenService verificationTokenService;

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void onApplicationEvent(CompleteRegistrationEvent event) {
        this.sendConfirmRegistration(event);
    }

    /**
     * Sends an email to the user with link to confirm registration.
     *
     * @param event the event of completing registration.
     */
    private void sendConfirmRegistration(CompleteRegistrationEvent event) {
        User user = event.getUser();
        verificationTokenService.addVerificationToken(user);

        String recipient = user.getEmail();

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper;
        try {
            messageHelper = new MimeMessageHelper(message, true);
            messageHelper.setSubject("Registration Confirmation - Urban Infusion");
            messageHelper.setFrom(sender);
            messageHelper.setTo(recipient);

            String content = createConfirmRegistrationMailContent(user);

            messageHelper.setText(content, true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        mailSender.send(message);
    }

    /**
     * Creates the mail content for registration confirmation email.
     *
     * @param user the user who is attempting to register.
     * @return html content of the registration confirmation mail.
     */
    private String createConfirmRegistrationMailContent(User user) {
        String confirmationUrl = "/api/confirm-registration?token=" + verificationTokenService.getTokenFromUser(user);
        String link = ("localhost:8080" + confirmationUrl);


        return "<h1 style='text-align: center;  color: #58555F; font-family: Montserrat, sans-serif; font-size: 24px'>Hi " + user.getFirstName() +"! Welcome to Urban Infusion!</h1>" +
                "<br>" +
                "<p style='text-align:center;'>Thank you for joining our quest to fuse ancient traditions and modern times</p>" +
                "<p style='text-align:center;'>Before you continue you need to active your account.</p>" +
                "<br>" +
                "<p style='text-align:center;'><a href='" + link + "' style='padding: 8px 16px; color: #fff; box-shadow: 0 5px 10px rgba(0, 0, 0, 0.15);" +
                "background-color: #4F744E; border-radius: 100px; border: 2px solid white; width: fit-content;" +
                "font-family: Montserrat, sans-serif; font-size: 16px; font-weight: 500; text-decoration: none; transition: all 0.4s ease-out;" +
                "cursor: pointer;'>Activate your account</a></p>";
    }
}
