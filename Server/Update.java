package Server;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Update {
    public boolean update(String search, String id, String name, int age, String address, String contact_number,
            String program, String college) throws RemoteException {
        // Connect to the database and check if the record exists
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/student_management",
                "carlogaballo", "")) {
            // Check if the record exists
            String selectQuery = "SELECT * FROM student_tbl WHERE id_number = ?";
            PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
            selectStatement.setString(1, search);
            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                // The record exists, perform the update
                String updateQuery = "UPDATE student_tbl SET id_number = ?, name = ?, age = ?, address = ?, contact_number = ?, program = ?, college = ? WHERE id_number = ?";
                PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
                updateStatement.setString(1, id);
                updateStatement.setString(2, name);
                updateStatement.setInt(3, age);
                updateStatement.setString(4, address);
                updateStatement.setString(5, contact_number);
                updateStatement.setString(6, program);
                updateStatement.setString(7, college);
                updateStatement.setString(8, search);

                // Execute the update statement
                int rowsAffected = updateStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Data updated successfully.");
                    return true; // Return true if update successful
                } else {
                    System.out.println("Data update failed.");
                    return false; // Return false if update failed
                }
            } else {
                // The record does not exist
                System.out.println("Student does not exist.");
                return false; // Return false if student does not exist
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false if an exception occurred
        }
    }
}
