package donation.utils;

import donation.model.BloodRequest;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IObserver extends Remote {

    default void addObserver(IObserver observer) throws RemoteException {
        throw new RemoteException("Not implemented!");
    }

    default void removeObserver(IObserver observer) throws RemoteException {
        throw new RemoteException("Not implemented!");
    }

    default void testUpdate() throws RemoteException {
        throw new RemoteException("Not implemented!");
    }

    default void notifyDonorAnalyseFinished(String username, String message) throws RemoteException {
        throw new RemoteException("Not implemented!");
    }

    default void notifyDonorUpdateHistory(String username) throws RemoteException {
        throw new RemoteException("Not implemented!");
    }

    default void notifyNewRequestAdded(String username, String message) throws RemoteException {
        throw new RemoteException("Not implemented!");
    }

    default void reloadDoctorTables() throws RemoteException {
        throw new RemoteException("Not implemented!");
    }

    default void reloadCenterView() throws RemoteException {
        throw new RemoteException("Not implemented!");
    }
}

