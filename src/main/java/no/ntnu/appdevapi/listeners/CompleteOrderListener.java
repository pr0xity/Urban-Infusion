package no.ntnu.appdevapi.listeners;

import no.ntnu.appdevapi.entities.OrderDetails;
import no.ntnu.appdevapi.entities.OrderItem;
import no.ntnu.appdevapi.entities.User;
import no.ntnu.appdevapi.events.CompleteOrderEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * Listener for complete order event.
 */
@Component
public class CompleteOrderListener implements ApplicationListener<CompleteOrderEvent> {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    public String SENDER;

    @Override
    public void onApplicationEvent(CompleteOrderEvent event) {
        this.sendOrderConfirmation(event);
    }

    /**
     * Sends order confirmation email to the customer/user.
     *
     * @param event when an order is completed.
     */
    private void sendOrderConfirmation(CompleteOrderEvent event) {
        User user = event.getUser();
        OrderDetails orderDetails = event.getOrderDetails();
        Iterable<OrderItem> orderItems = event.getOrderItems();

        String recipient = user.getEmail();

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = null;
        try {
            messageHelper = new MimeMessageHelper(message, true);
            messageHelper.setSubject("Order Confirmation - Urban Infusion");
            messageHelper.setFrom(SENDER);
            messageHelper.setTo(recipient);

            String content = createOrderConfirmationMailContent(user, orderDetails, orderItems);

            messageHelper.setText(content, true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        mailSender.send(message);
    }

    /**
     * Creates the content in an order confirmation email using the order details given and order items given.
     *
     * @param user the user of this order.
     * @param orderDetails the order details for the order confirmation.
     * @param orderItems the ordered items for the order confirmation.
     * @return html of the email content of the order confirmation.
     */
    private String createOrderConfirmationMailContent(User user, OrderDetails orderDetails, Iterable<OrderItem> orderItems) {
        StringBuilder content = new StringBuilder("<h1>Dear " + user.getFirstName() + " " + user.getLastName() + ",</h1><p>Your order has been received and will swiftly be processed. Thank you for supporting Urban Infusion</p>" +
                "<br> <br>" +
                "<table style=' margin: 0 auto;' width='700'>\n" +
                "  <tr style='display:flex;'>\n" +
                "    <td width='250'>" +
                "     <p style='font-weight: bold; line-height: 0;'>Order number:</p><p>" + orderDetails.getId() + "</p><br>" +
                "     <p style='font-weight: bold; line-height: 0;'>Order date:</p><p>" + orderDetails.getCreatedAt().toLocalDate() + "</p><br>" +
                "     <p style='font-weight: bold; line-height: 0;'>Payment method:</p><p>Coupon</p><br>" +
                "     <p style='font-weight: bold; line-height: 0;'>Delivery method:</p><p>Home-delivery</p><br>" +
                "    </td>\n" +
                "    <td width='250'>" +
                "     <p style='font-weight: bold; line-height: 0;'>Full name:</p><p>" + orderDetails.getUser().getFirstName() + " " + orderDetails.getUser().getLastName() + "</p><br>" +
                "     <p style='font-weight: bold; line-height: 0;'>Email-address:</p><p>" + orderDetails.getUser().getEmail() + "</p><br>" +
                "     <p style='font-weight: bold; line-height: 0;'>Phone-number:</p><p>" + orderDetails.getUser().getAddress().getPhone() + "</p><br>" +
                "     <p style='font-weight: bold; line-height: 0;'>Home-address:</p><p>" + orderDetails.getUser().getAddress().getAddressLine() + "</p><br>" +
                "    </td>\n" +
                "  </tr>\n" +
                "</table>" +
                "<br> <br>" +
                "<table style=' margin: 0 auto;' width='700'>" +
                "  <tr style='text-align: left; border-bottom: 1px solid black;'>" +
                "    <th><h3>Items</h3></th>" +
                "  </tr>" +
                "  <tr style='text-align: left; border-bottom: 1px solid black;'>" +
                "    <th style='border-bottom: 1px solid #999;'>Art. nr</th>" +
                "    <th style='border-bottom: 1px solid #999;'>Product</th>" +
                "    <th style='border-bottom: 1px solid #999;'>Quantity</th>" +
                "    <th style='border-bottom: 1px solid #999;'>Price (NOK)</th>" +
                "  </tr>");
        for (OrderItem orderItem : orderItems) {
            content.append("<tr>" +
                            "<td style='border-bottom: 1px solid #ddd;'>").append(orderItem.getProduct().getId()).append("</td>")
                    .append("<td style='border-bottom: 1px solid #ddd;'>").append(orderItem.getProduct().getName()).append("</td>")
                    .append("<td style='border-bottom: 1px solid #ddd;'>").append(orderItem.getQuantity()).append("</td>")
                    .append("<td style='border-bottom: 1px solid #ddd;'>").append(orderItem.getTotal()).append(",-").append("</td>")
                    .append("</tr>");
        }
        content.append("<tr><td></td><td></td><td></td><td style='line-height: 2;'><strong>Total: </strong>").append(orderDetails.getTotal()).append(",-").append("</td></tr>").append("</table>");

        return String.valueOf(content);
    }
}
