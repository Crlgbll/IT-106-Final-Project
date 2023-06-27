package Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Sort {
    public List<String> performSort() {
        List<String> sortedData = new ArrayList<>();

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

                // Create a string representation of the student's data
                String studentData = "ID: " + id + "\n" +
                        "Name: " + name + "\n" +
                        "Age: " + age + "\n" +
                        "Address: " + address + "\n" +
                        "Contact: " + contact_number + "\n" +
                        "Program: " + program + "\n" +
                        "College: " + college + "\n" +
                        "------------------";

                sortedData.add(studentData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sortedData;
    }
}
