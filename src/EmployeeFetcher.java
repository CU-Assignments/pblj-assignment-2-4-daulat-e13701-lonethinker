import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EmployeeFetcher {

    // Database URL, username, and password
    private static final String URL = "jdbc:mysql://localhost:3306/Zephyr";
    private static final String USER = "lonethinker";
    private static final String PASSWORD = "khikhi";

    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish a connection to the database
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to the database successfully.");

            // Create a statement object
            statement = connection.createStatement();

            // Define the SQL query to retrieve all records from the Employee table
            String sql = "SELECT EmpID, Name, Salary FROM Employee";

            // Execute the query and retrieve the result set
            resultSet = statement.executeQuery(sql);

            // Process the result set
            System.out.println("Employee Records:");
            while (resultSet.next()) {
                int empID = resultSet.getInt("EmpID");
                String name = resultSet.getString("Name");
                double salary = resultSet.getDouble("Salary");

                // Display the employee record
                System.out.printf("EmpID: %d, Name: %s, Salary: %.2f%n", empID, name, salary);
            }

        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("SQL Exception occurred.");
            e.printStackTrace();
        } finally {
            // Close resources in reverse order of their creation
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.err.println("Error closing resources.");
                e.printStackTrace();
            }
        }
    }
}
