import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class StudentController {
  // Database URL, username, and password
  private static final String URL = "jdbc:mysql://localhost:3306/Zephyr";
  private static final String USER = "lonethinker";
  private static final String PASSWORD = "khikhi";

  private Connection connection;

  public StudentController() {
    try {
      // Load the MySQL JDBC driver
      Class.forName("com.mysql.cj.jdbc.Driver");

      // Establish a connection to the database
      connection = DriverManager.getConnection(URL, USER, PASSWORD);
      System.out.println("Connected to the database successfully.");
    } catch (ClassNotFoundException e) {
      System.err.println("MySQL JDBC Driver not found.");
      e.printStackTrace();
    } catch (SQLException e) {
      System.err.println("SQL Exception occurred.");
      e.printStackTrace();
    }
  }

  // Create a new student
  public void createStudent(Student student) {
    String sql =
        "INSERT INTO Student (Name, Department, Marks) VALUES (?, ?, ?)";
    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
      pstmt.setString(1, student.getName());
      pstmt.setString(2, student.getDepartment());
      pstmt.setDouble(3, student.getMarks());

      int rowsAffected = pstmt.executeUpdate();
      if (rowsAffected > 0) {
        System.out.println("Student inserted successfully.");
      } else {
        System.out.println("Failed to insert student.");
      }
    } catch (SQLException e) {
      System.err.println("SQL Exception occurred while creating student.");
      e.printStackTrace();
    }
  }

  // Read all students
  public List<Student> readStudents() {
    List<Student> students = new ArrayList<>();
    String sql = "SELECT StudentID, Name, Department, Marks FROM Student";
    try (Statement stmt = connection.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {

      while (rs.next()) {
        int studentID = rs.getInt("StudentID");
        String name = rs.getString("Name");
        String department = rs.getString("Department");
        double marks = rs.getDouble("Marks");

        students.add(new Student(studentID, name, department, marks));
      }
    } catch (SQLException e) {
      System.err.println("SQL Exception occurred while reading students.");
      e.printStackTrace();
    }
    return students;
  }

  // Update an existing student
  public void updateStudent(int studentID, Student student) {
    String sql = "UPDATE Student SET Name = ?, Department = ?, Marks = ? "
                 + "WHERE StudentID = ?";
    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
      pstmt.setString(1, student.getName());
      pstmt.setString(2, student.getDepartment());
      pstmt.setDouble(3, student.getMarks());
      pstmt.setInt(4, studentID);

      int rowsAffected = pstmt.executeUpdate();
      if (rowsAffected > 0) {
        System.out.println("Student updated successfully.");
      } else {
        System.out.println("Failed to update student. Student ID not found.");
      }
    } catch (SQLException e) {
      System.err.println("SQL Exception occurred while updating student.");
      e.printStackTrace();
    }
  }

  // Delete a student
  public void deleteStudent(int studentID) {
    String sql = "DELETE FROM Student WHERE StudentID = ?";
    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
      pstmt.setInt(1, studentID);

      int rowsAffected = pstmt.executeUpdate();
      if (rowsAffected > 0) {
        System.out.println("Student deleted successfully.");
      } else {
        System.out.println("Failed to delete student. Student ID not found.");
      }
    } catch (SQLException e) {
      System.err.println("SQL Exception occurred while deleting student.");
      e.printStackTrace();
    }
  }

  // Close the connection
  public void closeConnection() {
    try {
      if (connection != null)
        connection.close();
    } catch (SQLException e) {
      System.err.println("Error closing resources.");
      e.printStackTrace();
    }
  }
}
