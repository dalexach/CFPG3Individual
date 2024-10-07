package sales.report.generator;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import sales.report.model.Product;
import sales.report.model.Seller;
import sales.util.Constants;

/**
 * The FileWriter class is responsible for writing data related to sellers, products, 
 * and sales to files in a specified directory. It ensures that the data is saved in 
 * the appropriate format for further processing in the sales report system.
 */
public class FileWriter {

    /**
     * Writes a list of sellers to a file in CSV format. The file will be saved in the
     * directory specified by the DATA_DIRECTORY constant.
     *
     * @param sellers A list of Seller objects to write to the file.
     * @throws IOException If an I/O error occurs when writing the file.
     */
    public static void writeSellersFile(List<Seller> sellers) throws IOException {
        File dataDir = new File(Constants.DATA_DIRECTORY);
        if (!dataDir.exists()) {
            dataDir.mkdir(); // Create the directory if it doesn't exist
        }
        try (PrintWriter writer = new PrintWriter(new java.io.FileWriter(new File(dataDir, Constants.SELLERS_FILE)))) {
            writer.println("TipoDocumento;NúmeroDocumento;NombresVendedor;ApellidosVendedor");
            for (Seller seller : sellers) {
                writer.printf("%s;%d;%s;%s%n", 
                    seller.getDocumentType(), seller.getDocumentNumber(),
                    seller.getFirstName(), seller.getLastName());
            }
        }
    }

    /**
     * Writes a list of products to a file in CSV format. The file will be saved in the
     * directory specified by the DATA_DIRECTORY constant.
     *
     * @param products A list of Product objects to write to the file.
     * @throws IOException If an I/O error occurs when writing the file.
     */
    public static void writeProductsFile(List<Product> products) throws IOException {
        File dataDir = new File(Constants.DATA_DIRECTORY);
        if (!dataDir.exists()) {
            dataDir.mkdir(); // Create the directory if it doesn't exist
        }
        try (PrintWriter writer = new PrintWriter(new java.io.FileWriter(new File(dataDir, Constants.PRODUCTS_FILE)))) {
            writer.println("IDProducto;NombreProducto;PrecioPorUnidadProducto");
            for (Product product : products) {
                writer.printf("%d;%s;%.2f%n", product.getId(), product.getName(), product.getPrice());
            }
        }
    }

    /**
     * Writes sales data for a specific seller to a file. Each sale is recorded with the
     * seller's document number, product ID, and the quantity sold. The file is named based
     * on the seller's document number and saved in the directory specified by DATA_DIRECTORY.
     *
     * @param sellerDocumentNumber The document number of the seller.
     * @param sales A list of sales data, where each sale is a String containing product and quantity information.
     * @throws IOException If an I/O error occurs when writing the file.
     */
    public static void writeSalesFile(String sellerDocumentNumber, List<String> sales) throws IOException {
        File dataDir = new File(Constants.DATA_DIRECTORY);
        if (!dataDir.exists()) {
            dataDir.mkdir(); // Create the directory if it doesn't exist
        }
        try (PrintWriter writer = new PrintWriter(new java.io.FileWriter(new File(dataDir, "Vendedor_" + sellerDocumentNumber + ".txt")))) {
            writer.println("NúmeroDocumentoVendedor;IDProducto;CantidadProductoVendido");
            for (String sale : sales) {
                writer.println(sale);
            }
        }
    }
}
