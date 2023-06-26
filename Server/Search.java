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
    try (Connection conn = DriverManager.getConnection(url, username, password)) {
        String query = "SELECT * FROM student_tbl WHERE id_number = ?";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setString(1, idNumber);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            // Record found
            System.out.println();
            System.out.println("------------------Record found------------------");
            System.out.println("ID Number: " + resultSet.getString("id_number"));
            System.out.println("Name: " + resultSet.getString("name"));
            System.out.println("Age: " + resultSet.getInt("age"));
            System.out.println("Address: " + resultSet.getString("address"));
            System.out.println("Contact: " + resultSet.getString("contact_number"));
            System.out.println("Program: " + resultSet.getString("program"));
            System.out.println("College: " + resultSet.getString("college"));
            System.out.println("------------------");

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
            
            return true;
        } else {
            // Record not found
            System.out.println("Record with ID " + idNumber + " does not exist.");
            return false;
        }
    } catch (SQLException e) {
        e.printStackTrace();
        throw e;
    }
}

    }

