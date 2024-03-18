package org.example;

import java.sql.*;
import java.util.Scanner;

public class A3Q1jdbc {

    static final String url = "jdbc:postgresql://localhost:5432/A3Q1";
    static final String user = "postgres";
    static final String pass = "postgres";



    public static void verifyConnection() {
        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            System.out.println("Connected successfully.");
        } catch (SQLException e) {
        }
    }

    // select all
    public static void getAllStudents() {
        try (Connection conn = DriverManager.getConnection(url, user, pass);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM students")) {

            while (rs.next()) {
                int studentId = rs.getInt("student_id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String email = rs.getString("email");
                Date enrollmentDate = rs.getDate("enrollment_date");
                System.out.println("Student ID: " + studentId + ", Name: " + firstName + " " + lastName +
                        ", Email: " + email + ", Enrollment Date: " + enrollmentDate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public static void addStudent(String firstName, String lastName, String email, Date enrollmentDate) {
        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            String sql = "INSERT INTO students (first_name, last_name, email, enrollment_date) VALUES (?, ?, ?, ?)";


            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, email);
            pstmt.setDate(4, enrollmentDate);

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new student has been added successfully.");


                ResultSet generatedKeys = pstmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    System.out.println("Generated ID for the new student: " + generatedId);
                }
            }
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    // update email using id
    public static void updateStudentEmail(int studentId, String newEmail) {
        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement pstmt = conn.prepareStatement("UPDATE students SET email = ? WHERE student_id = ?")) {

            pstmt.setString(1, newEmail);
            pstmt.setInt(2, studentId);
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Email of the student with ID " + studentId + " has been updated successfully.");
            } else {
                System.out.println("No such student found with ID " + studentId);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // delete student using id
    public static void deleteStudent(int studentId) {
        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement pstmt = conn.prepareStatement("DELETE FROM students WHERE student_id = ?")) {

            pstmt.setInt(1, studentId);
            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Student with ID " + studentId + " has been deleted successfully.");
            } else {
                System.out.println("No such student found with ID " + studentId);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // get student based on id
    public static void getStudentById(int studentId) {
        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM students WHERE student_id = ?")) {

            pstmt.setInt(1, studentId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String email = rs.getString("email");
                Date enrollmentDate = rs.getDate("enrollment_date");
                System.out.println("Student ID: " + studentId + ", Name: " + firstName + " " + lastName +
                        ", Email: " + email + ", Enrollment Date: " + enrollmentDate);
            } else {
                System.out.println("No student found with ID " + studentId);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {


        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("Menu:");
            System.out.println("1. View all students");
            System.out.println("2. Add a new student");
            System.out.println("3. Update student email");
            System.out.println("4. Delete a student");
            System.out.println("5. View student by ID");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    getAllStudents();
                    break;
                case 2:
                    System.out.print("Enter student first name: ");
                    String firstName = scanner.nextLine();
                    System.out.print("Enter student last name: ");
                    String lastName = scanner.nextLine();
                    System.out.print("Enter student email: ");
                    String email = scanner.nextLine();
                    System.out.print("Enter student enrollment date (YYYY-MM-DD): ");
                    String enrollmentDateStr = scanner.nextLine();
                    Date enrollmentDate = Date.valueOf(enrollmentDateStr);
                    addStudent(firstName, lastName, email, enrollmentDate);
                    break;
                case 3:
                    System.out.print("Enter student ID: ");
                    int studentIdForUpdate = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter new email: ");
                    String newEmail = scanner.nextLine();
                    updateStudentEmail(studentIdForUpdate, newEmail);
                    break;
                case 4:
                    System.out.print("Enter student ID: ");
                    int studentIdForDelete = scanner.nextInt();
                    deleteStudent(studentIdForDelete);
                    break;
                case 5:
                    System.out.print("Enter student ID: ");
                    int studentIdToView = scanner.nextInt();
                    getStudentById(studentIdToView);
                    break;
                case 6:
                    System.out.println("Ending program..");
                    break;
                default:
                    System.out.println("Invalid choice, choose a number between 1 and 6");
            }
        } while (choice != 6);

        scanner.close();
    }
}
