package sales.report.generator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * The GenerateInfoFiles class is responsible for generating data files used in a sales reporting system.
 * It creates files for products, salespersons, and sales records in the 'data' folder of the project.
 * It also reads data from the generated files and uses it to create pseudo-random sales data for each salesperson.
 */
public class GenerateInfoFiles {
    private static final String[] DOCUMENT_TYPES = {"CC", "CE", "TI", "PP"};
    private static final String[] FIRST_NAMES = {"Juan", "María", "Carlos", "Ana", "Pedro", "Laura"};
    private static final String[] LAST_NAMES = {"García", "Rodríguez", "Martínez", "López", "González", "Pérez"};
    private static final String[] PRODUCTS = {"Laptop", "Smartphone", "Tablet", "Smartwatch", "Auriculares", "Cámara"};
    private static final Random RANDOM = new Random();

    /**
     * Creates a sales file for a salesperson, containing random sales data.
     *
     * @param documentNumber The document number of the salesperson.
     * @param salesCount The number of sales records to generate.
     * @param productIds A list of product IDs to randomly assign to each sale.
     * @throws IOException If there is an error writing to the file.
     */
    public static void createSalesPersonFile(String documentNumber, int salesCount, List<Integer> productIds) throws IOException {
        File dataDir = new File("data");
        if (!dataDir.exists()) {
            dataDir.mkdir();
        }
        try (PrintWriter writer = new PrintWriter(new FileWriter(new File(dataDir, "Vendedor_" + documentNumber + ".txt")))) {
            writer.println("SellerDocumentNumber;ProductID;ProductQuantitySold");
            for (int i = 0; i < salesCount; i++) {
                int productId = productIds.get(RANDOM.nextInt(productIds.size()));
                int quantity = RANDOM.nextInt(10) + 1;
                writer.printf("%s;%d;%d%n", documentNumber, productId, quantity);
            }
        }
    }

    

    /**
     * Creates a file containing a list of products with random prices.
     *
     * @param productsCount The number of products to generate.
     * @throws IOException If there is an error writing to the file.
     */
    public static void createProductsFile(int productsCount) throws IOException {
        File dataDir = new File("data");
        if (!dataDir.exists()) {
            dataDir.mkdir();
        }
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("es", "ES"));
        DecimalFormat df = new DecimalFormat("#,##0.00", symbols);
        try (PrintWriter writer = new PrintWriter(new FileWriter(new File(dataDir, "products.txt")))) {
            writer.println("ProductID;ProductName;ProductUnitPrice");
            for (int i = 1; i <= productsCount; i++) {
                String productName = PRODUCTS[RANDOM.nextInt(PRODUCTS.length)];
                double price = 10.0 + (1000.0 - 10.0) * RANDOM.nextDouble();
                writer.printf("%d;%s;%s%n", i, productName, df.format(price));
            }
        }
    }

    /**
     * Creates a file containing a list of salespersons with random document numbers, first names, and last names.
     *
     * @param salesPersonCount The number of salespersons to generate.
     * @throws IOException If there is an error writing to the file.
     */
    public static void createSalesPersonInfoFile(int salesPersonCount) throws IOException {
        File dataDir = new File("data");
        if (!dataDir.exists()) {
            dataDir.mkdir();
        }
        try (PrintWriter writer = new PrintWriter(new FileWriter(new File(dataDir, "sellers.txt")))) {
            writer.println("DocumentType;DocumentNumber;SellerFirstName;SellerLastName");
            for (int i = 1; i <= salesPersonCount; i++) {
                String documentType = DOCUMENT_TYPES[RANDOM.nextInt(DOCUMENT_TYPES.length)];
                long id = 1000000000L + RANDOM.nextInt(1000000000); // Random document number
                String firstName = FIRST_NAMES[RANDOM.nextInt(FIRST_NAMES.length)];
                String lastName = LAST_NAMES[RANDOM.nextInt(LAST_NAMES.length)];
                writer.printf("%s;%d;%s;%s%n", documentType, id, firstName, lastName);
            }
        }
    }

    /**
     * Reads the product IDs from the products file.
     *
     * @return A list of product IDs.
     * @throws IOException If there is an error reading from the file.
     */
    private static List<Integer> readProductIds() throws IOException {
        List<Integer> productIds = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(new File("data", "products.txt")))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                productIds.add(Integer.parseInt(parts[0]));
            }
        }
        return productIds;
    }

    /**
     * Reads the salesperson document numbers from the vendors file.
     *
     * @return A list of salesperson document numbers.
     * @throws IOException If there is an error reading from the file.
     */
    private static List<String> readVendorIds() throws IOException {
        List<String> vendorIds = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(new File("data", "sellers.txt")))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                vendorIds.add(parts[1]); // Use DocumentNumber as ID
            }
        }
        return vendorIds;
    }

    /**
     * Main method to generate product, salesperson, and sales files. The products and salesperson files
     * are read and used to generate random sales data for each salesperson.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        try {
            int salesPersonCount = 5;
            int productsCount = 100;
            int salesPerSalesPerson = 10;

            // Generate input files
            createProductsFile(productsCount);
            createSalesPersonInfoFile(salesPersonCount);

            List<Integer> productIds = readProductIds();
            List<String> vendorIds = readVendorIds();

            // Generate sales files for each salesperson
            for (String vendorId : vendorIds) {
                createSalesPersonFile(vendorId, salesPerSalesPerson, productIds);
            }

            System.out.println("Archivos creados y guardados en la carpeta 'data'.");
        } catch (IOException e) {
            System.err.println("Error al generar los archivos: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
