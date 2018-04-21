import java.util.*;

/**
 * 
 */
public class Donation {

    /**
     * Default constructor
     */
    public Donation() {
    }

    /**
     * 
     */
    private Date donationDate;

    /**
     * 
     */
    private boolean HIVorAIDS;

    /**
     * 
     */
    private boolean hepatitis;

    /**
     * 
     */
    private boolean syphilis;

    /**
     * 
     */
    private boolean HTLV;

    /**
     * 
     */
    private int levelALT;

    /**
     * 
     */
    private User donor;

    /**
     * 
     */
    private List<BloodComponentQuantity> bloodComponents;

    /**
     * 
     */
    private MedicalQuestionnaire medicalQuestionnaire;

    /**
     * 
     */
    private DonationStatus donationStatus;


    public Date getDonationDate() {
        return donationDate;
    }

    public void setDonationDate(Date donationDate) {
        this.donationDate = donationDate;
    }

    public boolean isHIVorAIDS() {
        return HIVorAIDS;
    }

    public void setHIVorAIDS(boolean HIVorAIDS) {
        this.HIVorAIDS = HIVorAIDS;
    }

    public boolean isHepatitis() {
        return hepatitis;
    }

    public void setHepatitis(boolean hepatitis) {
        this.hepatitis = hepatitis;
    }

    public boolean isSyphilis() {
        return syphilis;
    }

    public void setSyphilis(boolean syphilis) {
        this.syphilis = syphilis;
    }

    public boolean isHTLV() {
        return HTLV;
    }

    public void setHTLV(boolean HTLV) {
        this.HTLV = HTLV;
    }

    public int getLevelALT() {
        return levelALT;
    }

    public void setLevelALT(int levelALT) {
        this.levelALT = levelALT;
    }

    public User getDonor() {
        return donor;
    }

    public void setDonor(User donor) {
        this.donor = donor;
    }

    public List<BloodComponentQuantity> getBloodComponents() {
        return bloodComponents;
    }

    public void setBloodComponents(List<BloodComponentQuantity> bloodComponents) {
        this.bloodComponents = bloodComponents;
    }

    public MedicalQuestionnaire getMedicalQuestionnaire() {
        return medicalQuestionnaire;
    }

    public void setMedicalQuestionnaire(MedicalQuestionnaire medicalQuestionnaire) {
        this.medicalQuestionnaire = medicalQuestionnaire;
    }

    public DonationStatus getDonationStatus() {
        return donationStatus;
    }

    public void setDonationStatus(DonationStatus donationStatus) {
        this.donationStatus = donationStatus;
    }
}