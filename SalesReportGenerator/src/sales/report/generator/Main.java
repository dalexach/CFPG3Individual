package sales.report.generator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * The Main class is responsible for processing sales data for sellers and products, 
 * generating reports that summarize total sales for each seller and each product. 
 * It reads the seller and product information from text files, processes individual 
 * sales files, and generates two reports in CSV format.
 */
public class Main {
    private static final String SELLERS_FILE = "data/sellers.txt";
    private static final String PRODUCTS_FILE = "data/products.txt";
    private static final String SALES_DIR = "data";
    private static final String SELLERS_REPORT = "reports/sellers_report.csv";
    private static final String PRODUCTS_REPORT = "reports/products_report.csv";
    private static final NumberFormat NUMBER_FORMAT = NumberFormat.getInstance(new Locale("es", "ES"));

    /**
     * Main method that controls the overall process of loading sellers, loading products,
     * processing sales, and generating reports for sellers and products.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        try {
            Map<String, Seller> sellers = loadSellers();
            Map<Integer, Product> products = loadProducts();
            processSales(sellers, products);
            generateSellersReport(sellers);
            generateProductsReport(products);
            System.out.println("Proceso completado. Reportes generados y almacenados en la carpeta 'reports'.");
        } catch (IOException | ParseException e) {
            System.err.println("Error al procesar los archivos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Loads sellers from the "vendedores.txt" file.
     *
     * @return A map of sellers, where the key is the document number, and the value is a Seller object.
     * @throws IOException If there is an error reading the file.
     */
    private static Map<String, Seller> loadSellers() throws IOException {
        Map<String, Seller> sellers = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(SELLERS_FILE))) {
            br.readLine(); // Skip header
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 4) {
                    String id = parts[1]; // Document number as key
                    sellers.put(id, new Seller(parts[0], parts[1], parts[2], parts[3]));
                }
            }
        }
        return sellers;
    }

    /**
     * Loads products from the "productos.txt" file.
     *
     * @return A map of products, where the key is the product ID, and the value is a Product object.
     * @throws IOException If there is an error reading the file.
     */
    private static Map<Integer, Product> loadProducts() throws IOException, ParseException {
        Map<Integer, Product> products = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(PRODUCTS_FILE))) {
            br.readLine(); // Skip header
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 3) {
                    int id = Integer.parseInt(parts[0]);
                    double price = NUMBER_FORMAT.parse(parts[2]).doubleValue();
                    products.put(id, new Product(id, parts[1], price));
                }
            }
        }
        return products;
    }

    /**
     * Processes sales data from individual salesperson files, updating the total sales 
     * for both sellers and products.
     *
     * @param sellers  A map of sellers.
     * @param products A map of products.
     * @throws IOException If there is an error reading sales files.
     */
    private static void processSales(Map<String, Seller> sellers, Map<Integer, Product> products) throws IOException {
        File dir = new File(SALES_DIR);
        File[] salesFiles = dir.listFiles((d, name) -> name.startsWith("Vendedor_") && name.endsWith(".txt"));
        if (salesFiles != null) {
            for (File file : salesFiles) {
                try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                    br.readLine(); // Skip header
                    String line;
                    while ((line = br.readLine()) != null) {
                        String[] parts = line.split(";");
                        if (parts.length == 3) {
                            String sellerId = parts[0];
                            int productId = Integer.parseInt(parts[1]);
                            int quantity = Integer.parseInt(parts[2]);
                            Seller seller = sellers.get(sellerId);
                            Product product = products.get(productId);
                            if (seller != null && product != null) {
                                seller.addSale(product, quantity);
                                product.incrementSoldQuantity(quantity);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Generates a report for sellers, listing each seller's document number, full name, 
     * and total sales, sorted by total sales in descending order.
     *
     * @param sellers A map of sellers.
     * @throws IOException If there is an error writing the report.
     */
    private static void generateSellersReport(Map<String, Seller> sellers) throws IOException {
        List<Seller> sellerList = new ArrayList<>(sellers.values());
        sellerList.sort(Comparator.comparingDouble(Seller::getTotalSales).reversed());

        File reportsDir = new File("reports");
        if (!reportsDir.exists()) {
            reportsDir.mkdir();
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(SELLERS_REPORT))) {
            writer.println("TipodeDocumento;NumerodeDocumento;Nombre;TotalVentas");
            for (Seller seller : sellerList) {
                writer.printf("%s;%s;%s;%.2f%n", 
                    seller.getDocumentType(), seller.getDocumentNumber(), 
                    seller.getFullName(), seller.getTotalSales());
            }
        }
        System.out.println("Reporte de vendedores generado.");
    }

    /**
     * Generates a report for products, listing each product's name, total quantity sold, 
     * and the average price, sorted by total quantity sold in descending order.
     *
     * @param products A map of products.
     * @throws IOException If there is an error writing the report.
     */
    private static void generateProductsReport(Map<Integer, Product> products) throws IOException {
        Map<String, ProductSummary> productSummaries = new HashMap<>();

        for (Product product : products.values()) {
            ProductSummary summary = productSummaries.computeIfAbsent(product.getName(), 
                k -> new ProductSummary(k, 0, 0.0));
            summary.addSale(product.getSoldQuantity(), product.getPrice());
        }

        List<ProductSummary> summaryList = new ArrayList<>(productSummaries.values());
        summaryList.sort(Comparator.comparingInt(ProductSummary::getSoldQuantity).reversed());

        File reportsDir = new File("reports");
        if (!reportsDir.exists()) {
            reportsDir.mkdir();
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(PRODUCTS_REPORT))) {
            writer.println("NombredelProducto;CantidadVendida;PrecioPromedio");
            for (ProductSummary summary : summaryList) {
                if (summary.getSoldQuantity() > 0) {
                    writer.printf("%s;%d;%.2f%n", 
                        summary.getName(), summary.getSoldQuantity(), summary.getAveragePrice());
                }
            }
        }
        System.out.println("Reporte de productos generado.");
    }
}

/**
 * The Seller class represents a seller with document information and total sales.
 */
class Seller {
    private String documentType;
    private String documentNumber;
    private String firstName;
    private String lastName;
    private double totalSales;

    /**
     * Constructs a Seller with the given document and personal information.
     *
     * @param documentType   The type of the seller's document.
     * @param documentNumber The seller's document number.
     * @param firstName      The seller's first name.
     * @param lastName       The seller's last name.
     */
    public Seller(String documentType, String documentNumber, String firstName, String lastName) {
        this.documentType = documentType;
        this.documentNumber = documentNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.totalSales = 0;
    }

    /**
     * Adds a sale to the seller's total sales.
     *
     * @param product  The product sold.
     * @param quantity The quantity sold.
     */
    public void addSale(Product product, int quantity) {
        totalSales += product.getPrice() * quantity;
    }

    public String getDocumentType() { return documentType; }
    public String getDocumentNumber() { return documentNumber; }
    public String getFullName() { return firstName + " " + lastName; }
    public double getTotalSales() { return totalSales; }
}

/**
 * The Product class represents a product with an ID, name, price, and total quantity sold.
 */
class Product {
    private int id;
    private String name;
    private double price;
    private int soldQuantity;

    /**
     * Constructs a Product with the given ID, name, and price.
     *
     * @param id    The product ID.
     * @param name  The product name.
     * @param price The product price.
     */
    public Product(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.soldQuantity = 0;
    }

    /**
     * Increments the quantity sold for this product.
     *
     * @param quantity The quantity sold.
     */
    public void incrementSoldQuantity(int quantity) {
        soldQuantity += quantity;
    }

    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getSoldQuantity() { return soldQuantity; }
}

/**
 * The ProductSummary class represents a summary of a product's sales with the total quantity sold and the average price.
 */
class ProductSummary {
    private String name;
    private int soldQuantity;
    private double totalSales;

    /**
     * Constructs a ProductSummary with the given product name, total quantity sold, and total sales amount.
     *
     * @param name         The product name.
     * @param soldQuantity The total quantity sold.
     * @param totalSales   The total sales amount.
     */
    public ProductSummary(String name, int soldQuantity, double totalSales) {
        this.name = name;
        this.soldQuantity = soldQuantity;
        this.totalSales = totalSales;
    }

    /**
     * Adds a sale to this product summary.
     *
     * @param quantity The quantity sold.
     * @param price    The product price.
     */
    public void addSale(int quantity, double price) {
        this.soldQuantity += quantity;
        this.totalSales += quantity * price;
    }

    public String getName() { return name; }
    public int getSoldQuantity() { return soldQuantity; }
    public double getAveragePrice() { return soldQuantity > 0 ? totalSales / soldQuantity : 0; }
}
