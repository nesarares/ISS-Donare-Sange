package donation.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * 
 */
@Entity
@Table(name="medicalQuestionnaire")
public class MedicalQuestionnaire implements Serializable {

    /**
     * Default constructor
     */
    public MedicalQuestionnaire() {
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;

    /**
     * 
     */
    @Column(name = "healthy")
    private boolean healthy;

    /**
     * 
     */
    @Column(name = "unexpectedLossWeight")
    private boolean unexpectedLossWeight;

    /**
     * 
     */
    @Column(name = "inexplicableFever")
    private boolean inexplicableFever;

    /**
     * 
     */
    @Column(name = "dentalTreatment")
    private boolean dentalTreatment;

    /**
     * 
     */
    @Column(name = "vaccines")
    private boolean vaccines;

    /**
     * 
     */
    @Column(name = "drugTreatment")
    private boolean drugTreatment;

    /**
     * 
     */
    @Column(name = "sexualPartnerHIVHepatitis")
    private boolean sexualPartnerHIVHepatitis;

    /**
     * 
     */
    @Column(name = "sexualPartnerIntravenousDrug")
    private boolean sexualPartnerIntravenousDrug;

    /**
     * 
     */
    @Column(name = "sexualPartnerSexWorker")
    private boolean sexualPartnerSexWorker;

    /**
     * 
     */
    @Column(name = "multipleSexualPartners")
    private boolean multipleSexualPartners;

    /**
     * 
     */
    @Column(name = "intravenousDrugs")
    private boolean intravenousDrugs;

    /**
     * 
     */
    @Column(name = "sexWorker")
    private boolean sexWorker;

    /**
     * 
     */
    @Column(name = "sexualPartnerChanged")
    private boolean sexualPartnerChanged;

    /**
     * 
     */
    @Column(name = "numberOfSexualPartners")
    private int numberOfSexualPartners;

    /**
     * 
     */
    @Column(name = "surgery")
    private boolean surgery;

    /**
     * 
     */
    @Column(name = "tattoos")
    private boolean tattoos;

    /**
     *
     */
    @Column(name = "receivedBlood")
    private boolean receivedBlood;

    /**
     *
     */
    @Column(name = "pregnancy")
    private boolean pregnancy;

    /**
     * 
     */
    @Column(name = "birthdayOfChild")
    private Date birthdayOfChild;

    /**
     * 
     */
    @Column(name = "lastMenstruation")
    private Date lastMenstruation;

    /**
     * 
     */
    @Column(name = "bornLivedTravelledForeign")
    private boolean bornLivedTravelledForeign;

    /**
     * 
     */
    @Column(name = "bLTFWhere")
    private String bLTFWhere;

    /**
     * 
     */
    @Column(name = "bLTFWhen")
    private String bLTFWhen;

    /**
     * 
     */
    @Column(name = "detention")
    private boolean detention;

    /**
     * 
     */
    @Column(name = "hepatitis")
    private boolean hepatitis;

    /**
     * 
     */
    @Column(name = "icterusTuberculosisReumaticFeverMalaria")
    private boolean icterusTuberculosisReumaticFeverMalaria;

    /**
     * 
     */
    @Column(name = "heartDiseaseHLBloodPressure")
    private boolean heartDiseaseHLBloodPressure;

    /**
     * 
     */
    @Column(name = "stroke")
    private boolean stroke;

    /**
     * 
     */
    @Column(name = "asthma")
    private boolean asthma;

    /**
     * 
     */
    @Column(name = "convulsionsNervousDisease")
    private boolean convulsionsNervousDisease;

    /**
     * 
     */
    @Column(name = "diabetisUlcerCancer")
    private boolean diabetisUlcerCancer;

    /**
     * 
     */
    @Column(name = "STD")
    private boolean STD;

    /**
     * 
     */
    @Column(name = "smoker")
    private boolean smoker;

    /**
     * 
     */
    @Column(name = "lastAlcoholConsume")
    private Date lastAlcoholConsume;

    /**
     * 
     */
    @Column(name = "alcohol")
    private String alcohol;

    /**
     * 
     */
    @Column(name = "alcoholQuantity")
    private int alcoholQuantity;

    /**
     * 
     */
    @Column(name = "beenRefusedDelayed")
    private boolean beenRefusedDelayed;

    /**
     * 
     */
    @Column(name = "postDonationAttention")
    private boolean postDonationAttention;

    /**
     * 
     */
    @Column(name = "bloodPressure")
    private String bloodPressure;

    @Column(name = "idUser")
    private int idUser;

    @Column(name = "registrationDate")
    private Date date;

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public boolean isDiabetisUlcerCancer() {
        return diabetisUlcerCancer;
    }

    public void setDiabetisUlcerCancer(boolean diabetisUlcerCancer) {
        this.diabetisUlcerCancer = diabetisUlcerCancer;
    }

    public boolean isHealthy() {
        return healthy;
    }

    public void setHealthy(boolean healthy) {
        this.healthy = healthy;
    }

    public boolean isUnexpectedLossWeight() {
        return unexpectedLossWeight;
    }

    public void setUnexpectedLossWeight(boolean unexpectedLossWeight) {
        this.unexpectedLossWeight = unexpectedLossWeight;
    }

    public boolean isInexplicableFever() {
        return inexplicableFever;
    }

    public void setInexplicableFever(boolean inexplicableFever) {
        this.inexplicableFever = inexplicableFever;
    }

    public boolean isDentalTreatment() {
        return dentalTreatment;
    }

    public void setDentalTreatment(boolean dentalTreatment) {
        this.dentalTreatment = dentalTreatment;
    }

    public boolean isVaccines() {
        return vaccines;
    }

    public void setVaccines(boolean vaccines) {
        this.vaccines = vaccines;
    }

    public boolean isDrugTreatment() {
        return drugTreatment;
    }

    public void setDrugTreatment(boolean drugTreatment) {
        this.drugTreatment = drugTreatment;
    }

    public boolean isSexualPartnerHIVHepatitis() {
        return sexualPartnerHIVHepatitis;
    }

    public void setSexualPartnerHIVHepatitis(boolean sexualPartnerHIVHepatitis) {
        this.sexualPartnerHIVHepatitis = sexualPartnerHIVHepatitis;
    }

    public boolean isSexualPartnerIntravenousDrug() {
        return sexualPartnerIntravenousDrug;
    }

    public void setSexualPartnerIntravenousDrug(boolean sexualPartnerIntravenousDrug) {
        this.sexualPartnerIntravenousDrug = sexualPartnerIntravenousDrug;
    }

    public boolean isSexualPartnerSexWorker() {
        return sexualPartnerSexWorker;
    }

    public void setSexualPartnerSexWorker(boolean sexualPartnerSexWorker) {
        this.sexualPartnerSexWorker = sexualPartnerSexWorker;
    }

    public boolean isMultipleSexualPartners() {
        return multipleSexualPartners;
    }

    public void setMultipleSexualPartners(boolean multipleSexualPartners) {
        this.multipleSexualPartners = multipleSexualPartners;
    }

    public boolean isIntravenousDrugs() {
        return intravenousDrugs;
    }

    public void setIntravenousDrugs(boolean intravenousDrugs) {
        this.intravenousDrugs = intravenousDrugs;
    }

    public boolean isSexWorker() {
        return sexWorker;
    }

    public void setSexWorker(boolean sexWorker) {
        this.sexWorker = sexWorker;
    }

    public boolean isSexualPartnerChanged() {
        return sexualPartnerChanged;
    }

    public void setSexualPartnerChanged(boolean sexualPartnerChanged) {
        this.sexualPartnerChanged = sexualPartnerChanged;
    }

    public int getNumberOfSexualPartners() {
        return numberOfSexualPartners;
    }

    public void setNumberOfSexualPartners(int numberOfSexualPartners) {
        this.numberOfSexualPartners = numberOfSexualPartners;
    }

    public boolean isSurgery() {
        return surgery;
    }

    public void setSurgery(boolean surgery) {
        this.surgery = surgery;
    }

    public boolean isTattoos() {
        return tattoos;
    }

    public void setTattoos(boolean tattoos) {
        this.tattoos = tattoos;
    }

    public boolean isReceivedBlood() {
        return receivedBlood;
    }

    public void setReceivedBlood(boolean receivedBlood) {
        this.receivedBlood = receivedBlood;
    }

    public boolean isPregnancy() {
        return pregnancy;
    }

    public void setPregnancy(boolean pregnancy) {
        this.pregnancy = pregnancy;
    }

    public Date getBirthdayOfChild() {
        return birthdayOfChild;
    }

    public void setBirthdayOfChild(Date birthdayOfChild) {
        this.birthdayOfChild = birthdayOfChild;
    }

    public Date getLastMenstruation() {
        return lastMenstruation;
    }

    public void setLastMenstruation(Date lastMenstruation) {
        this.lastMenstruation = lastMenstruation;
    }

    public boolean isBornLivedTravelledForeign() {
        return bornLivedTravelledForeign;
    }

    public void setBornLivedTravelledForeign(boolean bornLivedTravelledForeign) {
        this.bornLivedTravelledForeign = bornLivedTravelledForeign;
    }

    public String getbLTFWhere() {
        return bLTFWhere;
    }

    public void setbLTFWhere(String bLTFWhere) {
        this.bLTFWhere = bLTFWhere;
    }

    public String getbLTFWhen() {
        return bLTFWhen;
    }

    public void setbLTFWhen(String bLTFWhen) {
        this.bLTFWhen = bLTFWhen;
    }

    public boolean isDetention() {
        return detention;
    }

    public void setDetention(boolean detention) {
        this.detention = detention;
    }

    public boolean isHepatitis() {
        return hepatitis;
    }

    public void setHepatitis(boolean hepatitis) {
        this.hepatitis = hepatitis;
    }

    public boolean isIcterusTuberculosisReumaticFeverMalaria() {
        return icterusTuberculosisReumaticFeverMalaria;
    }

    public void setIcterusTuberculosisReumaticFeverMalaria(boolean icterusTuberculosisReumaticFeverMalaria) {
        this.icterusTuberculosisReumaticFeverMalaria = icterusTuberculosisReumaticFeverMalaria;
    }

    public boolean isHeartDiseaseHLBloodPressure() {
        return heartDiseaseHLBloodPressure;
    }

    public void setHeartDiseaseHLBloodPressure(boolean heartDiseaseHLBloodPressure) {
        this.heartDiseaseHLBloodPressure = heartDiseaseHLBloodPressure;
    }

    public boolean isStroke() {
        return stroke;
    }

    public void setStroke(boolean stroke) {
        this.stroke = stroke;
    }

    public boolean isAsthma() {
        return asthma;
    }

    public void setAsthma(boolean asthma) {
        this.asthma = asthma;
    }

    public boolean isConvulsionsNervousDisease() {
        return convulsionsNervousDisease;
    }

    public void setConvulsionsNervousDisease(boolean convulsionsNervousDisease) {
        this.convulsionsNervousDisease = convulsionsNervousDisease;
    }

    public boolean isDiavbetisUlcerCancer() {
        return diabetisUlcerCancer;
    }

    public void setDiavbetisUlcerCancer(boolean diavbetisUlcerCancer) {
        this.diabetisUlcerCancer = diavbetisUlcerCancer;
    }

    public boolean isSTD() {
        return STD;
    }

    public void setSTD(boolean STD) {
        this.STD = STD;
    }

    public boolean isSmoker() {
        return smoker;
    }

    public void setSmoker(boolean smoker) {
        this.smoker = smoker;
    }

    public Date getLastAlcoholConsume() {
        return lastAlcoholConsume;
    }

    public void setLastAlcoholConsume(Date lastAlcoholConsume) {
        this.lastAlcoholConsume = lastAlcoholConsume;
    }

    public String getAlcohol() {
        return alcohol;
    }

    public void setAlcohol(String alcohol) {
        this.alcohol = alcohol;
    }

    public int getAlcoholQuantity() {
        return alcoholQuantity;
    }

    public void setAlcoholQuantity(int alcoholQuantity) {
        this.alcoholQuantity = alcoholQuantity;
    }

    public boolean isBeenRefusedDelayed() {
        return beenRefusedDelayed;
    }

    public void setBeenRefusedDelayed(boolean beenRefusedDelayed) {
        this.beenRefusedDelayed = beenRefusedDelayed;
    }

    public boolean isPostDonationAttention() {
        return postDonationAttention;
    }

    public void setPostDonationAttention(boolean postDonationAttention) {
        this.postDonationAttention = postDonationAttention;
    }

    public String getBloodPressure() {
        return bloodPressure;
    }

    public void setBloodPressure(String bloodPressure) {
        this.bloodPressure = bloodPressure;
    }
}