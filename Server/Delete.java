package Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Delete {
    public boolean delete(String id) throws SQLException {
        boolean deletionStatus = false;
        // Connect to the database and perform the delete operation
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/student_management","carlogaballo", "")) {
            // Create the SQL statement and set the parameter value
            if (id.isEmpty()) {
                return deletionStatus;
            } else {
                String deleteQuery = "DELETE FROM student_tbl WHERE id_number = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
                preparedStatement.setString(1, id);

                // Execute the delete statement
                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Data deleted successfully.");
                    deletionStatus = true;
                } else {
                    System.out.println("Data deletion failed.");
                    return deletionStatus;
                }
            }
        }
        return deletionStatus;
    }
}