package donation.model;

import donation.model.*;
import donation.model.validators.IValidator;
import donation.model.validators.ValidationException;
import donation.model.validators.ValidatorDonorProfile;
import donation.model.validators.ValidatorUser;
import org.junit.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TestModel {

    @Test
    public void Test_BloodComponentQuantity() {

        BloodComponentQuantity bcq = new BloodComponentQuantity();

        bcq.setID(1);
        bcq.setIDdonation(2);
        bcq.setIDrequest(3);
        bcq.setIDTransfusionCenter(4);
        bcq.setQuantity(5);

        Calendar calendar = Calendar.getInstance();
        calendar.set(2018, Calendar.JANUARY, 1);
        Date date = calendar.getTime();

        bcq.setExpirationDate(date);
        bcq.setBloodComponent(BloodComponent.Plasma);
        bcq.setRhBloodGroup(RhBloodGroup.Positive);
        bcq.setAboBloodGroup(ABOBloodGroup.A);
        bcq.setBloodStatus(BloodStatus.Valid);

        Assert.assertEquals(bcq.getID(), 1);
        Assert.assertEquals(bcq.getIDdonation(), 2);
        Assert.assertEquals(bcq.getIDrequest(), 3);
        Assert.assertEquals(bcq.getIDTransfusionCenter(), 4);
        Assert.assertEquals(bcq.getQuantity(), 5);
        Assert.assertEquals(bcq.getExpirationDate(), date);

        Assert.assertNotEquals(bcq.getBloodComponent(), BloodComponent.Leukocytes);
        Assert.assertNotEquals(bcq.getRhBloodGroup(), RhBloodGroup.Negative);
        Assert.assertNotEquals(bcq.getAboBloodGroup(), ABOBloodGroup.B);
        Assert.assertNotEquals(bcq.getBloodStatus(), BloodStatus.Waiting);

    }

    @Test
    public void Test_User() {

        User user = new User();

        user.setId(1);
        user.setUsername("2");
        user.setPassHash("3");
        user.setType(UserType.Admin);

        Assert.assertEquals(user.getId(), 1);
        Assert.assertEquals(user.getUsername(), "2");

        Assert.assertFalse(!user.getPassHash().equals("3"));
        Assert.assertFalse(!user.getType().equals(UserType.Admin));

    }

    @Test
    public void Test_BloodRequest() {

        BloodRequest br = new BloodRequest();
        User user1 = new User(1, "2", "3", UserType.Admin);
        User user2 = new User(4, "5", "6", UserType.Donor);

        br.setID(1);
        br.setSender(user1);
        br.setReceiver(user2);
        br.setBloodRequestStatus(BloodRequestStatus.Completed);

        Assert.assertEquals(br.getID(), 1);
        Assert.assertEquals(br.getSender(), user1);

        Assert.assertFalse(!br.getReceiver().equals(user2));
        Assert.assertFalse(!br.getBloodRequestStatus().equals(BloodRequestStatus.Completed));

    }

    @Test
    public void Test_BloodTransfusionCenterProfile() {

        BloodTransfusionCenterProfile btcp = new BloodTransfusionCenterProfile();

        btcp.setID(1);
        btcp.setAddress("2");
        btcp.setIdUser(3);

        Assert.assertEquals(btcp.getID(), 1);

        Assert.assertNotEquals(btcp.getAddress(), "3");
        Assert.assertNotEquals(btcp.getIdUser(), 100);

    }

    @Test
    public void Test_DoctorProfile() {

        DoctorProfile dp = new DoctorProfile();

        dp.setID(1);
        dp.setFirstName("2");
        dp.setLastName("3");
        dp.setHospital("4");
        dp.setIdUser(5);

        Assert.assertNotEquals(dp.getID(), 2);
        Assert.assertNotEquals(dp.getFirstName(), "NotHisFirstName");
        Assert.assertNotEquals(dp.getLastName(), "NotHisLastName");

        Assert.assertEquals(dp.getHospital(), "4");
        Assert.assertEquals(dp.getIdUser(), 5);

    }

    @Test
    public void Test_Donation() {

        Donation d = new Donation();
        User user = new User(1, "2", "3", UserType.Donor);
        MedicalQuestionnaire mq = new MedicalQuestionnaire();
        mq.setID(1408);

        Calendar calendar = Calendar.getInstance();
        calendar.set(2018, Calendar.JANUARY, 1);
        Date date = calendar.getTime();

        List<BloodComponentQuantity> list = new ArrayList<>();
        list.add(new BloodComponentQuantity());
        list.add(new BloodComponentQuantity());
        list.add(new BloodComponentQuantity());

        d.setID(1);
        d.setIdTransfusionCenter(2);
        d.setDonationDate(date);
        d.setHIVorAIDS(false);
        d.setHepatitis(false);
        d.setSyphilis(false);
        d.setHTLV(false);
        d.setLevelALT(3);
        d.setDonor(user);
        d.setBloodComponents(list);
        d.setMedicalQuestionnaire(mq);
        d.setDonationStatus(DonationStatus.Taking);

        Assert.assertEquals(d.getID(), 1);
        Assert.assertEquals(d.getIdTransfusionCenter(), 2);
        Assert.assertEquals(d.getDonationDate(), date);

        Assert.assertFalse(d.isHIVorAIDS());
        Assert.assertFalse(d.isHepatitis());
        Assert.assertFalse(d.isSyphilis());
        Assert.assertFalse(d.isHTLV());

        Assert.assertNotEquals(d.getLevelALT(), 4);
        Assert.assertNotEquals(d.getDonor().getType(), UserType.Admin);
        Assert.assertNotEquals(d.getBloodComponents().size(), 100);

        Assert.assertEquals(d.getBloodComponents().size(), 3);
        Assert.assertEquals(d.getMedicalQuestionnaire().getID(), 1408);
        Assert.assertEquals(d.getDonationStatus(), DonationStatus.Taking);

    }

    @Test
    public void Test_DonorProfile() {

        DonorProfile dp = new DonorProfile();
        Calendar calendar = Calendar.getInstance();
        calendar.set(1997, Calendar.SEPTEMBER, 14);
        Date date = calendar.getTime();

        dp.setID(1);
        dp.setFirstName("2");
        dp.setLastName("3");
        dp.setBirthDate(date);
        dp.setNationality("4");
        dp.setEmail("5");
        dp.setPhone("6");
        dp.setIdUser(7);
        dp.setWeight(8);
        dp.setHeight(9);
        dp.setCNP("10");
        dp.setResidence("11");
        dp.setRhBloodGroup(RhBloodGroup.Positive);
        dp.setAboBloodGroup(ABOBloodGroup.A);

        Assert.assertEquals(dp.getID(), 1);
        Assert.assertEquals(dp.getFirstName(), "2");
        Assert.assertEquals(dp.getLastName(), "3");
        Assert.assertEquals(dp.getBirthDate(), date);
        Assert.assertEquals(dp.getNationality(), "4");

        Assert.assertNotEquals(dp.getEmail(), "email");
        Assert.assertNotEquals(dp.getPhone(), "123");
        Assert.assertNotEquals(dp.getIdUser(), 123);
        Assert.assertNotEquals(dp.getWeight(), 12.50);

        Assert.assertTrue(dp.getCNP().contains("10"));
        Assert.assertEquals(dp.getResidence(), "11");
        Assert.assertEquals(dp.getRhBloodGroup(), RhBloodGroup.Positive);
        Assert.assertEquals(dp.getAboBloodGroup(), ABOBloodGroup.A);

    }

    @Test
    public void Test_MedicalQuestionnaire() {

        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        Calendar calendar3 = Calendar.getInstance();
        Calendar calendar4 = Calendar.getInstance();

        calendar1.set(2018, Calendar.JANUARY, 1);
        calendar2.set(2018, Calendar.FEBRUARY, 2);
        calendar3.set(2018, Calendar.MARCH, 3);
        calendar4.set(2018, Calendar.APRIL, 4);

        Date date1 = calendar1.getTime();
        Date date2 = calendar2.getTime();
        Date date3 = calendar3.getTime();
        Date date4 = calendar4.getTime();

        MedicalQuestionnaire mq = new MedicalQuestionnaire();


        mq.setID(1);
        mq.setNumberOfSexualPartners(Integer.MAX_VALUE);
        mq.setbLTFWhere("3");
        mq.setbLTFWhen("4");
        mq.setAlcohol("5");
        mq.setBloodPressure("6");
        mq.setIdUser(7);
        mq.setAlcoholQuantity(Integer.MAX_VALUE);

        mq.setBirthdayOfChild(date1);
        mq.setLastMenstruation(date2);
        mq.setLastAlcoholConsume(date3);
        mq.setDate(date4);


        mq.setStroke(true);
        mq.setAsthma(true);
        mq.setSmoker(true);
        mq.setHealthy(true);
        mq.setVaccines(true);
        mq.setDetention(true);
        mq.setHepatitis(true);
        mq.setUnexpectedLossWeight(true);
        mq.setPostDonationAttention(true);
        mq.setMultipleSexualPartners(true);
        mq.setBornLivedTravelledForeign(true);

        mq.setSTD(false);
        mq.setSurgery(false);
        mq.setPregnancy(false);
        mq.setSexWorker(false);
        mq.setReceivedBlood(false);
        mq.setDrugTreatment(false);
        mq.setDentalTreatment(false);
        mq.setIntravenousDrugs(false);
        mq.setInexplicableFever(false);
        mq.setBeenRefusedDelayed(false);
        mq.setDiabetisUlcerCancer(false);
        mq.setSexualPartnerChanged(false);
        mq.setSexualPartnerSexWorker(false);
        mq.setConvulsionsNervousDisease(false);
        mq.setSexualPartnerHIVHepatitis(false);
        mq.setHeartDiseaseHLBloodPressure(false);
        mq.setSexualPartnerIntravenousDrug(false);
        mq.setIcterusTuberculosisReumaticFeverMalaria(false);

        Assert.assertEquals(mq.getID(), 1);
        Assert.assertEquals(mq.getbLTFWhere(), "3");
        Assert.assertEquals(mq.getbLTFWhen(), "4");
        Assert.assertEquals(mq.getAlcohol(), "5");
        Assert.assertEquals(mq.getAlcoholQuantity(), Integer.MAX_VALUE);
        Assert.assertEquals(mq.getBloodPressure(), "6");
        Assert.assertEquals(mq.getIdUser(), 7);
        Assert.assertEquals(mq.getNumberOfSexualPartners(), Integer.MAX_VALUE);

        Assert.assertEquals(mq.getBirthdayOfChild(), date1);
        Assert.assertEquals(mq.getLastMenstruation(), date2);
        Assert.assertEquals(mq.getLastAlcoholConsume(), date3);
        Assert.assertEquals(mq.getDate(), date4);

        Assert.assertTrue(mq.isHealthy());
        Assert.assertTrue(mq.isUnexpectedLossWeight());
        Assert.assertTrue(mq.isVaccines());
        Assert.assertTrue(mq.isMultipleSexualPartners());
        Assert.assertTrue(mq.isBornLivedTravelledForeign());
        Assert.assertTrue(mq.isDetention());
        Assert.assertTrue(mq.isHepatitis());
        Assert.assertTrue(mq.isStroke());
        Assert.assertTrue(mq.isAsthma());
        Assert.assertTrue(mq.isSmoker());
        Assert.assertTrue(mq.isPostDonationAttention());

        Assert.assertFalse(mq.isSTD());
        Assert.assertFalse(mq.isSurgery());
        Assert.assertFalse(mq.isPregnancy());
        Assert.assertFalse(mq.isSexWorker());
        Assert.assertFalse(mq.isReceivedBlood());
        Assert.assertFalse(mq.isDrugTreatment());
        Assert.assertFalse(mq.isDentalTreatment());
        Assert.assertFalse(mq.isIntravenousDrugs());
        Assert.assertFalse(mq.isInexplicableFever());
        Assert.assertFalse(mq.isBeenRefusedDelayed());
        Assert.assertFalse(mq.isDiabetisUlcerCancer());
        Assert.assertFalse(mq.isSexualPartnerChanged());
        Assert.assertFalse(mq.isSexualPartnerSexWorker());
        Assert.assertFalse(mq.isConvulsionsNervousDisease());
        Assert.assertFalse(mq.isSexualPartnerHIVHepatitis());
        Assert.assertFalse(mq.isHeartDiseaseHLBloodPressure());
        Assert.assertFalse(mq.isSexualPartnerIntravenousDrug());
        Assert.assertFalse(mq.isIcterusTuberculosisReumaticFeverMalaria());

    }

    @Test
    public void Test_Validators() {

        IValidator<User> userValidator = new ValidatorUser();
        IValidator<DonorProfile> donorProfileValidator = new ValidatorDonorProfile();

        User user = new User();
        try {
            user.setUsername("");
            user.setPassHash("passhash");
            userValidator.validate(user);
            Assert.fail();
        } catch (ValidationException ex) {
            Assert.assertTrue(true);
        }

        try {
            user.setUsername("username");
            user.setPassHash("");
            userValidator.validate(user);
            Assert.fail();
        } catch (ValidationException ex) {
            Assert.assertTrue(true);
        }

        DonorProfile dp = new DonorProfile();
        dp.setEmail("");
        try {
            donorProfileValidator.validate(dp);
            Assert.fail();
        } catch (ValidationException ex) {
            Assert.assertTrue(true);
        }
    }

}
