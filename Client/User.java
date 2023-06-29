package Client;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.rmi.RemoteException;
import java.util.List;

import Server.Search;
import Server.Parser;
import Server.StudentInt;

public class User extends JFrame {
    private StudentInt server;
    private JLabel lblUserMenu;
    public JTextField Name;
    public JTextField Age;
    public JTextField Program;
    public JTextField Address;
    public JTextField College;
    public JTextField Contact;
    public JTextField student_ID;
    public JTextField SearchID;
    private JLabel lblAge;
    private JLabel lblContactNo;
    private JLabel lblAddress;
    private JLabel lblProgram;
    private JLabel lblCollege;
    private JLabel lblID_1;

    public User() {
        try {
            // Get the reference to the remote Student service

            setTitle("User GUI");

            // Set the astro space theme colors
            Color darkBlue = new Color(0, 17, 51);
            Color white = new Color(255, 255, 255);

            getContentPane().setBackground(darkBlue);
            setSize(843, 472);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            getContentPane().setLayout(null);

            lblUserMenu = new JLabel("USER'S MENU");
            lblUserMenu.setFont(new Font("SansSerif", Font.BOLD, 14));
            lblUserMenu.setForeground(white);
            lblUserMenu.setBounds(82, 34, 134, 36);
            getContentPane().add(lblUserMenu);

            JButton btnSort = new JButton("Sort");
            btnSort.setBounds(55, 81, 149, 42);
            getContentPane().add(btnSort);

            btnSort.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(User.this, "Sorting...");
                    try {
                        List<String> sortedData = server.performSort(); // Retrieve the sorted data from the server

                        // Create a JTextArea to display the sorted data
                        JTextArea textArea = new JTextArea();
                        textArea.setEditable(false);

                        // Append the sorted data to the text area
                        for (String studentData : sortedData) {
                            textArea.append(studentData + "\n");
                        }

                        // Create a JScrollPane and add the text area to it
                        JScrollPane scrollPane = new JScrollPane(textArea);
                        scrollPane.setPreferredSize(new Dimension(400, 300));

                        // Display the scrollable message dialog
                        JOptionPane.showMessageDialog(User.this, scrollPane, "Sorted Data", JOptionPane.PLAIN_MESSAGE);

                    } catch (RemoteException | SQLException err) {
                        System.err.println("Client err: " + err.toString());
                    }
                }
            });
            JButton btnDisplay = new JButton("Display");
            btnDisplay.setBounds(55, 144, 149, 42);
            getContentPane().add(btnDisplay);
            btnDisplay.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(User.this, "Displaying...");
                    try {
                        List<String> studentData = server.displayStudentData();

                        // Create a JTextArea to display the student data
                        JTextArea textArea = new JTextArea();
                        textArea.setEditable(false);

                        // Append the student data to the text area
                        for (String data : studentData) {
                            textArea.append(data + "\n");
                        }

                        // Create a JScrollPane and add the text area to it
                        JScrollPane scrollPane = new JScrollPane(textArea);
                        scrollPane.setPreferredSize(new Dimension(400, 300));

                        // Display the scrollable message dialog
                        JOptionPane.showMessageDialog(User.this, scrollPane, "Student Data", JOptionPane.PLAIN_MESSAGE);

                    } catch (RemoteException | SQLException err) {
                        System.err.println("Client err: " + err.toString());
                    }
                }
            });

            JButton btnDelete = new JButton("Delete");
            btnDelete.setBounds(55, 204, 149, 42);
            getContentPane().add(btnDelete);
            // ActionListener code
            btnDelete.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String studentID = SearchID.getText();
                    try {
                        String recordInfo = server.searchRecord(studentID);
                        if (recordInfo != null) {
                            boolean deletionStatus = server.delete(studentID);
                            JOptionPane.showMessageDialog(User.this, "Deleting...");
                            System.out.println("\n Deleting... \n");
                            if (deletionStatus) {
                                JOptionPane.showMessageDialog(User.this,
                                        "Student " + studentID + " was deleted successfully.", "Deletion Successful",
                                        JOptionPane.INFORMATION_MESSAGE);
                                System.out.println("Student " + studentID + " was deleted successfully.");
                            } else {
                                JOptionPane.showMessageDialog(User.this, "An error occurred while deleting the user.",
                                        "Deletion Error", JOptionPane.ERROR_MESSAGE);
                                System.out.println("An error occurred while deleting the user.");
                            }
                        } else {
                            JOptionPane.showMessageDialog(User.this, "Student " + studentID + " does not exist.",
                                    "Deletion Failed", JOptionPane.WARNING_MESSAGE);
                            System.out.println("Student " + studentID + " does not exist.");
                        }
                    } catch (RemoteException | SQLException e1) {
                        e1.printStackTrace();
                        JOptionPane.showMessageDialog(User.this, "An error occurred while deleting the user.",
                                "Deletion Error", JOptionPane.ERROR_MESSAGE);
                        System.out.println("An error occurred while deleting the user.");
                    }
                }
            });

            JButton btnUpdate = new JButton("Update");
            btnUpdate.setBounds(55, 264, 149, 42);
            getContentPane().add(btnUpdate);
            btnUpdate.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String searchID = SearchID.getText();
                    String studentID = student_ID.getText();
                    String name = Name.getText();
                    int age = Integer.parseInt(Age.getText());
                    String address = Address.getText();
                    String contactNumber = Contact.getText();
                    String program = Program.getText();
                    String college = College.getText();

                    try {
                        String recordInfo = server.searchRecord(searchID);
                        boolean updateStatus = server.update(searchID, studentID, name, age, address, contactNumber,
                                program, college);
                        if (recordInfo != null) {
                            if (updateStatus) {
                                JOptionPane.showMessageDialog(User.this, "Updating...");
                                JOptionPane.showMessageDialog(User.this, "Data updated successfully.");
                                System.out.println("\nRecord updated successfully \n");
                            } else {
                                JOptionPane.showMessageDialog(User.this, "Data update failed.");

                            }

                        } else {
                            JOptionPane.showMessageDialog(User.this, "Student " + searchID + " does not exist.");
                        }

                    } catch (Exception e1) {
                        e1.printStackTrace();
                        JOptionPane.showMessageDialog(User.this, "Data update failed.");
                    }
                }
            });

            JButton btnExtractXML = new JButton("Extract XML");
            btnExtractXML.setBounds(55, 327, 149, 42);
            getContentPane().add(btnExtractXML);

            // Assuming you have an instance of the Parser class named 'parser'
            btnExtractXML.addActionListener(new ActionListener() {
                Parser parser = new Parser();

                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(User.this, "Extracting Data from XML...");
                    int existingRecords = parser.displayXML();
                    if (existingRecords == 0) {
                        JOptionPane.showMessageDialog(User.this, "Data Extracted Successfully");
                    } else if (existingRecords == -1) {
                        JOptionPane.showMessageDialog(User.this, "An error occurred during data extraction.");
                    } else {
                        JOptionPane.showMessageDialog(User.this, "Data already exists in the database.");
                    }
                }
            });

            // Name for Update Method
            JLabel lblNewLabel = new JLabel("Name");
            lblNewLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
            lblNewLabel.setForeground(white);
            lblNewLabel.setBounds(330, 142, 97, 21);
            getContentPane().add(lblNewLabel);

            Name = new JTextField();
            Name.setBounds(330, 162, 180, 30);
            getContentPane().add(Name);
            Name.setColumns(10);

            // Age for Update Method
            lblAge = new JLabel("Age");
            lblAge.setFont(new Font("SansSerif", Font.PLAIN, 12));
            lblAge.setForeground(white);
            lblAge.setBounds(331, 202, 97, 21);
            getContentPane().add(lblAge);

            Age = new JTextField();
            Age.setBounds(330, 222, 180, 31);
            getContentPane().add(Age);
            Age.setColumns(10);

            // Program for Update Method
            lblProgram = new JLabel("Program");
            lblProgram.setFont(new Font("SansSerif", Font.PLAIN, 12));
            lblProgram.setForeground(white);
            lblProgram.setBounds(568, 202, 97, 21);
            getContentPane().add(lblProgram);

            Program = new JTextField();
            Program.setBounds(568, 222, 180, 30);
            getContentPane().add(Program);
            Program.setColumns(10);

            // Address for Update Method
            lblAddress = new JLabel("Address");
            lblAddress.setFont(new Font("SansSerif", Font.PLAIN, 12));
            lblAddress.setForeground(white);
            lblAddress.setBounds(568, 142, 97, 21);
            getContentPane().add(lblAddress);

            Address = new JTextField();
            Address.setBounds(568, 162, 180, 30);
            getContentPane().add(Address);
            Address.setColumns(10);

            // College for Update Method
            lblCollege = new JLabel("College");
            lblCollege.setFont(new Font("SansSerif", Font.PLAIN, 12));
            lblCollege.setForeground(white);
            lblCollege.setBounds(568, 262, 97, 21);
            getContentPane().add(lblCollege);

            College = new JTextField();
            College.setBounds(568, 284, 180, 30);
            getContentPane().add(College);
            College.setColumns(10);

            // Contact number for Update Method
            lblContactNo = new JLabel("Contact no.");
            lblContactNo.setFont(new Font("SansSerif", Font.PLAIN, 12));
            lblContactNo.setForeground(white);
            lblContactNo.setBounds(330, 262, 97, 21);
            getContentPane().add(lblContactNo);

            Contact = new JTextField();
            Contact.setBounds(330, 284, 180, 30);
            getContentPane().add(Contact);
            Contact.setColumns(10);

            // Student ID for Update
            student_ID = new JTextField();
            student_ID.setBounds(331, 102, 180, 26);
            getContentPane().add(student_ID);
            student_ID.setColumns(10);

            lblID_1 = new JLabel("ID Number");
            lblID_1.setFont(new Font("SansSerif", Font.PLAIN, 12));
            lblID_1.setForeground(white);
            lblID_1.setBounds(331, 82, 97, 21);
            getContentPane().add(lblID_1);

            SearchID = new JTextField();
            SearchID.setBounds(510, 41, 180, 26);
            getContentPane().add(SearchID);
            SearchID.setColumns(10);

            JButton btnSearchID = new JButton("Search ID");
            btnSearchID.setBounds(700, 41, 95, 26);
            getContentPane().add(btnSearchID);

            btnSearchID.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String studentID = SearchID.getText();

                    JOptionPane.showMessageDialog(User.this, "Searching for student " + studentID);
                    System.out.println("Searching for student " + studentID);
                    try {
                        String recordInfo = server.searchRecord(studentID);
                        if (recordInfo != null) {
                            JOptionPane.showMessageDialog(User.this, recordInfo, "Record Found",
                                    JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(User.this, "Record with ID " + studentID + " does not exist.",
                                    "Record Not Found", JOptionPane.WARNING_MESSAGE);
                            System.out.println("Searching for student " + studentID + " does not exist.");
                        }
                    } catch (RemoteException | SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void recordSearch(String id, String name, int age, String address, String contact_number, String program,
            String college) {
        if (id != null && !id.isEmpty() &&
                name != null && !name.isEmpty() &&
                address != null && !address.isEmpty() &&
                contact_number != null && !contact_number.isEmpty() &&
                program != null && !program.isEmpty() &&
                college != null && !college.isEmpty()) {
            // All input parameters are valid
            Search search = new Search(); // Create an instance of the Search class
            try {
                String recordInfo = search.searchRecord(id);
                if (recordInfo != null) {
                    System.out.println(recordInfo);
                } else {
                    System.out.println("Record with ID " + id + " does not exist.");
                }
            } catch (RemoteException | SQLException e) {
                e.printStackTrace();
                // Handle the exception appropriately
            }
        } else {
            // Invalid input parameters
            System.out.println("Invalid input parameters. Please provide values for all fields.");
        }
    }

    public void serverConnection(String host, int port) {
        try {
            Registry registry = LocateRegistry.getRegistry(host, port);
            server = (StudentInt) registry.lookup("Server");
            System.out.println("Connection established between client and server...");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        User user = new User();
        try {
            user.serverConnection("localhost", 9100);
            user.setVisible(true); // Make the GUI window visible
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
