package Server;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class Update {
    public void update(String id, String name, int age, String address, String contact_number, String program, String college) throws RemoteException {
        // Connect to the database and perform the update operation
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/student_management", "carlogaballo", "")) {
            // Create the SQL statement and set the parameter values
            String updateQuery = "UPDATE student_tbl SET name = ?, age = ?, address = ?, contact_number = ?, program = ?, college = ? WHERE id_number = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, age);
            preparedStatement.setString(3, address);
            preparedStatement.setString(4, contact_number);
            preparedStatement.setString(5, program);
            preparedStatement.setString(6, college);
            preparedStatement.setString(7, id);

            // Execute the update statement
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Data updated successfully.");
            } else {
                System.out.println("Data update failed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
