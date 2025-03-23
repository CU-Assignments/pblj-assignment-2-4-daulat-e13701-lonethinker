import java.util.List;
import java.util.Scanner;

public class StudentApp {
    private StudentView view;
    private StudentController controller;
    private Scanner scanner;

    public StudentApp() {
        view = new StudentView();
        controller = new StudentController();
        scanner = new Scanner(System.in);
    }

    public void run() {
        boolean exit = false;
        while (!exit) {
            int choice = view.getMenuChoice();

            switch (choice) {
                case 1:
                    createStudent();
                    break;
                case 2:
                    readStudents();
                    break;
                case 3:
                    updateStudent();
                    break;
                case 4:
                    deleteStudent();
                    break;
                case 5:
                    exit = true;
                    break;
                default:
                    view.displayMessage("Invalid option. Please try again.");
            }
        }
        controller.closeConnection();
        scanner.close();
    }

    private void createStudent() {
        Student student = view.getStudentDetails();
        controller.createStudent(student);
    }

    private void readStudents() {
        List<Student> students = controller.readStudents();
        view.displayStudents(students);
    }

    private void updateStudent() {
        int studentID = view.getStudentID();
        Student student = view.getStudentDetails();
        controller.updateStudent(studentID, student);
    }

    private void deleteStudent() {
        int studentID = view.getStudentID();
        controller.deleteStudent(studentID);
    }

    public static void main(String[] args) {
        StudentApp app = new StudentApp();
        app.run();
    }
}
