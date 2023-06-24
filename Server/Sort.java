package Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Sort {
    public void performSort() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/student_management",
                "carlogaballo", "")) {
            String query = "SELECT * FROM student_tbl ORDER BY name ASC";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                // Retrieve the data for each student from the result set
                String id = resultSet.getString("id_number");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String address = resultSet.getString("address");
                String contact_number = resultSet.getString("contact_number");
                String program = resultSet.getString("program");
                String college = resultSet.getString("college");

                // Do something with the retrieved data (e.g., display it)
                System.out.println("ID: " + id);
                System.out.println("Name: " + name);
                System.out.println("Age: " + age);
                System.out.println("Address: " + address);
                System.out.println("Contact: " + contact_number);
                System.out.println("Program: " + program);
                System.out.println("College: " + college);
                System.out.println("------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
