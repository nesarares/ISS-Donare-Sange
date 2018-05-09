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
    private IRepository <Donation> donationRepository;

    private IValidator<User> userValidator = new ValidatorUser();
    private IValidator<DonorProfile> donorProfileValidator = new ValidatorDonorProfile();

    private Map<String, IObserver> loggedUsers = new ConcurrentHashMap<>();

    private OfflineNotifier notifier = new OfflineNotifier();

    public MainServiceImpl(IRepository<User> userIRepository, IRepository<DonorProfile> donorProfileRepository,IRepository <MedicalQuestionnaire> medicalQuestionnaireRepository,IRepository <Donation> donationIRepository) {

        this.userRepository = userIRepository;
        this.donorProfileRepository = donorProfileRepository;
        this.medicalQuestionnaireRepository = medicalQuestionnaireRepository;
        this.donationRepository = donationIRepository;
    }

    @Override
    public synchronized boolean login(String username, String password, IObserver observer) throws Exception {

        User user = userRepository.find(x -> x.getUsername().equals(username));

        if (user == null || !user.getPassHash().equals(password)) return false;
        if (loggedUsers.get(username) != null) throw new Exception("User is already connected :(");

        loggedUsers.put(username, observer);

        if(notifier.hasUndeliveredMessages(username))notifier.sendUndeliveredMessages(username,this::notifyAnalysisFinished);
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
    public void addProfile(String firstName, String lastName, Date birthDate, String address, String nationality, String email, String phone) {

    }

    @Override
    public DonorProfile getProfile(String username) {
        int idUser = userRepository.find(u -> u.getUsername().equals(username)).getId();
        return donorProfileRepository.find(p -> p.getIdUser() == idUser);
    }

    @Override
    public List<Donation> getHistory(String username) {
        return donationRepository.getAllFiltered(x->x.getDonor().getUsername().equals(username));
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
    public List<BloodComponentQuantity> getBloodStock(Predicate<BloodComponentQuantity> bloodComponentFilter) {
        return null;
    }

    @Override
    public void updateEnvoyedBloodBagStatus() {

    }


    private MedicalQuestionnaire getLastQuestionnaire(int userId) throws  Exception{

        Optional <MedicalQuestionnaire> lastQuestionnaire =
                medicalQuestionnaireRepository.getAllFiltered(x->x.getIdUser() == userId)
                        .stream()
                        .sorted((x,y)->-x.getDate().compareTo(y.getDate()))
                        .findFirst();

        if(!lastQuestionnaire.isPresent())throw new Exception("You should first complete the donation questionnaire for the selected user!");

        return lastQuestionnaire.get();
    }
    
    
    private void  checkIfCanDonate (int userId,Date newDonationDate) throws  Exception{
        
        Optional <Donation> lastDonationDate =
                donationRepository.getAllFiltered(x->x.getDonor().getId() == userId)
                        .stream()
                        .sorted((x,y)->-x.getDonationDate().compareTo(y.getDonationDate()))
                        .findFirst();

        if(!lastDonationDate.isPresent())return;

        Date lastDonation = java.sql.Date.valueOf(lastDonationDate.get().getDonationDate().toString().split(" ")[0]);

        if(DayCounter.getDaysBetween(lastDonation.toString(),newDonationDate.toString()) >= 30)return;

        throw new Exception("The user could not donate because he have not passed the 30-days no donation period!");
    }

    private void addDonationHelper(Donation donation) throws  Exception{
        //todo getlast donation,verify if the number of days is bigger 30 add into database refresh the tables

        User donor = donation.getDonor();
        MedicalQuestionnaire recentUserQuestionnaire = getLastQuestionnaire(donor.getId());

        checkIfCanDonate(donor.getId(),donation.getDonationDate());

        donation.setDonationStatus(DonationStatus.Classification);
        donation.setDonor(donor);
        donation.setMedicalQuestionnaire(recentUserQuestionnaire);

        donationRepository.save(donation);

        notifyNewHistoryContent(donor.getUsername());
    }

    @Override
    public void addDonation(String centerEmployeeUsername, Donation donation) throws  Exception {

        String message = java.sql.Date.valueOf(LocalDate.now()).toString() + " - The new analysis have arrived.\n";
        String errors = "";

        if(donation.isHepatitis())message += "Hepatitis ";

        if(donation.isHIVorAIDS())message += "HivOrAIDS ";

        if(donation.isSyphilis())message += "Syphilis";

        if(donation.isHTLV())message += "HTLV";

        //todo add in database the donation

        if(!donation.isHTLV() && !donation.isHIVorAIDS() && !donation.isSyphilis() && !donation.isHepatitis()){

            try {
                addDonationHelper(donation);
            }catch (Exception e){
                errors += e.getMessage();

                if(errors.contains("30-days"))notifyAnalysisFinished(
                        donation.getDonor().getUsername(),
                        LocalDate.now().toString().split(" ")[0] + " - " + "You cannot donate right now!.You should wait until the 30-days-period expires"
                );
            }
        }
        else{
            notifyAnalysisFinished(donation.getDonor().getUsername(),message);//user
            throw new  Exception("Donation could not be added because the selected donor does not satisfy donation requirements!");
        }

        if(!errors.equals(""))throw new Exception(errors);

        notifyAnalysisFinished(donation.getDonor().getUsername(),message);
    }

    @Override
    public void updateDonation(String username, Donation donation) {

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
    public List<User> getAllByType(UserType type) {
        return userRepository.getAllFiltered(x -> x.getType().equals(type));
    }

    @Override
    public User getUserById(int userId) {
        return userRepository.getAllFiltered(x->x.getId() == userId).get(0);
    }

    @Override
    public void removeNotificationFromDonor(String username, String message) {
        notifier.removeNotificationFromDonor(username,message);
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


    private void notifyNewHistoryContent(String username){

        IObserver connectedClient = loggedUsers.get(username);

        if(connectedClient == null)return;

        try{
            connectedClient.notifyDonorUpdateHistory(username);
        }catch (Exception e){
            System.out.println("NotifyNewHistoryContent->" + e.getMessage());
        }
    }

    private void notifyAnalysisFinished(String username,String content){

        IObserver connectedClient  = loggedUsers.get(username);

        if(connectedClient == null){
            notifier.addMessage(username,content);
            System.out.println("MainServiceImpl -> Client cannot be notified(it will be later notified)! -> " + username);
            return;
        }

        try {
            connectedClient.notifyDonorAnalyseFinished(username,content);
        } catch (RemoteException e) {
            System.out.println("notifyAnalysisFinished->" + e.getMessage());
        }
    }
}
