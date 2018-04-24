
import java.util.*;
import java.util.function.Predicate;

/**
 * 
 */
public interface IServiceScientist {

    /**
     * @param questionnaire
     */
    public void addMedicalQuestionnaire(MedicalQuestionnaire questionnaire);

    /**
     * @param bloodComponentFilter 
     * @return
     */
    public List<BloodComponentQuantity> getBloodStock(Predicate<BloodComponentQuantity> bloodComponentFilter);

    /**
     * 
     */
    public void updateEnvoyedBloodBagStatus();

    /**
     * @param username 
     * @param donation
     */
    public void addDonation(String username, Donation donation);

    /**
     * @param username 
     * @param donation
     */
    public void updateDonation(String username, Donation donation);

}