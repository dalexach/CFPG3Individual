package sales.report.generator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import sales.report.model.Product;
import sales.report.model.Seller;
import sales.util.Constants;

/**
 * The GenerateInfoFiles class is responsible for generating random seller, product,
 * and sales data and writing this information to files. It uses helper methods to
 * generate sellers and products, and creates sales records for each seller.
 */
public class GenerateInfoFiles {

    /**
     * Main method that orchestrates the generation of sellers, products, and sales data.
     * It generates the files for sellers, products, and sales in the specified 'data' directory.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        try {
            // Generate seller and product data
            List<Seller> sellers = generateSellers(Constants.SALESMAN_COUNT);
            List<Product> products = generateProducts(Constants.PRODUCTS_COUNT);

            // Write sellers and products to their respective files
            FileWriter.writeSellersFile(sellers);
            FileWriter.writeProductsFile(products);

            // Generate and write sales data for each seller
            for (Seller seller : sellers) {
                List<String> sales = generateSales(seller.getDocumentNumber(), Constants.SALES_PER_SALESMAN, products.size());
                FileWriter.writeSalesFile(String.valueOf(seller.getDocumentNumber()), sales);
            }

            System.out.println("Archivos generados y almacenados en la carpeta 'data'.");
        } catch (IOException e) {
            System.err.println("Error al generar los archivos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Generates a list of random sellers using the DataGenerator class. The number of sellers
     * generated is determined by the 'count' parameter.
     *
     * @param count The number of sellers to generate.
     * @return A list of Seller objects.
     */
    private static List<Seller> generateSellers(int count) {
        List<Seller> sellers = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            sellers.add(new Seller(
                DataGenerator.generateDocumentType(),
                DataGenerator.generateDocumentNumber(),
                DataGenerator.generateName(),
                DataGenerator.generateLastName()
            ));
        }
        return sellers;
    }

    /**
     * Generates a list of random products using the DataGenerator class. The number of products
     * generated is determined by the 'count' parameter.
     *
     * @param count The number of products to generate.
     * @return A list of Product objects.
     */
    private static List<Product> generateProducts(int count) {
        List<Product> products = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            products.add(new Product(
                i,
                DataGenerator.generateProductName(),
                DataGenerator.generatePrice()
            ));
        }
        return products;
    }

    /**
     * Generates a list of random sales for a specific seller. Each sale record consists of
     * the seller's document number, a randomly selected product ID, and a randomly generated
     * quantity sold.
     *
     * @param sellerDocumentNumber The document number of the seller.
     * @param salesCount The number of sales records to generate.
     * @param productCount The number of products available to randomly choose from.
     * @return A list of sales records as strings.
     */
    private static List<String> generateSales(long sellerDocumentNumber, int salesCount, int productCount) {
        List<String> sales = new ArrayList<>();
        for (int i = 0; i < salesCount; i++) {
            int productId = (int) (Math.random() * productCount) + 1;
            int quantity = (int) (Math.random() * 10) + 1;
            sales.add(String.format("%d;%d;%d", sellerDocumentNumber, productId, quantity));
        }
        return sales;
    }
}
