package no.ntnu.appdevapi.DTO;

/**
 * Data transfer object of order details.
 */
public class OrderDetailsDto {

    /**
     * The user's of this order's email address.
     */
    private String id;

    /**
     * The total cost of this order.
     */
    private String[] itemIds;

    /**
     * The amount of items in this order.
     */
    private String[] quantities;

    /**
     * Whether the order is processed or not.
     */
    private boolean processed;

    /**
     * Creates an DTO of order details.
     *
     * @param id the id of the order.
     * @param itemIds the ids of the items in the order.
     * @param quantities the quantity of each item in this order.
     * @param processed if this order is processed or not.
     */
    public OrderDetailsDto(String id, String[] itemIds, String[] quantities, boolean processed) {
        this.id = id;
        this.itemIds = itemIds;
        this.quantities = quantities;
        this.processed = processed;
    }

    //Empty constructor
    public OrderDetailsDto() {
    }

    /**
     * Returns the id of the order.
     *
     * @return {@code long} id of the order.
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the id of each item in the order.
     *
     * @return {@code int[]} id of every item in the order.
     */
    public String[] getItemIds() {
        return itemIds;
    }

    /**
     * Returns the amount of each item in this order.
     *
     * @return the amount of each item in this order.
     */
    public String[] getQuantities() {
        return quantities;
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
