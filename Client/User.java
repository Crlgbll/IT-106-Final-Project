package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import Server.StudentInt;
import javax.swing.JTextField;

public class User extends WindowAdapter implements ActionListener {
    private JFrame frame;
    private JPanel pnlSort;
    private JPanel pnlDelete;
    private JPanel pnlDisplay;
    private JPanel pnlUpdate;
    private JLabel lblSort;
    private JLabel lblDelete;
    private JLabel lblDisplay;
    private JLabel lblNewLabel_4;
    private JLabel lblUserMenu;

    private StudentInt server;

    public User() {
        frame = new JFrame("User GUI");
        frame.setBackground(Color.PINK);
        frame.getContentPane().setBackground(Color.PINK);
        frame.setSize(407, 546);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JPanel pnlExtractXML = new JPanel();
        pnlExtractXML.setBackground(UIManager.getColor("activeCaption"));
        pnlExtractXML.setBounds(119, 281, 156, 36);
        frame.getContentPane().add(pnlExtractXML);
        pnlExtractXML.setLayout(null);

        JLabel lblExtractXML = new JLabel("Extract XML");
        lblExtractXML.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblExtractXML.setBounds(42, 11, 114, 14);
        pnlExtractXML.add(lblExtractXML);

        pnlSort = new JPanel();
        pnlSort.setBackground(UIManager.getColor("activeCaption"));
        pnlSort.setBounds(39, 150, 140, 46);
        frame.getContentPane().add(pnlSort);
        pnlSort.setLayout(null);

        lblSort = new JLabel("Sort");
        lblSort.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblSort.setBounds(54, 11, 64, 24);
        pnlSort.add(lblSort);

        pnlDelete = new JPanel();
        pnlDelete.setBackground(UIManager.getColor("activeCaption"));
        pnlDelete.setLayout(null);
        pnlDelete.setBounds(39, 213, 140, 46);
        frame.getContentPane().add(pnlDelete);

        lblDelete = new JLabel("Delete");
        lblDelete.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblDelete.setBounds(48, 11, 46, 24);
        pnlDelete.add(lblDelete);

        pnlDisplay = new JPanel();
        pnlDisplay.setBackground(UIManager.getColor("activeCaption"));
        pnlDisplay.setLayout(null);
        pnlDisplay.setBounds(215, 150, 140, 46);
        frame.getContentPane().add(pnlDisplay);

        lblDisplay = new JLabel("Display");
        lblDisplay.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblDisplay.setBounds(51, 11, 52, 24);
        pnlDisplay.add(lblDisplay);

        pnlUpdate = new JPanel();
        pnlUpdate.setBackground(UIManager.getColor("activeCaption"));
        pnlUpdate.setLayout(null);
        pnlUpdate.setBounds(215, 213, 140, 46);
        frame.getContentPane().add(pnlUpdate);

        lblNewLabel_4 = new JLabel("Update");
        lblNewLabel_4.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblNewLabel_4.setBounds(49, 11, 53, 24);
        pnlUpdate.add(lblNewLabel_4);

        lblUserMenu = new JLabel("USER'S MENU");
        lblUserMenu.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblUserMenu.setBounds(151, 99, 134, 36);
        frame.getContentPane().add(lblUserMenu);

        JTextField studentIdTextField = new JTextField();
        studentIdTextField.setBounds(35,  350, 320, 30);
        studentIdTextField.setBackground(UIManager.getColor("activeCaption"));
        studentIdTextField.setFont(new Font("SansSerif", Font.PLAIN, 14));

        frame.getContentPane().add(studentIdTextField);
        pnlExtractXML.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    server.parse();
                } catch (RemoteException err) {
                    err.printStackTrace();
                }
                JOptionPane.showMessageDialog(frame, "Extracting XML...");
            }
        });

        pnlSort.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    System.out.println("Clicked");
                    server.sort();
                } catch (RemoteException | SQLException err) {
                    System.err.println("Client err: " + err.toString());
                    err.printStackTrace();
                }
                JOptionPane.showMessageDialog(frame, "Sort Users...");
            }
        });

        pnlDisplay.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    // Invoke the display method on the server
                    System.out.println("Display is clicked...");
                    server.display();
                } catch (RemoteException err) {
                    err.printStackTrace();
                } catch (SQLException err) {
                    System.err.println("SQL error: " + err.toString());
                    err.printStackTrace();
                }
                JOptionPane.showMessageDialog(frame, "Display Users...");
            }
        });

        pnlDelete.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Scanner sc = new Scanner.
                String studentID = studentIdTextField.getText();
                System.out.println("Deleted");
                System.out.println(studentID);
                 try {
                    server.delete(studentID);
                } catch (RemoteException | SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                JOptionPane.showMessageDialog(frame, "Deleting User...");
            }
        });

        pnlUpdate.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
               String studentID = studentIdTextField.getText();
               System.out.println("Updated");
               System.out.println(studentID);
                try {
                    server.update(studentID);
                } catch (RemoteException | SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                JOptionPane.showMessageDialog(frame, "Updating...");
            }
        });

        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Handle action events if needed
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
