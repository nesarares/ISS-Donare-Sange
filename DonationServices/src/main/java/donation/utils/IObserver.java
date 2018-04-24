package donation.utils;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IObserver extends Remote {

    //todo to be implemented

    void addObserver(IObserver observer) throws RemoteException;

    void testUpdate() throws  RemoteException;
}
