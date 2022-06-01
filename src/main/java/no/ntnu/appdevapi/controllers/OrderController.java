package no.ntnu.appdevapi.controllers;

import io.swagger.annotations.ApiOperation;
import no.ntnu.appdevapi.entities.*;
import no.ntnu.appdevapi.events.CompleteOrderEvent;
import no.ntnu.appdevapi.events.ProcessedOrderEvent;
import no.ntnu.appdevapi.services.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.*;

/**
 * REST API controller for orders.
 */
@RestController
@RequestMapping("api/orders")
public class OrderController {
    
    @Autowired
    private OrderDetailsService orderDetailsService;
    
    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ShoppingSessionService shoppingSessionService;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private UserService userService;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

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
     * Returns the five most recently created orders.
     *
     * @return List of the five newest orders.
     */
    @RequestMapping(value = "/recent", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation(value = "Get the five most recent orders.")
    @ResponseBody
    public List<OrderDetails> getRecent() {
        List<OrderDetails> orders = orderDetailsService.getAllOrderDetails();
        orders.sort(Comparator.comparing(OrderDetails::getCreatedAt).reversed());
        int k = orders.size();
        if (k > 5) {
            orders.subList(5,k).clear();
        }
        return orders;
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
            applicationEventPublisher.publishEvent(new CompleteOrderEvent(getUser(), orderDetails, orderItems));
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
     * @param orderDetails the order details to update with.
     * @return 200 OK on success, 400 Bad request on failure.
     */
    @PutMapping("/{orderId}")
    public ResponseEntity<String> update(@PathVariable long orderId, @RequestBody String orderDetails) {
        ResponseEntity<String> response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        OrderDetails old = orderDetailsService.getOrderDetails(orderId);

        if (null != orderDetails && null != old) {

            JSONObject order = new JSONObject(orderDetails);
            List<OrderItem> items = new ArrayList<>();
            JSONArray itemIds = (JSONArray) order.get("itemIds");
            JSONArray quantities = (JSONArray) order.get("quantities");
            for (int i = 0; i < itemIds.length(); i++) {
                System.out.println(itemIds.get(i));
                items.add(new OrderItem(old, productService.getProduct(Long.valueOf((Integer) itemIds.get(i))),
                        (Integer) quantities.get(i)));
            }
            old.setOrderItems(items);

            if ((Boolean) order.get("processed") && !old.isProcessed()) {
                old.setProcessed((Boolean) order.get("processed"));
                old.setUpdatedAt();
                applicationEventPublisher.publishEvent(new ProcessedOrderEvent(old.getUser(), old));
            } else {
                old.setProcessed((Boolean) order.get("processed"));
                old.setUpdatedAt();
            }

            this.orderDetailsService.update(orderId, old);
            response = new ResponseEntity<>(HttpStatus.OK);
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
}
