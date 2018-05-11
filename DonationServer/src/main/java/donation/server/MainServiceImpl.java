package donation.server;

import donation.model.*;
import donation.model.validators.IValidator;
import donation.model.validators.ValidationException;
import donation.model.validators.ValidatorDonorProfile;
import donation.model.validators.ValidatorUser;
import donation.persistence.repository.IRepository;
import donation.persistence.repository.RepositoryException;
import donation.server.utils.DayCounter;
import donation.server.utils.OfflineNotifier;
import donation.services.IMainService;
import donation.utils.IObserver;

import javax.xml.crypto.Data;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

public class MainServiceImpl implements IMainService {

    private IRepository<User> userRepository;
    private IRepository<DonorProfile> donorProfileRepository;
    private IRepository<MedicalQuestionnaire> medicalQuestionnaireRepository;
    private IRepository<Donation> donationRepository;
    private IRepository<BloodComponentQuantity> bloodComponentQuantityRepository;
    private IRepository<BloodTransfusionCenterProfile> bloodTransfusionCenterProfileRepository;

    private IValidator<User> userValidator = new ValidatorUser();
    private IValidator<DonorProfile> donorProfileValidator = new ValidatorDonorProfile();

    private Map<String, IObserver> loggedUsers = new ConcurrentHashMap<>();

    private OfflineNotifier notifier = new OfflineNotifier();

    private final int DAYS_UNTIL_NEXT_DONATION = 30;
    private final int BLOOD_BAG_QUANTITY = 450;
    private final int BLOOD_COMPONENT_QUANTITY = BLOOD_BAG_QUANTITY / 3;


    public MainServiceImpl(IRepository<User> userIRepository,
                           IRepository<DonorProfile> donorProfileRepository,
                           IRepository<MedicalQuestionnaire> medicalQuestionnaireRepository,
                           IRepository<Donation> donationIRepository,
                           IRepository<BloodComponentQuantity> bloodComponentQuantityRepository,
                           IRepository<BloodTransfusionCenterProfile> bloodTransfusionCenterProfileRepository) {

        this.userRepository = userIRepository;
        this.donorProfileRepository = donorProfileRepository;
        this.medicalQuestionnaireRepository = medicalQuestionnaireRepository;
        this.donationRepository = donationIRepository;
        this.bloodComponentQuantityRepository = bloodComponentQuantityRepository;
        this.bloodTransfusionCenterProfileRepository = bloodTransfusionCenterProfileRepository;
    }

    @Override
    public synchronized boolean login(String username, String password, IObserver observer) throws Exception {

        User user = userRepository.find(x -> x.getUsername().equals(username));

        if (user == null || !user.getPassHash().equals(password)) return false;
        if (loggedUsers.get(username) != null) throw new Exception("User is already connected :(");

        loggedUsers.put(username, observer);

        if (notifier.hasUndeliveredMessages(username))
            notifier.sendUndeliveredMessages(username, this::notifyAnalysisFinished);
        return true;
    }

    @Override
    public synchronized void addNewUser(String username, String password, UserType userType, DonorProfile donorProfile) throws Exception {

        User newUser = new User();
        newUser.setPassHash(password);
        newUser.setUsername(username);
        newUser.setType(userType);

        String error = "";

        try {
            userValidator.validate(newUser);
        } catch (Exception e) {
            error += e.getMessage();
        }

        try {
            donorProfileValidator.validate(donorProfile);
        } catch (Exception e) {
            error += e.getMessage();
        }

        if (!error.equals("")) throw new ValidationException(error);

        if (userRepository.find(u -> u.getUsername().equals(username)) != null)
            throw new ValidationException("Username already exists\n");

        userRepository.save(newUser);
        int id = userRepository.find(u -> u.getUsername().equals(username)).getId();
        donorProfile.setIdUser(id);

        donorProfileRepository.save(donorProfile);
    }

    @Override
    public void addNewUser(String username, UserType userType) throws Exception {

    }

    @Override
    public void updatePassword(String username, String newPassword) {

    }

    @Override
    public void logout(String username, IObserver observer) {
        if (loggedUsers.get(username) == null) return;

        System.out.println("User logged out: " + username);
        loggedUsers.remove(username);
    }

    @Override
    public UserType getUserType(String username) {
        User user = userRepository.find(x -> x.getUsername().equals(username));
        if (user == null) return null;
        return user.getType();
    }

    @Override
    public void sendBloodRequest(BloodRequest request) {

    }

    @Override
    public DonorProfile getProfile(String username) {
        int idUser = userRepository.find(u -> u.getUsername().equals(username)).getId();
        return donorProfileRepository.find(p -> p.getIdUser() == idUser);
    }

    @Override
    public List<Donation> getHistory(String username) {
        return donationRepository.getAllFiltered(x -> x.getDonor().getUsername().equals(username));
    }

    @Override
    public void updateProfile(String username, DonorProfile profile) throws Exception {

        donorProfileValidator.validate(profile);
        int id = userRepository.find(u -> u.getUsername().equals(username)).getId();
        profile.setIdUser(id);

        int oldProfileId = donorProfileRepository.find(d -> d.getIdUser() == id).getID();
        donorProfileRepository.update(oldProfileId, profile);
    }

