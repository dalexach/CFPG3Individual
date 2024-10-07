package sales.report.model;

/**
 * The Sale class represents a sales transaction in the system. It contains the document number 
 * of the seller, the product ID, and the quantity of the product sold in a particular sale.
 */
public class Sale {
    private String sellerDocumentNumber;
    private int productId;
    private int quantity;

    /**
     * Constructs a Sale object with the specified seller's document number, product ID, and quantity sold.
     *
     * @param sellerDocumentNumber The document number of the seller involved in the sale.
     * @param productId The ID of the product sold.
     * @param quantity The quantity of the product sold.
     */
    public Sale(String sellerDocumentNumber, int productId, int quantity) {
        this.sellerDocumentNumber = sellerDocumentNumber;
        this.productId = productId;
        this.quantity = quantity;
    }

    // Getters for the sale fields

    /**
     * Returns the document number of the seller.
     *
     * @return The seller's document number.
     */
    public String getSellerDocumentNumber() {
        return sellerDocumentNumber;
    }

    /**
     * Returns the product ID of the product sold.
     *
     * @return The product ID.
     */
    public int getProductId() {
        return productId;
    }

    /**
     * Returns the quantity of the product sold in the sale.
     *
     * @return The quantity sold.
     */
    public int getQuantity() {
        return quantity;
    }
}
