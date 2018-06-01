package donation.services;

import donation.model.*;

import java.util.*;
import java.util.function.Predicate;

/**
 *
 */
public interface IServiceScientist {

    /**
     * @param questionnaire
     */
    void addMedicalQuestionnaire(MedicalQuestionnaire questionnaire) throws Exception;

    /**
     * @return
     */
    List<BloodComponentQuantity> getBloodStock(String center);

    /**
     * @param username
     * @param donation
     */
    void addDonation(String username, Donation donation) throws Exception;

    /**
     * @param username
     * @param donation
     */
    void updateDonation(String username, Donation donation);

    /**
     * @param bloodBag
     */
    void updateBloodComponentQuantity(BloodComponentQuantity bloodBag) throws Exception;

    /**
     * @return
     */
    List<DonorProfile> getDonorProfiles(String keyword);

    /**
     * @param username
     * @return
     */
    BloodTransfusionCenterProfile getTransfusionCenterProfile(String username);

    /**
     * @return
     */
    List<BloodTransfusionCenterProfile> getAllTransfusionCenterProfiles();

    List<BloodRequest> getBloodRequestsCenter(String username);


}