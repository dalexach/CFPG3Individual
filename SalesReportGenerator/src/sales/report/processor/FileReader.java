package sales.report.processor;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import sales.report.model.Product;
import sales.report.model.Sale;
import sales.report.model.Seller;
import sales.util.Constants;

/**
 * The FileReader class is responsible for reading data from various input files
 * and converting them into lists of Seller, Product, and Sale objects.
 */
public class FileReader {
    private static final NumberFormat NUMBER_FORMAT = NumberFormat.getInstance(new Locale("es", "ES"));

    /**
     * Reads the sellers data from the input file and returns a list of Seller objects.
     *
     * @return A list of Seller objects.
     * @throws IOException If an I/O error occurs while reading the file.
     */
    public static List<Seller> readSellers() throws IOException {
        List<Seller> sellers = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new java.io.FileReader(new File(Constants.DATA_DIRECTORY, Constants.SELLERS_FILE)))) {
            String line;
            br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 4) {
                    sellers.add(new Seller(parts[0], Long.parseLong(parts[1]), parts[2], parts[3]));
                }
            }
        }
        return sellers;
    }

    /**
     * Reads the products data from the input file and returns a list of Product objects.
     *
     * @return A list of Product objects.
     * @throws IOException If an I/O error occurs while reading the file.
     */
    public static List<Product> readProducts() throws IOException {
        List<Product> products = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new java.io.FileReader(new File(Constants.DATA_DIRECTORY, Constants.PRODUCTS_FILE)))) {
            String line;
            br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 3) {
                    try {
                        int id = Integer.parseInt(parts[0]);
                        String name = parts[1];
                        double price = NUMBER_FORMAT.parse(parts[2]).doubleValue();
                        products.add(new Product(id, name, price));
                    } catch (ParseException e) {
                        System.err.println("Error parsing price for product: " + parts[1]);
                    }
                }
            }
        }
        return products;
    }

    /**
     * Reads the sales data from multiple input files and returns a list of Sale objects.
     *
     * @return A list of Sale objects.
     * @throws IOException If an I/O error occurs while reading the files.
     */
    public static List<Sale> readSales() throws IOException {
        List<Sale> sales = new ArrayList<>();
        File dir = new File(Constants.DATA_DIRECTORY);
        File[] salesFiles = dir.listFiles((d, name) -> name.matches("Vendedor_\\d+\\.txt"));
        if (salesFiles != null) {
            for (File file : salesFiles) {
                try (BufferedReader br = new BufferedReader(new java.io.FileReader(file))) {
                    String line;
                    br.readLine(); // Skip header
                    while ((line = br.readLine()) != null) {
                        String[] parts = line.split(";");
                        if (parts.length == 3) {
                            sales.add(new Sale(parts[0], Integer.parseInt(parts[1]), Integer.parseInt(parts[2])));
                        }
                    }
                }
            }
        }
        return sales;
    }
}