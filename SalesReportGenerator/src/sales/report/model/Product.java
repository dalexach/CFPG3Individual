package sales.report.model;

/**
 * The Product class represents a product in the sales system. Each product has an ID, 
 * a name, a price, and a quantity representing how many units have been sold.
 */
public class Product {
    private int id;
    private String name;
    private double price;
    private int soldQuantity;

    /**
     * Constructs a Product with the specified ID, name, and price. The initial sold quantity is set to 0.
     *
     * @param id The unique identifier for the product.
     * @param name The name of the product.
     * @param price The price of the product.
     */
    public Product(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.soldQuantity = 0; // Default sold quantity is 0
    }

    /**
     * Increases the quantity of the product that has been sold.
     *
     * @param quantity The quantity to add to the sold total.
     */
    public void incrementSoldQuantity(int quantity) {
        soldQuantity += quantity;
    }

    // Getters for the product fields

    /**
     * Returns the ID of the product.
     *
     * @return The product ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the name of the product.
     *
     * @return The product name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the price of the product.
     *
     * @return The product price.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Returns the total quantity of the product that has been sold.
     *
     * @return The total sold quantity.
     */
    public int getSoldQuantity() {
        return soldQuantity;
    }
}
