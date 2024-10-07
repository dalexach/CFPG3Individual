package sales.report.generator;

import java.util.Random;
import sales.util.Constants;

/**
 * The DataGenerator class provides methods to generate random data for 
 * document types, names, products, and prices. It is used to simulate 
 * the creation of sales and product information in a sales report system.
 */
public class DataGenerator {
    private static final Random RANDOM = new Random();

    /**
     * Generates a random document type from the list of available types.
     *
     * @return A random document type as a String (e.g., "CC", "TI", etc.).
     */
    public static String generateDocumentType() {
        return Constants.DOCUMENT_TYPES[RANDOM.nextInt(Constants.DOCUMENT_TYPES.length)];
    }

    /**
     * Generates a random first name from the list of available names.
     *
     * @return A random first name as a String.
     */
    public static String generateName() {
        return Constants.FIRST_NAMES[RANDOM.nextInt(Constants.FIRST_NAMES.length)];
    }

    /**
     * Generates a random last name from the list of available last names.
     *
     * @return A random last name as a String.
     */
    public static String generateLastName() {
        return Constants.LAST_NAMES[RANDOM.nextInt(Constants.LAST_NAMES.length)];
    }

    /**
     * Generates a random product name from the list of available products.
     *
     * @return A random product name as a String.
     */
    public static String generateProductName() {
        return Constants.PRODUCTS[RANDOM.nextInt(Constants.PRODUCTS.length)];
    }

    /**
     * Generates a random product price between 10.0 and 1000.0.
     *
     * @return A random price as a double.
     */
    public static double generatePrice() {
        return 10.0 + (1000.0 - 10.0) * RANDOM.nextDouble();
    }

    /**
     * Generates a random document number for a person.
     *
     * @return A random document number as a long, in the range of 1,000,000,000 to 1,999,999,999.
     */
    public static long generateDocumentNumber() {
        return 1000000000L + RANDOM.nextInt(1000000000);
    }
}
