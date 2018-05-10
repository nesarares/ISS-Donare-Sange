package donation.services;

import donation.model.Donation;
import donation.model.DonorProfile;

import java.util.*;

/**
 * 
 */
public interface IServiceDonor {

    /**
     * @return
     */
     DonorProfile getProfile(String username);

    /**
     * @return
     */
     List<Donation> getHistory(String username);

    /**
     * @param username 
     * @param profile
     */
     void updateProfile(String username, DonorProfile profile) throws Exception;

}