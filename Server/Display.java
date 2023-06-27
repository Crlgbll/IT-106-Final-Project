package Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Display {
    public List<String> displayStudentData() {
        List<String> studentData = new ArrayList<>();

        // Database connection details
        String url = "jdbc:mysql://localhost:3306/student_management";
        String username = "carlogaballo";
        String password = "";

        try {
            // Establish the database connection
            Connection connection = DriverManager.getConnection(url, username, password);

            // Prepare the SQL query
            String query = "SELECT * FROM student_tbl";
            PreparedStatement statement = connection.prepareStatement(query);

            // Execute the query and retrieve the result set
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String address = resultSet.getString("address");
                String contact = resultSet.getString("contact_number");
                String program = resultSet.getString("program");
                String college = resultSet.getString("college");
                String idNumber = resultSet.getString("id_number");

                String studentInfo = "ID: " + idNumber + "\n" +
                                     "Name: " + name + "\n" +
                                     "Age: " + age + "\n" +
                                     "Address: " + address + "\n" +
                                     "Contact: " + contact + "\n" +
                                     "Program: " + program + "\n" +
                                     "College: " + college + "\n";

                studentData.add(studentInfo);
            }

            // Close the resources
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("An error occurred while displaying student data: " + e.getMessage());
        }

        return studentData;  // Add this line to return the studentData
    }
}