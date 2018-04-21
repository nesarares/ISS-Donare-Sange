import java.util.*;

/**
 * 
 */
public class MedicalQuestionnaire {

    /**
     * Default constructor
     */
    public MedicalQuestionnaire() {
    }

    /**
     * 
     */
    private boolean healthy;

    /**
     * 
     */
    private boolean unexpectedLossWeight;

    /**
     * 
     */
    private boolean inexplicableFever;

    /**
     * 
     */
    private boolean dentalTreatment;

    /**
     * 
     */
    private boolean vaccines;

    /**
     * 
     */
    private boolean drugTreatment;

    /**
     * 
     */
    private boolean sexualPartnerHIVHepatitis;

    /**
     * 
     */
    private boolean sexualPartnerIntravenousDrug;

    /**
     * 
     */
    private boolean sexualPartnerSexWorker;

    /**
     * 
     */
    private boolean multipleSexualPartners;

    /**
     * 
     */
    private boolean intravenousDrugs;

    /**
     * 
     */
    private boolean sexWorker;

    /**
     * 
     */
    private boolean sexualPartnerChanged;

    /**
     * 
     */
    private int numberOfSexualPartners;

    /**
     * 
     */
    private boolean surgery;

    /**
     * 
     */
    private boolean tattoos;

    /**
     * 
     */
    private boolean receivedBlood;

    /**
     * 
     */
    private boolean pregnancy;

    /**
     * 
     */
    private Date birthdayOfChild;

    /**
     * 
     */
    private Date lastMenstruation;

    /**
     * 
     */
    private boolean bornLivedTravelledForeign;

    /**
     * 
     */
    private String bLTFWhere;

    /**
     * 
     */
    private String bLTFWhen;

    /**
     * 
     */
    private boolean detention;

    /**
     * 
     */
    private boolean hepatitis;

    /**
     * 
     */
    private boolean icterusTuberculosisReumaticFeverMalaria;

    /**
     * 
     */
    private boolean heartDiseaseHLBloodPressure;

    /**
     * 
     */
    private boolean stroke;

    /**
     * 
     */
    private boolean asthma;

    /**
     * 
     */
    private boolean convulsionsNervousDisease;

    /**
     * 
     */
    private boolean diavbetisUlcerCancer;

    /**
     * 
     */
    private boolean STD;

    /**
     * 
     */
    private boolean smoker;

    /**
     * 
     */
    private Date lastAlcoholConsume;

    /**
     * 
     */
    private String alcohol;

    /**
     * 
     */
    private int alcoholQuantity;

    /**
     * 
     */
    private boolean beenRefusedDelayed;

    /**
     * 
     */
    private boolean postDonationAttention;

    /**
     * 
     */
    private String bloodPressure;


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
        return diavbetisUlcerCancer;
    }

    public void setDiavbetisUlcerCancer(boolean diavbetisUlcerCancer) {
        this.diavbetisUlcerCancer = diavbetisUlcerCancer;
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