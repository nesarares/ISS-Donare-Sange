package donation.client.controllers;

import donation.utils.IObserver;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.List;

public class ControllerRoot extends UnicastRemoteObject implements Serializable, IObserver {

    private List<IObserver> observers = new LinkedList<>();

    public ControllerRoot() throws RemoteException {

    }

    public List<IObserver> getObservers() {
        return observers;
    }

    public void addObserver(IObserver observer) throws RemoteException {
        observers.add(observer);
        System.out.println("ControllerRoot->" + observers.size());
    }

    public  void removeObserver(IObserver observer) throws  RemoteException{
        if(observer == null){
            observers.clear();
            return;
        }
        observers.remove(observer);
    }

    @Override
    public void testUpdate() throws RemoteException {
        for (IObserver observer : observers) {
            observer.testUpdate();
        }
    }

    @Override
    public void notifyDonorAnalyseFinished(String username, String message)throws  RemoteException {

        for(IObserver observer : observers){
            observer.notifyDonorAnalyseFinished(username,message);
        }
    }

    @Override
    public void notifyDonorUpdateHistory(String username) throws RemoteException {

        for(IObserver  observer : observers){
            observer.notifyDonorUpdateHistory(username);
        }
    }

    @Override
    public void notifyNewRequestAdded(String username,String message) throws RemoteException {
        for(IObserver  observer : observers){
            observer.notifyNewRequestAdded(username,message);
        }
    }


}
