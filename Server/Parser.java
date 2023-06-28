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

public class Parser {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/student_management";
    private static final String DB_USERNAME = "carlogaballo";
    private static final String DB_PASSWORD = "";

    public void displayXML() {
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
                File xmlDocument = new File("C:\\Users\\charm\\OneDrive\\Desktop\\Carlo Things\\Codes\\106-almost-done\\Client\\student.xml");
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.parse(xmlDocument);

                NodeList studentList = doc.getElementsByTagName("student");
                String insertQuery = "INSERT INTO student_tbl (id_number, name, age, address, contact_number, program, college) VALUES (?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

                for (int i = 0; i < studentList.getLength(); i++) {
                    Node studentNode = studentList.item(i);

                    if (studentNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element studentElement = (Element) studentNode;

                        System.out.println("ID: " + studentElement.getElementsByTagName("id").item(0).getTextContent());
                        System.out.println("Name: " + studentElement.getElementsByTagName("name").item(0).getTextContent());

                        preparedStatement.setString(1, studentElement.getElementsByTagName("id").item(0).getTextContent());
                        preparedStatement.setString(2, studentElement.getElementsByTagName("name").item(0).getTextContent());
                        int age = Integer.parseInt(studentElement.getElementsByTagName("age").item(0).getTextContent());
                        preparedStatement.setInt(3, age);
                        preparedStatement.setString(4, studentElement.getElementsByTagName("address").item(0).getTextContent());
                        preparedStatement.setString(5, studentElement.getElementsByTagName("contact").item(0).getTextContent());
                        preparedStatement.setString(6, studentElement.getElementsByTagName("program").item(0).getTextContent());
                        preparedStatement.setString(7, studentElement.getElementsByTagName("college").item(0).getTextContent());

                        int rowsAffected = preparedStatement.executeUpdate();

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
        }
    }
}
