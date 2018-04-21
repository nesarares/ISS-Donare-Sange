package donation.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * 
 */
@Entity
@Table(name="donation")
public class Donation  implements Serializable{

    /**
     * Default constructor
     */
    public Donation() {
    }

    /**
     * 
     */
    @Column(name="donationDate")
    private Date donationDate;

    /**
     * 
     */
    @Column(name="HIVorAIDS")
    private boolean HIVorAIDS;

    /**
     * 
     */
    @Column(name="hepatitis")
    private boolean hepatitis;

    /**
     * 
     */
    @Column(name="syphilis")
    private boolean syphilis;

    /**
     * 
     */
    @Column(name="HTLV")
    private boolean HTLV;

    /**
     * 
     */
    @Column(name="levelALT")
    private int levelALT;

    /**
     * 
     */
    @ManyToOne
    @JoinColumn(name="donor")
    private User donor;

    /**
     * 
     */
    @OneToMany(mappedBy = "bC", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(FetchMode.SELECT)
    private List<BloodComponentQuantity> bloodComponents;

    /**
     * 
     */
    @OneToOne
    @JoinColumn(name="medicalQuestionnaire")
    private MedicalQuestionnaire medicalQuestionnaire;

    /**
     * 
     */
    @Column(name="donationStatus")
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