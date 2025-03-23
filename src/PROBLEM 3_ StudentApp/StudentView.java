import java.util.List;
import java.util.Scanner;

public class StudentView {
  private Scanner scanner = new Scanner(System.in);

  public int getMenuChoice() {
    System.out.println("\nStudent Management Menu:");
    System.out.println("1. Create Student");
    System.out.println("2. Read Students");
    System.out.println("3. Update Student");
    System.out.println("4. Delete Student");
    System.out.println("5. Exit");
    System.out.print("Choose an option: ");
    return scanner.nextInt();
  }

  public Student getStudentDetails() {
    scanner.nextLine(); // Consume newline
    System.out.print("Enter Name: ");
    String name = scanner.nextLine();
    System.out.print("Enter Department: ");
    String department = scanner.nextLine();
    System.out.print("Enter Marks: ");
    double marks = scanner.nextDouble();
    return new Student(name, department, marks);
  }

  public int getStudentID() {
    System.out.print("Enter Student ID: ");
    return scanner.nextInt();
  }

  public void displayStudent(Student student) {
    System.out.println("Student Details: " + student);
  }

  public void displayStudents(List<Student> students) {
    System.out.println("\nStudent List:");
    System.out.println("----------------------------------------");
    System.out.printf("%-10s %-30s %-30s %-10s%n", "StudentID", "Name",
                      "Department", "Marks");
    System.out.println("----------------------------------------");
    for (Student student : students) {
      System.out.printf("%-10d %-30s %-30s %-10.2f%n", student.getStudentID(),
                        student.getName(), student.getDepartment(),
                        student.getMarks());
    }
  }

  public void displayMessage(String message) { System.out.println(message); }
}