    @Override
    public void addMedicalQuestionnaire(MedicalQuestionnaire questionnaire) throws RepositoryException {
        //todo sa facem ca dupa ce aduga un chestionar sa adauge si donarea/rezultatele donarii
        //todo sa nu reamaa donari fara chestionre
        medicalQuestionnaireRepository.save(questionnaire);
    }

    @Override
    public List<BloodComponentQuantity> getBloodStock(String center) {
        return bloodComponentQuantityRepository.getAllFiltered(
                b -> b.getIDTransfusionCenter() == userRepository.find(u -> u.getUsername().equals(center)).getId()
        );
    }

    private MedicalQuestionnaire getLastQuestionnaire(int userId) throws Exception {

        Optional<MedicalQuestionnaire> lastQuestionnaire =
                medicalQuestionnaireRepository.getAllFiltered(x -> x.getIdUser() == userId)
                        .stream()
                        .sorted((x, y) -> -x.getDate().compareTo(y.getDate()))
                        .findFirst();

        if (!lastQuestionnaire.isPresent())
            throw new Exception("You should first complete the donation questionnaire for the selected user!");

        return lastQuestionnaire.get();
    }


    private void checkIfCanDonate(int userId, Date newDonationDate) throws Exception {

        Optional<Donation> lastDonationDate =
                donationRepository.getAllFiltered(x -> x.getDonor().getId() == userId)
                        .stream()
                        .sorted((x, y) -> -x.getDonationDate().compareTo(y.getDonationDate()))
                        .findFirst();

        if (!lastDonationDate.isPresent()) return;

        Date lastDonation = java.sql.Date.valueOf(lastDonationDate.get().getDonationDate().toString().split(" ")[0]);

        if (DayCounter.getDaysBetween(lastDonation.toString(), newDonationDate.toString()) >= 30) return;

        throw new Exception("The user could not donate because he have not passed the 30-days no donation period!");
    }


    private BloodComponentQuantity createBloodComponentQuantity(BloodComponent type, DonorProfile donorProfile, Donation donation) {
        BloodComponentQuantity bloodComponentQuantity = new BloodComponentQuantity();
        bloodComponentQuantity.setBloodComponent(type);
        bloodComponentQuantity.setAboBloodGroup(donorProfile.getAboBloodGroup());
        bloodComponentQuantity.setRhBloodGroup(donorProfile.getRhBloodGroup());
        bloodComponentQuantity.setBloodStatus(
                donation.isHepatitis() || donation.isHIVorAIDS() || donation.isHTLV() || donation.isSyphilis() ?
                        BloodStatus.NotValid :
                        BloodStatus.Valid
        );
        bloodComponentQuantity.setIDdonation(donation.getID());
        bloodComponentQuantity.setIDrequest(0);
        bloodComponentQuantity.setIDTransfusionCenter(donation.getIdTransfusionCenter());


        Calendar calendar = new GregorianCalendar();
        calendar.setTime(donation.getDonationDate());

        switch (type) {
            case Plasma:
                calendar.add(Calendar.DATE, 90);
                break;
            case Leukocytes:
                calendar.add(Calendar.DATE, 5);
                break;
            case Thrombocytes:
                calendar.add(Calendar.DATE, 42);
                break;
        }

        System.out.println(calendar.getTime());
        bloodComponentQuantity.setExpirationDate(calendar.getTime());
        bloodComponentQuantity.setQuantity(BLOOD_COMPONENT_QUANTITY);

        return bloodComponentQuantity;
    }

    private List<BloodComponentQuantity> createBloodComponents(Donation donation) throws Exception {

        List<BloodComponentQuantity> components = new ArrayList<>();

        User user = donation.getDonor();

        DonorProfile donorProfile = donorProfileRepository.find(x -> x.getIdUser() == user.getId());

        components.addAll(Arrays.asList(
                createBloodComponentQuantity(BloodComponent.Leukocytes, donorProfile, donation),
                createBloodComponentQuantity(BloodComponent.Plasma, donorProfile, donation),
                createBloodComponentQuantity(BloodComponent.Thrombocytes, donorProfile, donation)
        ));

        for (BloodComponentQuantity bloodComponentQuantity : components)
            bloodComponentQuantityRepository.save(bloodComponentQuantity);

        return components;
    }

    private void addDonationHelper(Donation donation, int centerId) throws Exception {

        User donor = donation.getDonor();
        MedicalQuestionnaire recentUserQuestionnaire = getLastQuestionnaire(donor.getId());

        checkIfCanDonate(donor.getId(), donation.getDonationDate());

        donation.setDonationStatus(DonationStatus.Classification);
        donation.setDonor(donor);
        donation.setMedicalQuestionnaire(recentUserQuestionnaire);
        donation.setIdTransfusionCenter(centerId);

        donationRepository.save(donation);
        donation.setBloodComponents(createBloodComponents(donation));

        notifyNewHistoryContent(donor.getUsername());
    }

