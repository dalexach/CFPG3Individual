package sales.util;

/**
 * The Constants class contains various constants used throughout the sales system.
 * These constants include directory names, file names, and default values for data generation.
 */
public class Constants {

    // Directories and file names
    public static final String DATA_DIRECTORY = "data";
    public static final String REPORTS_DIRECTORY = "reportes";
    public static final String SELLERS_FILE = "vendedores.txt";
    public static final String PRODUCTS_FILE = "productos.txt";
    public static final String SELLERS_REPORT = "reporte_vendedores.csv";
    public static final String PRODUCTS_REPORT = "reporte_productos.csv";

    // Data generation constants
    public static final int SALESMAN_COUNT = 5;
    public static final int PRODUCTS_COUNT = 100;
    public static final int SALES_PER_SALESMAN = 10;

    // Sample data for generating random sellers and products
    public static final String[] DOCUMENT_TYPES = {"CC", "CE", "TI", "PP"};
    public static final String[] FIRST_NAMES = {"Juan", "María", "Carlos", "Ana", "Pedro", "Laura"};
    public static final String[] LAST_NAMES = {"García", "Rodríguez", "Martínez", "López", "González", "Pérez"};
    public static final String[] PRODUCTS = {"Laptop", "Smartphone", "Tablet", "Smartwatch", "Auriculares", "Cámara"};
}
