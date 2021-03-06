package no.ntnu.appdevapi.listeners;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import no.ntnu.appdevapi.entities.OrderDetails;
import no.ntnu.appdevapi.entities.User;
import no.ntnu.appdevapi.events.ProcessedOrderEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

/**
 * Listener for processed order event.
 */
@Component
public class ProcessedOrderListener implements ApplicationListener<ProcessedOrderEvent> {

  @Autowired
  private JavaMailSender mailSender;

  @Value("${spring.mail.username}")
  public String SENDER;

  @Override
  public void onApplicationEvent(ProcessedOrderEvent event) {
    sendOrderIsProcessedMail(event);
  }

  /**
   * Sends an email to the user notifying about their order being processed.
   *
   * @param event when an order is processed.
   */
  private void sendOrderIsProcessedMail(ProcessedOrderEvent event) {
    OrderDetails orderDetails = event.getOrderDetails();
    User user = event.getOrderDetails().getUser();

    String recipient = user.getEmail();

    MimeMessage message = mailSender.createMimeMessage();
    MimeMessageHelper messageHelper;
    try {
      messageHelper = new MimeMessageHelper(message, true);
      messageHelper.setSubject("Your order has been processed - Urban Infusion");
      messageHelper.setFrom(SENDER);
      messageHelper.setTo(recipient);

      String content = createOrderIsProcessedMailContent(user, orderDetails);

      messageHelper.setText(content, true);
    } catch (MessagingException e) {
      e.printStackTrace();
    }

    mailSender.send(message);
  }

  /**
   * Creates the mail content of an order is processed mail.
   *
   * @param user         the user whose order is processed.
   * @param orderDetails the details of the order being processed.
   * @return the formatted mail content of an order is processed mail.
   */
  private String createOrderIsProcessedMailContent(User user, OrderDetails orderDetails) {
    return "<h1 style='text-align: center;  color: #58555F; font-family: Montserrat, sans-serif; font-size: 24px'>Your order has been processed!</h1>" +
            "<br>" +
            "<p style='text-align:center;'>Hi " + user.getFirstName() + "! Your order (order nr. " +
            orderDetails.getId() + ") has been processed and is on its way</p>" +
            "<p style='text-align:center;'>Your order should arrive between 3-4 business days.</p>" +
            "<br>" +
            "<p style='text-align:center;'>Thank you for supporting Urban Infusion!</p>";
  }
}
