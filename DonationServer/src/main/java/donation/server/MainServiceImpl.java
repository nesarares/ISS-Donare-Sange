package donation.server;

import backup.Backup;
import donation.model.*;
import donation.model.validators.IValidator;
import donation.model.validators.ValidationException;
import donation.model.validators.ValidatorDonorProfile;
import donation.model.validators.ValidatorUser;
import donation.persistence.repository.IRepository;
import donation.persistence.repository.RepositoryException;
import donation.server.utils.DayCounter;
import donation.server.utils.OfflineNotifier;
import donation.server.utils.distance.calculator.RequestPlanner;
import donation.services.IMainService;
import donation.utils.IObserver;

import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class MainServiceImpl implements IMainService {

    private IRepository<User> userRepository;
    private IRepository<DonorProfile> donorProfileRepository;
    private IRepository<MedicalQuestionnaire> medicalQuestionnaireRepository;
    private IRepository<Donation> donationRepository;
    private IRepository<BloodComponentQuantity> bloodComponentQuantityRepository;
    private IRepository<BloodTransfusionCenterProfile> bloodTransfusionCenterProfileRepository;
    private IRepository<BloodRequest> bloodRequestRepository;
    private IRepository<DoctorProfile> doctorProfileRepository;

    private IValidator<User> userValidator = new ValidatorUser();
    private IValidator<DonorProfile> donorProfileValidator = new ValidatorDonorProfile();

    private Map<String, IObserver> loggedUsers = new ConcurrentHashMap<>();

    private Backup backup = Backup.getInstance();
    private OfflineNotifier notifier = new OfflineNotifier();
    private OfflineNotifier centerNotifier = new OfflineNotifier();
    private RequestPlanner<BloodTransfusionCenterProfile> requestPlanner = new RequestPlanner<>();
    private Map<BloodRequest, List<BloodTransfusionCenterProfile>> requestTracker = new HashMap<>();


    private final int DAYS_UNTIL_NEXT_DONATION = 30;
    private final int BLOOD_BAG_QUANTITY = 450;
    private final int BLOOD_COMPONENT_QUANTITY = BLOOD_BAG_QUANTITY / 3;
//    private final int BACKUP_TIME = 3600 * 24 * 1000;//seconds * hours * milliseconds
    private final int BACKUP_TIME = 7 * 1000;//seconds * hours * milliseconds


    private void backupAction() {

        while (true) {
            try {
                Thread.sleep(BACKUP_TIME);
                backup.doBackup();
            } catch (InterruptedException e) {
                System.out.println("BackupAction->" + e.getMessage());
            }
        }
    }

    private void addCenterLocations() {
        userRepository
                .getAllFiltered(x -> x.getType() == UserType.BloodTransfusionCenter)
                .forEach(x -> {
                    BloodTransfusionCenterProfile centerProfile = bloodTransfusionCenterProfileRepository
                            .find(y -> y.getIdUser() == x.getId());
                    requestPlanner.addNewLocation(centerProfile);
                });
    }

    private synchronized MedicalQuestionnaire getLastQuestionnaire(int userId) throws Exception {
        Optional<MedicalQuestionnaire> lastQuestionnaire =
                medicalQuestionnaireRepository.getAllFiltered(x -> x.getIdUser() == userId)
                        .stream()
                        .sorted((x, y) -> -x.getDate().compareTo(y.getDate()))
                        .findFirst();

        if (!lastQuestionnaire.isPresent())
            throw new Exception("You should first complete the donation questionnaire for the selected user!");

        return lastQuestionnaire.get();
    }

    private synchronized void checkIfCanDonate(int userId, Date newDonationDate) throws Exception {
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

        bloodComponentQuantity.setExpirationDate(calendar.getTime());
        bloodComponentQuantity.setQuantity(BLOOD_COMPONENT_QUANTITY);

        return bloodComponentQuantity;
    }

    private List<BloodComponentQuantity> createBloodComponents(Donation donation) throws Exception {
        User user = donation.getDonor();
        DonorProfile donorProfile = donorProfileRepository.find(x -> x.getIdUser() == user.getId());

        List<BloodComponentQuantity> components = new ArrayList<>(Arrays.asList(
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

    private void sendToAllCenters() {

        List<User> users = userRepository.getAll().stream().filter(x -> x.getType() == UserType.BloodTransfusionCenter).collect(Collectors.toList());

        for (User user : users) {
            IObserver center = loggedUsers.get(user.getUsername());
            if (center == null) {
                centerNotifier.addMessage(user.getUsername(), LocalDate.now() + " " + "A new blood request has arrived!");
                continue;
            }

            try {
                centerNotifier.addMessage(user.getUsername(), LocalDate.now() + " " + "A new blood request has arrived!");
                center.notifyNewRequestAdded(user.getUsername(), LocalDate.now() + " " + "A new blood request has arrived!");
            } catch (RemoteException e) {
                System.out.println("MainImpl->sendToAllCenters->" + e.getMessage());
            }
        }
    }

    private void sendOnlyToOneCenter(String centerUsername) {
        IObserver center = loggedUsers.get(centerUsername);

        if (center == null) {
            centerNotifier.addMessage(centerUsername, LocalDate.now() + " " + "A new blood request has arrived!");
            return;
        }

        try {
            centerNotifier.addMessage(centerUsername, LocalDate.now() + " " + "A new blood request has arrived!");
            center.notifyNewRequestAdded(centerUsername, LocalDate.now() + " " + "A new blood request has arrived!");
        } catch (RemoteException e) {
            System.out.println("MainImpl->sendOnlyToOneCenter->" + e.getMessage());
        }
    }

    private User getRequestReceiver(String doctorUsername) {
        String hospitalLocation = doctorProfileRepository.getAllFiltered(
                x -> x.getIdUser() == userRepository
                        .find(y -> y.getUsername().equals(doctorUsername))
                        .getId()
        ).get(0).getHospital();

        BloodTransfusionCenterProfile nearestCenter = requestPlanner.getNearestObjectsTo(hospitalLocation).get(0);
        return userRepository.findById(nearestCenter.getIdUser());
    }

    private void notifyNewBloodRequestAdded(String centerName) {
        if (centerName == null) {
            sendToAllCenters();
            return;
        }
        sendOnlyToOneCenter(centerName);
    }

    private BloodTransfusionCenterProfile getCenterProfile(String center) {

        return bloodTransfusionCenterProfileRepository.find(
                x -> x.getIdUser() == userRepository.find(
                        y -> y.getUsername().equals(center)
                ).getId()
        );

    }

    private void moveBloodRequest(BloodRequest request, BloodTransfusionCenterProfile toCenter) throws RepositoryException {

        User userTo = userRepository.find(
                usr -> usr.getId() == toCenter.getIdUser()
        );

        System.out.println("Before->" + bloodRequestRepository.find(x -> x.getID() == request.getID()).getReceiver().getUsername());
        request.setReceiver(userTo);

        System.out.println(userTo.getUsername());
        bloodRequestRepository.update(request.getID(), request);
        System.out.println("After->" + bloodRequestRepository.find(x -> x.getID() == request.getID()).getReceiver().getUsername());

        notifyNewBloodRequestAdded(userTo.getUsername());
    }

    private void sendToASpecificCenter(BloodRequest request, BloodTransfusionCenterProfile toCenter, BloodTransfusionCenterProfile fromCenter) throws Exception {
        moveBloodRequest(request, toCenter);
    }

    @SuppressWarnings("unchecked")
    private void sendRequestFromOneCenterToAnother(BloodTransfusionCenterProfile centerProfile, BloodRequest request) throws Exception {

        List<BloodTransfusionCenterProfile> nearestLocations = (List<BloodTransfusionCenterProfile>) requestPlanner.getNearestObjectsTo(centerProfile, false);

        List<BloodTransfusionCenterProfile> usedCenters = requestTracker.get(request);

        BloodTransfusionCenterProfile nextLocation = null;

        for (BloodTransfusionCenterProfile location : nearestLocations) {
            if (usedCenters.contains(location)) continue;
            nextLocation = location;
            break;
        }

        if (nextLocation == null) {
            requestTracker.get(request).clear();

            request.setBloodRequestStatus(BloodRequestStatus.Unresolved);
            bloodRequestRepository.update(request.getID(), request);

            sendBloodAlert();

            throw new Exception("The request could not be resolved!\nThere are no centers that have blood for: " + request.getPatientName());
        }

        sendToASpecificCenter(request, nextLocation, centerProfile);
    }

    private synchronized void sendBloodAlert() {
        List<User> donors = getAllByType(UserType.Donor);

        donors.forEach(user -> {
            IObserver loggedUser = loggedUsers.get(user.getUsername());
            notifier.addMessage(user.getUsername(), new Date() + " There is a blood alert!");

            if (loggedUser != null) {
                try {
                    loggedUser.notifyDonorAnalyseFinished(user.getUsername(), new Date() + " There is a blood alert!");
                } catch (RemoteException e) {
                    System.out.println("sendBlooadAlert() -> " + e.getMessage());
                }
            }
        });
    }

    private void reloadCenter(User receiver) {
        IObserver center = loggedUsers.get(receiver.getUsername());
        if (center == null) return;
        try {
            center.reloadCenterView();
        } catch (RemoteException e) {
            System.out.println("ReloadCenter() ->  " + e.getMessage());
        }
    }

    private void notifyRequestUpdated(User user) {

        IObserver doctor = loggedUsers.get(user.getUsername());

        if (doctor == null) return;

        try {
            doctor.reloadDoctorTables();
        } catch (RemoteException e) {
            System.out.println("Notify request update -> " + e.getMessage());
        }

    }


    public int getBloodComponentQuantity() {
        return BLOOD_COMPONENT_QUANTITY;
    }

    public MainServiceImpl(IRepository<User> userIRepository,
                           IRepository<DonorProfile> donorProfileRepository,
                           IRepository<MedicalQuestionnaire> medicalQuestionnaireRepository,
                           IRepository<Donation> donationIRepository,
                           IRepository<BloodComponentQuantity> bloodComponentQuantityRepository,
                           IRepository<BloodTransfusionCenterProfile> bloodTransfusionCenterProfileRepository,
                           IRepository<BloodRequest> bloodRequestRepository,
                           IRepository<DoctorProfile> doctorProfileRepository) {

        this.userRepository = userIRepository;
        this.donorProfileRepository = donorProfileRepository;
        this.medicalQuestionnaireRepository = medicalQuestionnaireRepository;
        this.donationRepository = donationIRepository;
        this.bloodComponentQuantityRepository = bloodComponentQuantityRepository;
        this.bloodTransfusionCenterProfileRepository = bloodTransfusionCenterProfileRepository;
        this.bloodRequestRepository = bloodRequestRepository;
        this.doctorProfileRepository = doctorProfileRepository;

        addCenterLocations();

        new Thread(this::backupAction).start();

    }


    @Override
    public synchronized boolean login(String username, String password, IObserver observer) throws Exception {

        User user = userRepository.find(x -> x.getUsername().equals(username));
        if (user == null || !user.getPassHash().equals(password)) return false;
        if (loggedUsers.get(username) != null) throw new Exception("User is already connected :(");

        loggedUsers.put(username, observer);

        if (notifier.hasUndeliveredMessages(username)) {
            notifier.sendUndeliveredMessages(username, this::notifyAnalysisFinished);
        }

        if (!centerNotifier.hasUndeliveredMessages(username)) return true;

        centerNotifier.sendUndeliveredMessages(username, (u, m) -> {

            IObserver center = loggedUsers.get(u);

            if (center == null) {
                return;
            }

            try {
                center.notifyNewRequestAdded(u, m);
            } catch (Exception e) {
                System.out.println("Login->" + e.getMessage());
            }

        });


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
    public synchronized void logout(String username, IObserver observer) {
        if (loggedUsers.get(username) == null) return;

        System.out.println("User logged out: " + username);
        loggedUsers.remove(username);
    }

    @Override
    public synchronized UserType getUserType(String username) {
        User user = userRepository.find(x -> x.getUsername().equals(username));
        if (user == null) return null;
        return user.getType();
    }

    @Override
    public synchronized DonorProfile getProfile(String username) {
        int idUser = userRepository.find(u -> u.getUsername().equals(username)).getId();
        return donorProfileRepository.find(p -> p.getIdUser() == idUser);
    }

    @Override
    public synchronized List<Donation> getHistory(String username) {
        return donationRepository.getAllFiltered(x -> x.getDonor().getUsername().equals(username));
    }

    @Override
    public synchronized void updateProfile(String username, DonorProfile profile) throws Exception {
        donorProfileValidator.validate(profile);
        int id = userRepository.find(u -> u.getUsername().equals(username)).getId();
        profile.setIdUser(id);

        int oldProfileId = donorProfileRepository.find(d -> d.getIdUser() == id).getID();
        donorProfileRepository.update(oldProfileId, profile);
    }

    @Override
    public synchronized void addMedicalQuestionnaire(MedicalQuestionnaire questionnaire) throws RepositoryException {
        medicalQuestionnaireRepository.save(questionnaire);
    }

    @Override
    public synchronized List<BloodComponentQuantity> getBloodStock(String center) {
        return bloodComponentQuantityRepository.getAllFiltered(
                b -> b.getIDTransfusionCenter() == userRepository
                        .find(u -> u.getUsername().equals(center)).getId() && b.getQuantity() > 0
        );
    }

    @Override
    public synchronized void addDonation(String centerEmployeeUsername, Donation donation) throws Exception {
        String message = java.sql.Date.valueOf(LocalDate.now()).toString() + " - The new analysis have arrived.\n";

        try {
            int centerId = userRepository.find(x -> x.getUsername().equals(centerEmployeeUsername)).getId();
            addDonationHelper(donation, centerId);
        } catch (Exception e) {
            if (e.getMessage().contains("30-days")) {
                Optional<Donation> lastDonationDate =
                        donationRepository.getAllFiltered(don ->
                                don.getDonor().getId() == userRepository.find(u -> u.getUsername().equals(donation.getDonor().getUsername())).getId()
                        )
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
    public synchronized void updateBloodComponentQuantity(BloodComponentQuantity bloodBag) throws Exception {
        bloodComponentQuantityRepository.update(bloodBag.getID(), bloodBag);
    }

    @Override
    public synchronized List<DonorProfile> getDonorProfiles(String keyword) {
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
    public synchronized List<BloodRequest> getBloodRequestsCenter(String username) {
        return bloodRequestRepository.getAllFiltered(request ->
                (request.getReceiver() == null ||
                        request.getReceiver().getUsername().equals(username)) &&
                        request.getBloodRequestStatus() != BloodRequestStatus.Completed);

    }

    @Override
    public synchronized List<User> getAllByType(UserType type) {
        return userRepository.getAllFiltered(x -> x.getType().equals(type));
    }

    @Override
    public User getUserById(int userId) {
        return userRepository.getAllFiltered(x -> x.getId() == userId).get(0);
    }

    @Override
    public synchronized void removeNotificationFromDonor(String username, String message) {
        if (userRepository.find(x -> x.getUsername().equals(username)).getType() == UserType.BloodTransfusionCenter) {
            centerNotifier.removeNotificationFromDonor(username, message);
            return;
        }

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

    @Override
    public synchronized void addBloodRequest(BloodRequest request, String username) throws Exception {

        User nearestCenter = getRequestReceiver(username);

        request.setSender(userRepository.find(u -> u.getUsername().equals(username)));
        request.setBloodRequestStatus(BloodRequestStatus.Waiting);
        request.setDateRequested(new Date());
        request.setReceiver(nearestCenter);//for all centers

        bloodRequestRepository.save(request);
        notifyNewBloodRequestAdded(nearestCenter.getUsername());//for all centers
    }

    @Override
    public List<BloodRequest> getBloodRequestsDoctor(String username) {
        return bloodRequestRepository.getAllFiltered(request -> request.getSender().getUsername().equals(username));
    }

    @Override
    public DoctorProfile getDoctorProfile(String username) {
        return doctorProfileRepository
                .find(profile ->
                        profile.getIdUser() == userRepository
                                .find(user -> user
                                        .getUsername()
                                        .equals(username))
                                .getId());
    }

    @Override
    public synchronized void sendRequestToAnotherCenter(BloodRequest bloodRequest, String fromCenter) throws Exception {

        BloodTransfusionCenterProfile fromCenterProfile = getCenterProfile(fromCenter);

        System.out.println(bloodRequest.getID());
        requestTracker.computeIfAbsent(bloodRequest, k -> new ArrayList<>());//if the request does not exist on server=> we add it
        requestTracker.get(bloodRequest).add(fromCenterProfile);

        sendRequestFromOneCenterToAnother(fromCenterProfile, bloodRequest);
    }

    @Override
    public synchronized BloodRequest addComponentToRequest(BloodRequest request, BloodComponentQuantity bloodComponent) throws Exception {

        switch (bloodComponent.getBloodComponent()) {

            case Leukocytes: {
                int addedQuantity = Math.min(
                        request.getLeukocytesQuantity(),
                        bloodComponent.getQuantity()
                );
                request.setLeukocytesQuantity(
                        request.getLeukocytesQuantity() - addedQuantity
                );
                bloodComponent.setQuantity(
                        bloodComponent.getQuantity() - addedQuantity
                );
                break;
            }

            case Thrombocytes: {

                int addedQuantity = Math.min(
                        request.getThrombocytesQuantity(),
                        bloodComponent.getQuantity()
                );

                request.setThrombocytesQuantity(
                        request.getThrombocytesQuantity() - addedQuantity
                );

                bloodComponent.setQuantity(
                        bloodComponent.getQuantity() - addedQuantity
                );

                break;
            }

            case Plasma: {

                int addedQuantity = Math.min(
                        request.getPlasmaQuantity(),
                        bloodComponent.getQuantity()
                );

                request.setPlasmaQuantity(
                        request.getPlasmaQuantity() - addedQuantity
                );

                bloodComponent.setQuantity(
                        bloodComponent.getQuantity() - addedQuantity
                );

                break;
            }
        }

        bloodComponent.setIDrequest(request.getID());
        bloodComponentQuantityRepository.update(bloodComponent.getID(), bloodComponent);
        bloodRequestRepository.update(request.getID(), request);

        if (request.getThrombocytesQuantity() == 0 && request.getPlasmaQuantity() == 0 && request.getLeukocytesQuantity() == 0) {
            request.setBloodRequestStatus(BloodRequestStatus.Completed);
            bloodRequestRepository.update(request.getID(), request);
            if (requestTracker.get(request) != null) requestTracker.get(request).clear();
        }

        notifyRequestUpdated(request.getSender());
        reloadCenter(request.getReceiver());
        return request;
    }

    @Override
    public synchronized List<BloodComponentQuantity> getBloodComponentsByRequest(BloodRequest request) {

        List<BloodComponentQuantity> initialList = bloodComponentQuantityRepository.getAllFiltered(x -> x.getIDrequest() == request.getID());
        initialList.forEach(x -> x.setQuantity(BLOOD_COMPONENT_QUANTITY - x.getQuantity()));
        return initialList;

    }

}