    @Override
    public void addDonation(String centerEmployeeUsername, Donation donation) throws Exception {

        String message = java.sql.Date.valueOf(LocalDate.now()).toString() + " - The new analysis have arrived.\n";

        try {
            int centerId = userRepository.find(x -> x.getUsername().equals(centerEmployeeUsername)).getId();
            addDonationHelper(donation, centerId);
        } catch (Exception e) {
            if (e.getMessage().contains("30-days")) {
                Optional<Donation> lastDonationDate =
                        donationRepository.getAllFiltered(x -> x.getDonor().getId() == userRepository.find(u -> u.getUsername().equals(donation.getDonor())).getId())
                                .stream()
                                .sorted((x, y) -> -x.getDonationDate().compareTo(y.getDonationDate()))
                                .findFirst();

                if (!lastDonationDate.isPresent()) throw e;

                Date lastDonation = java.sql.Date.valueOf(lastDonationDate.get().getDonationDate().toString().split(" ")[0]);
                throw new Exception("Could not insert donation, user not eligible. Days between last donation and this donation: " +
                        DayCounter.getDaysBetween(lastDonation.toString(), donation.getDonationDate().toString()));
            } else {
                throw e;
            }
        }

        notifyAnalysisFinished(donation.getDonor().getUsername(), message);
    }

    @Override
    public void updateDonation(String username, Donation donation) {

    }

    @Override
    public void updateBloodComponentQuantity(BloodComponentQuantity bloodBag) throws Exception {
        bloodComponentQuantityRepository.update(bloodBag.getID(), bloodBag);
    }

    @Override
    public List<DonorProfile> getDonorProfiles(String keyword) {
        return donorProfileRepository.getAllFiltered(p ->
                p.getFirstName()
                        .concat(" ")
                        .concat(p.getLastName())
                        .concat(" ")
                        .concat(p.getCNP())
                        .toLowerCase()
                        .contains(keyword.toLowerCase())
        );
    }

    @Override
    public BloodTransfusionCenterProfile getTransfusionCenterProfile(String username) {
        return bloodTransfusionCenterProfileRepository.find(b -> b.getIdUser() == userRepository.find(u -> u.getUsername().equals(username)).getId());
    }

    @Override
    public List<BloodTransfusionCenterProfile> getAllTransfusionCenterProfiles() {
        return bloodTransfusionCenterProfileRepository.getAll();
    }

    @Override
    public List<User> getAllByType(UserType type) {
        return userRepository.getAllFiltered(x -> x.getType().equals(type));
    }

    @Override
    public User getUserById(int userId) {
        return userRepository.getAllFiltered(x -> x.getId() == userId).get(0);
    }

    @Override
    public void removeNotificationFromDonor(String username, String message) {
        notifier.removeNotificationFromDonor(username, message);
    }

    @Override
    public int getDaysUntilNextDonationForDonor(String username, java.sql.Date currentDate) {
        int userId = userRepository.find(x -> x.getUsername().equals(username)).getId();

        Optional<Donation> lastDonationDate =
                donationRepository.getAllFiltered(x -> x.getDonor().getId() == userId)
                        .stream()
                        .sorted((x, y) -> -x.getDonationDate().compareTo(y.getDonationDate()))
                        .findFirst();

        if (!lastDonationDate.isPresent()) return 0;

        Date lastDonation = java.sql.Date.valueOf(lastDonationDate.get().getDonationDate().toString().split(" ")[0]);

        int intervalbetween = (int) DayCounter.getDaysBetween(lastDonation.toString(), currentDate.toString());
        return (DAYS_UNTIL_NEXT_DONATION - intervalbetween < 0 ? 0 : DAYS_UNTIL_NEXT_DONATION - intervalbetween);

    }

    @Override
    public int getAllDonationsForCenter(String username) {
        int centerId = userRepository.find(x -> x.getUsername().equals(username)).getId();
        return donationRepository.getAllFiltered(x -> x.getIdTransfusionCenter() == centerId).size();
    }

    @Override
    public int getNrNotifications(String username) {
        return notifier.getNrNotifications(username);
    }

    private void notifyAllObservers() {

        for (Map.Entry<String, IObserver> obs : loggedUsers.entrySet()) {
            try {
                obs.getValue().testUpdate();
            } catch (RemoteException e) {
                System.out.println("Impl->notifyAllObs->" + e.getMessage());
            }
        }
    }


    private void notifyNewHistoryContent(String username) {

        IObserver connectedClient = loggedUsers.get(username);

        if (connectedClient == null) return;

        try {
            connectedClient.notifyDonorUpdateHistory(username);
        } catch (Exception e) {
            System.out.println("NotifyNewHistoryContent->" + e.getMessage());
        }
    }

    private void notifyAnalysisFinished(String username, String content) {

        IObserver connectedClient = loggedUsers.get(username);

        if (connectedClient == null) {

            notifier.addMessage(username, content);
            System.out.println("MainServiceImpl -> Client cannot be notified(it will be later notified)! -> " + username);
            return;
        }

        try {
            notifier.addMessage(username, content);
            connectedClient.notifyDonorAnalyseFinished(username, content);
        } catch (RemoteException e) {
            System.out.println("notifyAnalysisFinished->" + e.getMessage());
        }
    }
}
