package donation.services;

import donation.model.BloodRequest;

import java.util.*;
import java.util.function.Predicate;

/**
 * 
 */
public interface IServiceDoctor {

    /**
     *
     * @param request
     * @param username
     */
    void addBloodRequest(BloodRequest request, String username) throws Exception;

    /**
     *
     * @param predicate
     * @return
     */
    List<BloodRequest> getBloodRequestsDoctor(String username);

}