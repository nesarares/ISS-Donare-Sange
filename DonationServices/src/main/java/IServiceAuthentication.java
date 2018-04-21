
import java.util.*;

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
    public boolean login(String username, String password, IObserver observer);

    /**
     * @param username 
     * @param password 
     * @param userType
     */
    public void addNewUser(String username, String password, UserType userType);

    /**
     * @param username 
     * @param userType
     */
    public void addNewUser(String username, UserType userType);

    /**
     * @param username 
     * @param newPassword
     */
    public void updatePassword(String username, String newPassword);

    /**
     * @param username 
     * @param observer
     */
    public void logout(String username, IObserver observer);

}