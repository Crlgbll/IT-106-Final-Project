package Server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;


public interface StudentInt extends Remote {
    public void sort() throws RemoteException, SQLException;

    public boolean update(String search, String id, String name, int age, String address, String contact, String program,
            String college) throws RemoteException, SQLException;

    public boolean delete(String id) throws RemoteException, SQLException;

    public void display() throws RemoteException, SQLException;

    public boolean parse() throws RemoteException;

    public List<String> performSort() throws RemoteException, SQLException; 

    public List<String> displayStudentData() throws RemoteException, SQLException;

    public String searchRecord(String idNumber) throws RemoteException, SQLException;

}
