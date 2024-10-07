package sales.report.processor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import sales.report.model.Product;
import sales.report.model.Seller;
import sales.util.Constants;

public class ReportGenerator {
    public static void generateSellersReport(List<Seller> sellers, String fileName) throws IOException {
        sellers.sort(Comparator.comparingDouble(Seller::getTotalSales).reversed());

        File reportsDir = new File(Constants.REPORTS_DIRECTORY);
        if (!reportsDir.exists()) {
            reportsDir.mkdir();
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(new File(reportsDir, fileName)))) {
            writer.println("TipoDocumento;NúmeroDocumento;NombreCompleto;TotalVentas");
            for (Seller seller : sellers) {
                writer.printf("%s;%d;%s;%.2f%n", 
                    seller.getDocumentType(), seller.getDocumentNumber(),
                    seller.getFullName(), seller.getTotalSales());
            }
        }
    }

    public static void generateProductsReport(List<Product> products, String fileName) throws IOException {
        Map<String, ProductSummary> productSummaries = new HashMap<>();

        for (Product product : products) {
            productSummaries.computeIfAbsent(product.getName(), k -> new ProductSummary(k))
                            .addSale(product.getSoldQuantity(), product.getPrice());
        }

        List<ProductSummary> summaries = new ArrayList<>(productSummaries.values());
        summaries.sort(Comparator.comparingInt(ProductSummary::getTotalQuantity).reversed());

        File reportsDir = new File(Constants.REPORTS_DIRECTORY);
        if (!reportsDir.exists()) {
            reportsDir.mkdir();
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(new File(reportsDir, fileName)))) {
            writer.println("NombreProducto;CantidadVendida;PrecioPromedio");
            for (ProductSummary summary : summaries) {
                if (summary.getTotalQuantity() > 0) {
                    writer.printf("%s;%d;%.2f%n", 
                        summary.getName(), summary.getTotalQuantity(), summary.getAveragePrice());
                }
            }
        }
    }

    private static class ProductSummary {
        private String name;
        private int totalQuantity;
        private double totalSales;

        public ProductSummary(String name) {
            this.name = name;
            this.totalQuantity = 0;
            this.totalSales = 0;
        }

        public void addSale(int quantity, double price) {
            this.totalQuantity += quantity;
            this.totalSales += quantity * price;
        }

        public String getName() {
            return name;
        }

        public int getTotalQuantity() {
            return totalQuantity;
        }

        public double getAveragePrice() {
            return totalQuantity > 0 ? totalSales / totalQuantity : 0;
        }
    }
}