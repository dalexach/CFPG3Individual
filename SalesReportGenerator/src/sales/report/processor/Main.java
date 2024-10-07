package sales.report.processor;

import java.io.IOException;
import java.util.List;
import sales.report.model.Product;
import sales.report.model.Sale;
import sales.report.model.Seller;
import sales.util.Constants;

/**
 * The Main class is responsible for processing sales data, generating reports,
 * and orchestrating the reading and processing of sellers, products, and sales.
 */
public class Main {

    /**
     * Main method that reads the sellers, products, and sales data, processes the sales,
     * and generates the corresponding reports.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        try {
            List<Seller> sellers = FileReader.readSellers();
            List<Product> products = FileReader.readProducts();
            List<Sale> sales = FileReader.readSales();

            processSales(sellers, products, sales);

            ReportGenerator.generateSellersReport(sellers, Constants.SELLERS_REPORT);
            ReportGenerator.generateProductsReport(products, Constants.PRODUCTS_REPORT);

            System.out.println("Proceso completado. Se han generado los reportes y están almacenados en la carpeta 'reportes'.");
        } catch (IOException e) {
            System.err.println("Error al procesar los archivos: " + e.getMessage());
        }
    }

    /**
     * Processes the sales data by associating each sale with the corresponding seller and product,
     * updating the seller's total sales and the product's sold quantity.
     *
     * @param sellers A list of sellers.
     * @param products A list of products.
     * @param sales A list of sales transactions.
     */
    private static void processSales(List<Seller> sellers, List<Product> products, List<Sale> sales) {
        for (Sale sale : sales) {
            Seller seller = findSeller(sellers, sale.getSellerDocumentNumber());
            Product product = findProduct(products, sale.getProductId());
            if (seller != null && product != null) {
                double saleAmount = product.getPrice() * sale.getQuantity();
                seller.addSale(saleAmount);
                product.incrementSoldQuantity(sale.getQuantity());
            }
        }
    }

    /**
     * Finds a seller by their document number from a list of sellers.
     *
     * @param sellers A list of sellers.
     * @param documentNumber The document number of the seller.
     * @return The Seller object if found, otherwise null.
     */
    private static Seller findSeller(List<Seller> sellers, String documentNumber) {
        return sellers.stream()
                .filter(s -> String.valueOf(s.getDocumentNumber()).equals(documentNumber))
                .findFirst()
                .orElse(null);
    }

    /**
     * Finds a product by its ID from a list of products.
     *
     * @param products A list of products.
     * @param id The product ID.
     * @return The Product object if found, otherwise null.
     */
    private static Product findProduct(List<Product> products, int id) {
        return products.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
