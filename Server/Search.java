package Server;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.SwingUtilities;

import Client.User;

public class Search {
    // Database connection details
    private String url = "jdbc:mysql://localhost:3306/student_management";
    private String username = "carlogaballo";
    private String password = "";

    public boolean searchRecord(String idNumber, User user) throws RemoteException, SQLException {
        StringBuilder message = new StringBuilder();
        boolean recordFound = false;

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

                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String address = resultSet.getString("address");
                String contactNumber = resultSet.getString("contact_number");
                String program = resultSet.getString("program");
                String college = resultSet.getString("college");

                SwingUtilities.invokeLater(() -> {
                    user.Name.setText(name);
                    user.Age.setText(String.valueOf(age));
                    user.Address.setText(address);
                    user.Contact.setText(contactNumber);
                    user.Program.setText(program);
                    user.College.setText(college);
                });

                SwingUtilities.invokeLater(() -> {
                    System.out.println(message.toString());
                });

                recordFound = true;
            } else {
                // Record not found
                System.out.println("Record with ID " + idNumber + " does not exist.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return recordFound;
    }
}
