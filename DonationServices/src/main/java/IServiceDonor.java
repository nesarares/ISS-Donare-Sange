
import java.util.*;

/**
 * 
 */
public interface IServiceDonor {

    /**
     * @param firstName 
     * @param lastName 
     * @param birthDate 
     * @param address 
     * @param nationality 
     * @param email 
     * @param phone
     */
    public void addProfile(String firstName, String lastName, Date birthDate, String address, String nationality, String email, String phone);

    /**
     * @return
     */
    public DonorProfile getProfile();

    /**
     * @return
     */
    public List<Donation> getHistory();

    /**
     * @param username 
     * @param profile
     */
    public void updateProfile(String username, DonorProfile profile);

}