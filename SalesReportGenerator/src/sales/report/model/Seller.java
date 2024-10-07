package sales.report.model;

/**
 * The Seller class represents a salesperson in the sales system. It contains the salesperson's
 * document information, name, and tracks their total sales.
 */
public class Seller {
    private String documentType;
    private long documentNumber;
    private String firstName;
    private String lastName;
    private double totalSales;

    /**
     * Constructs a Seller object with the specified document type, document number, first name, and last name.
     *
     * @param documentType The type of the seller's document (e.g., CC, TI).
     * @param documentNumber The unique document number of the seller.
     * @param firstName The first name of the seller.
     * @param lastName The last name of the seller.
     */
    public Seller(String documentType, long documentNumber, String firstName, String lastName) {
        this.documentType = documentType;
        this.documentNumber = documentNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.totalSales = 0;
    }

    /**
     * Adds a sale to the seller's total sales.
     *
     * @param amount The amount of the sale to be added to the total.
     */
    public void addSale(double amount) {
        totalSales += amount;
    }

    // Getters for Seller fields

    /**
     * Returns the document type of the seller.
     *
     * @return The document type.
     */
    public String getDocumentType() {
        return documentType;
    }

    /**
     * Returns the document number of the seller.
     *
     * @return The document number.
     */
    public long getDocumentNumber() {
        return documentNumber;
    }

    /**
     * Returns the first name of the seller.
     *
     * @return The first name.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Returns the last name of the seller.
     *
     * @return The last name.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Returns the full name of the seller (first name and last name).
     *
     * @return The full name.
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }

    /**
     * Returns the total sales amount of the seller.
     *
     * @return The total sales amount.
     */
    public double getTotalSales() {
        return totalSales;
    }
}
