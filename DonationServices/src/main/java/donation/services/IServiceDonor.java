package donation.services;

import donation.model.Donation;
import donation.model.DonorProfile;

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
     void addProfile(String firstName, String lastName, Date birthDate, String address, String nationality, String email, String phone);

    /**
     * @return
     */
     DonorProfile getProfile();

    /**
     * @return
     */
     List<Donation> getHistory();

    /**
     * @param username 
     * @param profile
     */
     void updateProfile(String username, DonorProfile profile) throws Exception;

}