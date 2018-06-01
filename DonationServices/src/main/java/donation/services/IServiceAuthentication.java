package donation.services;

import donation.model.DonorProfile;
import donation.utils.IObserver;
import donation.model.UserType;

import java.rmi.RemoteException;


/**
 *
 */
public interface IServiceAuthentication {

    /**
     * @param username
     * @param password
     * @param observer
     * @return
     */
    boolean login(String username, String password, IObserver observer) throws Exception;

    /**
     * @param username
     * @param password
     * @param userType
     */
    void addNewUser(String username, String password, UserType userType, DonorProfile donorProfile) throws Exception;

    /**
     * @param username
     * @param userType
     */
    void addNewUser(String username, UserType userType) throws Exception;

    /**
     * @param username
     * @param newPassword
     */
    void updatePassword(String username, String newPassword);

    /**
     * @param username
     * @param observer
     */
    void logout(String username, IObserver observer);

    /**
     * @param username
     */
    UserType getUserType(String username);

}