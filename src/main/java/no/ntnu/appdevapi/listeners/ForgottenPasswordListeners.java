package no.ntnu.appdevapi.listeners;

import no.ntnu.appdevapi.DTO.UserDto;
import no.ntnu.appdevapi.entities.User;
import no.ntnu.appdevapi.events.ForgottenPasswordEvent;
import no.ntnu.appdevapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.Random;

/**
 * Listener for forgotten password event.
 */
@Component
public class ForgottenPasswordListeners implements ApplicationListener<ForgottenPasswordEvent> {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserService userService;

    @Value("${spring.mail.username}")
    public String SENDER;

    @Override
    public void onApplicationEvent(ForgottenPasswordEvent event) {
        sendForgottenPasswordMail(event);
    }

    /**
     * Sends the forgotten email to the user.
     *
     * @param event forgotten password event.
     */
    private void sendForgottenPasswordMail(ForgottenPasswordEvent event) {
        User user = event.getUser();
        String recipient = user.getEmail();

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = null;
        try {
            messageHelper = new MimeMessageHelper(message, true);
            messageHelper.setSubject("Forgotten password - Urban Infusion");
            messageHelper.setFrom(SENDER);
            messageHelper.setTo(recipient);

            String content = createForgottenPasswordMailContent(user);

            messageHelper.setText(content, true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        mailSender.send(message);
    }

    /**
     * Creates the content for forgotten password email to be sent to the given user.
     *
     * @param user the user to send the email to.
     * @return content of a forgotten email.
     */
    private String createForgottenPasswordMailContent(User user) {
        return "<h1 style='text-align: center;  color: #58555F; font-family: Montserrat, sans-serif; font-size: 24px'>Forgotten password</h1>" +
                "<br>" +
                "<p style='text-align:center;'>We have generated a password for you: " + generateAndSaveRandomPassword(user) + "</p>" +
                "<p style='text-align:center;'>Use this password and then change to another when you log in</p>" +
                "<br>";
    }

    /**
     * Generates and saves a new password for the user.
     *
     * @param user the user to generate a new password for.
     * @return the new password (not encrypted).
     */
    private String generateAndSaveRandomPassword(User user) {
        byte[] array = new byte[256];
        new Random().nextBytes(array);

        String password = new String(array, StandardCharsets.UTF_8).replaceAll("[^A-Za-z0-9]", "").substring(0, 10);

        UserDto userDto = new UserDto();
        userDto.setNewPassword(password);
        userService.updateWithUserDto(user.getId(), userDto);
        return password;
    }
}
