package Server;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Search {
    // Database connection details
    private String url = "jdbc:mysql://localhost:3306/student_management";
    private String username = "carlogaballo";
    private String password = "";

    public String searchRecord(String idNumber) throws RemoteException, SQLException {
        StringBuilder message = new StringBuilder();
        String recordInfo = null;

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT * FROM student_tbl WHERE id_number = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, idNumber);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // Record found
                message.append("\n------------------Record found------------------\n");
                message.append("ID Number: ").append(resultSet.getString("id_number")).append("\n");
                message.append("Name: ").append(resultSet.getString("name")).append("\n");
                message.append("Age: ").append(resultSet.getInt("age")).append("\n");
                message.append("Address: ").append(resultSet.getString("address")).append("\n");
                message.append("Contact: ").append(resultSet.getString("contact_number")).append("\n");
                message.append("Program: ").append(resultSet.getString("program")).append("\n");
                message.append("College: ").append(resultSet.getString("college")).append("\n");
                message.append("------------------\n");

                recordInfo = message.toString();
            } else {
                // Record not found
                System.out.println("Record with ID " + idNumber + " does not exist.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return recordInfo;
    }
}
