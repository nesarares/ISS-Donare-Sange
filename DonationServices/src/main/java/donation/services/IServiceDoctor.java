package donation.services;

import donation.model.BloodRequest;
import donation.model.DoctorProfile;

import java.util.List;

/**
 *
 */
public interface IServiceDoctor {

    /**
     * @param request
     * @param username
     */
    void addBloodRequest(BloodRequest request, String username) throws Exception;

    /**
     * @param username
     * @return
     */
    List<BloodRequest> getBloodRequestsDoctor(String username);

    /**
     * @param username
     * @return
     */
    DoctorProfile getDoctorProfile(String username);

}