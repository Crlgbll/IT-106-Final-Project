package Server;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Update {
    public void update(String id, String name, int age, String address, String contact_number, String program, String college) throws RemoteException {
        // Connect to the database and check if the record exists
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/student_management", "carlogaballo", "")) {
            // Check if the record exists
            String selectQuery = "SELECT * FROM student_tbl WHERE id_number = ?";
            PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
            selectStatement.setString(1, id);
            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                // The record exists, perform the update
                String updateQuery = "UPDATE student_tbl SET name = ?, age = ?, address = ?, contact_number = ?, program = ?, college = ? WHERE id_number = ?";
                PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
                updateStatement.setString(1, name);
                updateStatement.setInt(2, age);
                updateStatement.setString(3, address);
                updateStatement.setString(4, contact_number);
                updateStatement.setString(5, program);
                updateStatement.setString(6, college);
                updateStatement.setString(7, id);

                // Execute the update statement
                int rowsAffected = updateStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Data updated successfully.");
                } else {
                    System.out.println("Data update failed.");
                }
            } else {
                // The record does not exist
                System.out.println("Student does not exist.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
