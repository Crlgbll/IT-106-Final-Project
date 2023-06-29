package Server;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.sql.ResultSet;

public class Parser {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/student_management";
    private static final String DB_USERNAME = "carlogaballo";
    private static final String DB_PASSWORD = "";

    public int displayXML() {
        int existingRecords = 0;
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
                File xmlDocument = new File("C:\\Users\\charity\\Desktop\\TobeSubmit\\Carlo Things\\IT-106-Final-Project\\Client\\student.xml");
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.parse(xmlDocument);

                NodeList studentList = doc.getElementsByTagName("student");
                String checkQuery = "SELECT COUNT(*) FROM student_tbl WHERE id_number = ?";
                String insertQuery = "INSERT INTO student_tbl (id_number, name, age, address, contact_number, program, college) VALUES (?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
                PreparedStatement insertStatement = connection.prepareStatement(insertQuery);

                for (int i = 0; i < studentList.getLength(); i++) {
                    Node studentNode = studentList.item(i);

                    if (studentNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element studentElement = (Element) studentNode;

                        String idNumber = studentElement.getElementsByTagName("id").item(0).getTextContent();

                        // Check if the record already exists in the database
                        checkStatement.setString(1, idNumber);
                        ResultSet checkResult = checkStatement.executeQuery();
                        checkResult.next();
                        int count = checkResult.getInt(1);

                        if (count > 0) {
                            // Record already exists in the database
                            System.out.println("Record with ID " + idNumber + " already exists.");
                            System.out.println();
                            existingRecords++;
                            continue; // Skip to the next iteration
                        }

                        System.out.println("ID: " + idNumber);
                        System.out.println("Name: " + studentElement.getElementsByTagName("name").item(0).getTextContent());

                        insertStatement.setString(1, idNumber);
                        insertStatement.setString(2, studentElement.getElementsByTagName("name").item(0).getTextContent());
                        int age = Integer.parseInt(studentElement.getElementsByTagName("age").item(0).getTextContent());
                        insertStatement.setInt(3, age);
                        insertStatement.setString(4, studentElement.getElementsByTagName("address").item(0).getTextContent());
                        insertStatement.setString(5, studentElement.getElementsByTagName("contact").item(0).getTextContent());
                        insertStatement.setString(6, studentElement.getElementsByTagName("program").item(0).getTextContent());
                        insertStatement.setString(7, studentElement.getElementsByTagName("college").item(0).getTextContent());

                        int rowsAffected = insertStatement.executeUpdate();

                        if (rowsAffected > 0) {
                            System.out.println("Data inserted successfully.");
                            System.out.println();
                        } else {
                            System.out.println("Data insertion failed.");
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            existingRecords = -1;
        }
        return existingRecords;
    }
}
