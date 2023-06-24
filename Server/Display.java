package Server;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Display {
    public void displayStudentData() {
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

            System.out.println("\n\n-------------------- DISPLAY -----------------\n\n");
            // Display the data
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String address = resultSet.getString("address");
                String contact = resultSet.getString("contact_number");
                String program = resultSet.getString("program");
                String college = resultSet.getString("college");
                String idNumber = resultSet.getString("id_number");

                System.out.println("ID: " + idNumber);
                System.out.println("Name: " + name);
                System.out.println("Age: " + age);
                System.out.println("Address: " + address);
                System.out.println("Contact: " + contact);
                System.out.println("Program: " + program);
                System.out.println("College: " + college);
                System.out.println("------------------");
            }

            // Close the resources
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("An error occurred while displaying student data: " + e.getMessage());
        }
    }

    
}
