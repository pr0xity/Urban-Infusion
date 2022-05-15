package no.ntnu.appdevapi.controllers;

import no.ntnu.appdevapi.DTO.OrderDetailsDto;
import no.ntnu.appdevapi.entities.*;
import no.ntnu.appdevapi.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * REST API controller for orders.
 */
@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    private JavaMailSender mailSender;
    
    @Autowired
    private OrderDetailsService orderDetailsService;
    
    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private ShoppingSessionService shoppingSessionService;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private UserService userService;

    /**
     * Get all order details.
     *
     * @return list of all order details.
     */
    @GetMapping
    public List<OrderDetails> getAll() {
        return this.orderDetailsService.getAllOrderDetails();
    }

    /**
     * Retrieves the order details with the given id.
     *
     * @param orderId id of the order details to get.
     * @return order details with the given id and 200 OK, or 404 Not found on failure.
     */
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDetails> getOne(@PathVariable Integer orderId) {
        ResponseEntity<OrderDetails> response;
        OrderDetails orderDetails = this.orderDetailsService.getOrderDetails(orderId);

        if (orderDetails != null) {
            response = new ResponseEntity<>(orderDetails, HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return response;
    }

    /**
     * Creates an adds an order of the existing shopping session.
     *
     * @return 201 Created on success, 400 Bad request on failure.
     */
    @PostMapping
    public ResponseEntity<String> add() throws MessagingException {
        ResponseEntity<String> response;

        if (getUser() != null && !getShoppingSession().getCart().isEmpty() && getUser() == getShoppingSession().getUser()) {
            //Create and add order details.
            OrderDetails orderDetails = convertShoppingSessionToOrderDetails(getShoppingSession());
            this.orderDetailsService.addOrderDetails(orderDetails);

            //Convert cart items to order items (linked to the order details) and add them.
            Iterable<OrderItem> orderItems = this.convertCartItemSetToOrderItemSet(orderDetails, getShoppingSession().getCart());
            this.orderItemService.addAllOrderItems(orderItems);

            //Send order confirmation email and empty shopping session.
            this.sendOrderConfirmation(orderDetails, orderItems);
            this.clearShoppingSession();
            response = new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return response;
    }

    /**
     * Updates the order details which has the given id by the given order details.
     *
     * @param orderId the id of the order details to update.
     * @param orderDetailsDto the order details to update with.
     * @return 200 OK on success, 400 Bad request on failure.
     */
    @PutMapping("/{orderId}")
    public ResponseEntity<String> update(@PathVariable long orderId, @RequestBody OrderDetailsDto orderDetailsDto) {
        ResponseEntity<String> response;

        OrderDetails orderDetails = orderDetailsService.getOrderDetails(orderId);
        User user = userService.findOneByEmail(orderDetailsDto.getEmail());

        if ( doesOrderExist(orderId, user) ) {
            orderDetails.setTotal(orderDetailsDto.getTotal());
            orderDetails.setQuantity(orderDetailsDto.getQuantity());
            orderDetails.setProcessed(orderDetailsDto.isProcessed());
            this.orderDetailsService.update(orderId, orderDetails);
            response = new ResponseEntity<>(HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return response;
    }

    /**
     * Deletes the order details which has the given id.
     *
     * @param orderId the id of the order details to be deleted.
     * @return 200 OK on success, 404 Not found on failure.
     */
    @DeleteMapping("/{orderId}")
    public ResponseEntity<String> delete(@PathVariable long orderId) {
        ResponseEntity<String> response;

        if (this.orderDetailsService.getOrderDetails(orderId) != null) {
            this.orderDetailsService.deleteOrderDetails(orderId);
            response = new ResponseEntity<>(HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return response;
    }

    /**
     * Converts the given set of cart items to a set of order items.
     *
     * @param orderDetails the order details associated with the order items.
     * @param cartItems set of cart items to be ordered.
     * @return set of order items.
     */
    private Set<OrderItem> convertCartItemSetToOrderItemSet(OrderDetails orderDetails, Set<CartItem> cartItems) {
        Set<OrderItem> orderItems = new HashSet<>();

        for(CartItem cartItem : cartItems) {
            orderItems.add( new OrderItem(orderDetails, cartItem.getProduct(), cartItem.getQuantity()) );
        }

        return orderItems;
    }

    /**
     * Converts the shopping session into order details.
     *
     * @param shoppingSession the shopping session to convert
     * @return order details instance with the same field values as the shopping session given.
     */
    private OrderDetails convertShoppingSessionToOrderDetails(ShoppingSession shoppingSession) {
        return new OrderDetails(shoppingSession.getUser(), shoppingSession.getTotal(), shoppingSession.getQuantity());
    }

    /**
     * Returns the shopping session for the current user.
     *
     * @return shopping session for the current user.
     */
    private ShoppingSession getShoppingSession() {
        return this.shoppingSessionService.getShoppingSessionByUser(getUser());
    }

    /**
     * Updates total and quantity fields in the current shopping session and persists the changes.
     */
    private void clearShoppingSession() {
        this.cartItemService.deleteAllCartItemsInShoppingSession(getShoppingSession());
        this.getShoppingSession().getCart().clear();
        getShoppingSession().updateQuantity();
        getShoppingSession().updateTotal();
        shoppingSessionService.update(getShoppingSession().getId(), getShoppingSession());
    }

    /**
     * Checks if an order with the given order id and given user exists.
     *
     * @param orderId the id of the order to check if exists.
     * @param user the user of the order to check if exists.
     * @return true if an order with the id and user exists, false if not.
     */
    private boolean doesOrderExist(long orderId, User user) {
        OrderDetails orderDetails = orderDetailsService.getOrderDetails(orderId);
        return  orderDetails != null && orderDetails.getId() == orderId && this.orderDetailsService.getOrderDetails(orderId) != null && orderDetails.getUser() == user ;
    }

    /**
     * Returns the user of this session.
     *
     * @return user of this session.
     */
    private User getUser() {
        return this.userService.getSessionUser();
    }

    /**
     * Sends order confirmation email to the customer/user.
     *
     * @param orderDetails the order details of the users order.
     * @param orderItems the ordered items of the users order.
     */
    private void sendOrderConfirmation(OrderDetails orderDetails, Iterable<OrderItem> orderItems) {
        String from = "UrbanInfusionTea@gmail.com";
        String to = this.getUser().getEmail();

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = null;
        try {
            messageHelper = new MimeMessageHelper(message, true);
            messageHelper.setSubject("Order Confirmation - Urban Infusion");
            messageHelper.setFrom(from);
            messageHelper.setTo(to);

            String content = createOrderConfirmationMailContent(orderDetails, orderItems);

            messageHelper.setText(content, true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        mailSender.send(message);
    }

    /**
     * Creates the content in an order confirmation email using the order details given and order items given.
     *
     * @param orderDetails the order details for the order confirmation.
     * @param orderItems the ordered items for the order confirmation.
     * @return html of the email content of the order confirmation.
     */
    private String createOrderConfirmationMailContent(OrderDetails orderDetails, Iterable<OrderItem> orderItems) {
        StringBuilder content = new StringBuilder("<h1>Dear " + getUser().getFirstName() + " " + getUser().getLastName() + ",</h1><p>Your order has been received and will swiftly be processed. Thank you for supporting Urban Infusion</p>" +
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
                "    <th style='border-bottom: 1px solid #999;'>Size</th>" +
                "    <th style='border-bottom: 1px solid #999;'>Quantity</th>" +
                "    <th style='border-bottom: 1px solid #999;'>Price (NOK)</th>" +
                "  </tr>");
        for (OrderItem orderItem : orderItems) {
            content.append("<tr>" +
                            "<td style='border-bottom: 1px solid #ddd;'>").append(orderItem.getProduct().getId()).append("</td>")
                    .append("<td style='border-bottom: 1px solid #ddd;'>").append(orderItem.getProduct().getName()).append("</td>")
                    .append("<td style='border-bottom: 1px solid #ddd;'>").append(orderItem.getProduct().getSize()).append("g").append("</td>")
                    .append("<td style='border-bottom: 1px solid #ddd;'>").append(orderItem.getQuantity()).append("</td>")
                    .append("<td style='border-bottom: 1px solid #ddd;'>").append(orderItem.getTotal()).append(",-").append("</td>")
                    .append("</tr>");
        }
        content.append("<tr><td></td><td></td><td></td><td></td><td style='line-height: 2;'><strong>Total: </strong>").append(orderDetails.getTotal()).append(",-").append("</td></tr>").append("</table>");

        return String.valueOf(content);
    }
}
