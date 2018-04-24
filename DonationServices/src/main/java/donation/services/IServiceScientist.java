package donation.services;

import donation.model.BloodComponentQuantity;
import donation.model.Donation;
import donation.model.MedicalQuestionnaire;

import java.util.*;
import java.util.function.Predicate;

/**
 * 
 */
public interface IServiceScientist {

    /**
     * @param questionnaire
     */
    void addMedicalQuestionnaire(MedicalQuestionnaire questionnaire);

    /**
     * @param bloodComponentFilter 
     * @return
     */
    List<BloodComponentQuantity> getBloodStock(Predicate<BloodComponentQuantity> bloodComponentFilter);

    /**
     * 
     */
    void updateEnvoyedBloodBagStatus();

    /**
     * @param username 
     * @param donation
     */
     void addDonation(String username, Donation donation);

    /**
     * @param username 
     * @param donation
     */
     void updateDonation(String username, Donation donation);

}