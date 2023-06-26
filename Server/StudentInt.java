package Server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;

import Client.User;

public interface StudentInt extends Remote {
    public void sort() throws RemoteException, SQLException;
    public void update(String search, String id, String name, int age, String address, String contact, String program, String college) throws RemoteException, SQLException;
    public void delete(String id) throws RemoteException, SQLException;
    public void display() throws RemoteException, SQLException;
    public void parse() throws RemoteException;
    public void search(String id, User user) throws RemoteException, SQLException;
    
}
