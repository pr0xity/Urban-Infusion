package no.ntnu.appdevapi.DTO;

/**
 * Data transfer object of order details.
 */
public class OrderDetailsDto {

    /**
     * The user's of this order's email address.
     */
    private final String email;

    /**
     * The total cost of this order.
     */
    private double total;

    /**
     * The amount of items in this order.
     */
    private int quantity;

    /**
     * Whether the order is processed or not.
     */
    private boolean processed;

    /**
     * Creates an DTO of order details.
     *
     * @param email the email of the user of this order.
     * @param total the total cost of this order.
     * @param quantity the amount of items in this order.
     * @param processed if this order is processed or not.
     */
    public OrderDetailsDto(String email, double total, int quantity, boolean processed) {
        this.email = email;

        if ( total != 0.0 ) {
            this.total = total;
        }
        if ( quantity != 0 ) {
            this.quantity = quantity;
        }

        this.processed = processed;
    }

    /**
     * Returns the user of this order's email-address.
     *
     * @return the user of this order's email-address.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Returns the total cost of this order.
     *
     * @return total cost of this order.
     */
    public double getTotal() {
        return total;
    }

    /**
     * Returns the amount of items in this order.
     *
     * @return the amount of items in this order.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Returns whether the order is processed or not.
     *
     * @return true if processed, false if not processed.
     */
    public boolean isProcessed() {
        return processed;
    }
}
