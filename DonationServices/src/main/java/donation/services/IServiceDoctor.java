package donation.services;

import donation.model.BloodRequest;

import java.util.*;

/**
 * 
 */
public interface IServiceDoctor {

    /**
     * @param request
     */
    void sendBloodRequest(BloodRequest request);

}