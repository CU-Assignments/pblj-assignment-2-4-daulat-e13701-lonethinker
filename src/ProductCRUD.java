import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class ProductCRUD {

  // Database URL, username, and password
  private static final String URL = "jdbc:mysql://localhost:3306/Zephyr";
  private static final String USER = "lonethinker";
  private static final String PASSWORD = "khikhi";

  private static Connection connection = null;
  private static Scanner scanner = new Scanner(System.in);

  public static void main(String[] args) {
    try {
      // Load the MySQL JDBC driver
      Class.forName("com.mysql.cj.jdbc.Driver");

      // Establish a connection to the database
      connection = DriverManager.getConnection(URL, USER, PASSWORD);
      System.out.println("Connected to the database successfully.");

      // Main menu loop
      boolean exit = false;
      while (!exit) {
        System.out.println("\nProduct Management Menu:");
        System.out.println("1. Create Product");
        System.out.println("2. Read Products");
        System.out.println("3. Update Product");
        System.out.println("4. Delete Product");
        System.out.println("5. Exit");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
        case 1:
          createProduct();
          break;
        case 2:
          readProducts();
          break;
        case 3:
          updateProduct();
          break;
        case 4:
          deleteProduct();
          break;
        case 5:
          exit = true;
          break;
        default:
          System.out.println("Invalid option. Please try again.");
        }
      }

    } catch (ClassNotFoundException e) {
      System.err.println("MySQL JDBC Driver not found.");
      e.printStackTrace();
    } catch (SQLException e) {
      System.err.println("SQL Exception occurred.");
      e.printStackTrace();
    } finally {
      // Close resources
      try {
        if (connection != null)
          connection.close();
        scanner.close();
      } catch (SQLException e) {
        System.err.println("Error closing resources.");
        e.printStackTrace();
      }
    }
  }

  // Create a new product
  private static void createProduct() {
    System.out.print("Enter Product Name: ");
    String productName = scanner.nextLine();
    System.out.print("Enter Price: ");
    double price = scanner.nextDouble();
    System.out.print("Enter Quantity: ");
    int quantity = scanner.nextInt();
    scanner.nextLine(); // Consume newline

    String sql =
        "INSERT INTO Product (ProductName, Price, Quantity) VALUES (?, ?, ?)";
    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
      pstmt.setString(1, productName);
      pstmt.setDouble(2, price);
      pstmt.setInt(3, quantity);

      int rowsAffected = pstmt.executeUpdate();
      if (rowsAffected > 0) {
        System.out.println("Product inserted successfully.");
      } else {
        System.out.println("Failed to insert product.");
      }
    } catch (SQLException e) {
      System.err.println("SQL Exception occurred while creating product.");
      e.printStackTrace();
    }
  }

  // Read all products
  private static void readProducts() {
    String sql = "SELECT ProductID, ProductName, Price, Quantity FROM Product";
    try (Statement stmt = connection.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {

      System.out.println("\nProduct List:");
      System.out.println("----------------------------------------");
      System.out.printf("%-10s %-30s %-10s %-10s%n", "ProductID", "ProductName",
                        "Price", "Quantity");
      System.out.println("----------------------------------------");
      while (rs.next()) {
        int productID = rs.getInt("ProductID");
        String productName = rs.getString("ProductName");
        double price = rs.getDouble("Price");
        int quantity = rs.getInt("Quantity");

        System.out.printf("%-10d %-30s %-10.2f %-10d%n", productID, productName,
                          price, quantity);
      }
    } catch (SQLException e) {
      System.err.println("SQL Exception occurred while reading products.");
      e.printStackTrace();
    }
  }

  // Update an existing product
  private static void updateProduct() {
    System.out.print("Enter Product ID to update: ");
    int productID = scanner.nextInt();
    scanner.nextLine(); // Consume newline

    System.out.print("Enter New Product Name: ");
    String productName = scanner.nextLine();
    System.out.print("Enter New Price: ");
    double price = scanner.nextDouble();
    System.out.print("Enter New Quantity: ");
    int quantity = scanner.nextInt();
    scanner.nextLine(); // Consume newline

    String sql = "UPDATE Product SET ProductName = ?, Price = ?, Quantity = " +
                 "? WHERE ProductID = ?";
    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
      pstmt.setString(1, productName);
      pstmt.setDouble(2, price);
      pstmt.setInt(3, quantity);
      pstmt.setInt(4, productID);

      int rowsAffected = pstmt.executeUpdate();
      if (rowsAffected > 0) {
        System.out.println("Product updated successfully.");
      } else {
        System.out.println("Failed to update product. Product ID not found.");
      }
    } catch (SQLException e) {
      System.err.println("SQL Exception occurred while updating product.");
      e.printStackTrace();
    }
  }

  // Delete a product
  private static void deleteProduct() {
    System.out.print("Enter Product ID to delete: ");
    int productID = scanner.nextInt();
    scanner.nextLine(); // Consume newline

    String sql = "DELETE FROM Product WHERE ProductID = ?";
    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
      pstmt.setInt(1, productID);

      int rowsAffected = pstmt.executeUpdate();
      if (rowsAffected > 0) {
        System.out.println("Product deleted successfully.");
      } else {
        System.out.println("Failed to delete product. Product ID not found.");
      }
    } catch (SQLException e) {
      System.err.println("SQL Exception occurred while deleting product.");
      e.printStackTrace();
    }
  }
}
