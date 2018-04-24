package donation.client.controllers;

import donation.utils.IObserver;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.List;

public class ControllerRoot extends UnicastRemoteObject implements Serializable,IObserver {

    private List<IObserver> observers = new LinkedList<>();

    public ControllerRoot() throws RemoteException {

    }


    public  void addObserver(IObserver observer) throws  RemoteException{
        observers.add(observer);
        System.out.println(observers.size());
    }

    @Override
    public void testUpdate() throws RemoteException {

        for(IObserver observer : observers){
            observer.testUpdate();
        }

    }
}
