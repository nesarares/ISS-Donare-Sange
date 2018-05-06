package donation.server;

import donation.model.*;
import donation.model.validators.IValidator;
import donation.model.validators.ValidationException;
import donation.model.validators.ValidatorDonorProfile;
import donation.model.validators.ValidatorUser;
import donation.persistence.repository.IRepository;
import donation.services.IMainService;
import donation.utils.IObserver;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

public class MainServiceImpl implements IMainService {

    private IRepository<User> userRepository;
    private IRepository<DonorProfile> donorProfileRepository;

    private IValidator<User> userValidator = new ValidatorUser();
    private IValidator<DonorProfile> donorProfileValidator = new ValidatorDonorProfile();

    private Map<String, IObserver> loggedUsers = new ConcurrentHashMap<>();

    public MainServiceImpl(IRepository<User> userIRepository, IRepository<DonorProfile> donorProfileRepository) {

        this.userRepository = userIRepository;
        this.donorProfileRepository = donorProfileRepository;
    }

    @Override
    public synchronized boolean login(String username, String password, IObserver observer) throws Exception {

        User user = userRepository.find(x -> x.getUsername().equals(username));

        if (user == null || !user.getPassHash().equals(password)) return false;
        if (loggedUsers.get(username) != null) throw new Exception("User is already connected :(");

        loggedUsers.put(username, observer);

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

        //todo view all conncted
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
    public List<Donation> getHistory() {
        return null;
    }

    @Override
    public void updateProfile(String username, DonorProfile profile) throws Exception {

        donorProfileValidator.validate(profile);
        int id = userRepository.find(u -> u.getUsername().equals(username)).getId();
        profile.setIdUser(id);

        int oldProfileId = donorProfileRepository.find(d->d.getIdUser() == id).getID();
        donorProfileRepository.update(oldProfileId, profile);
    }

    @Override
    public void addMedicalQuestionnaire(MedicalQuestionnaire questionnaire) {

    }

    @Override
    public List<BloodComponentQuantity> getBloodStock(Predicate<BloodComponentQuantity> bloodComponentFilter) {
        return null;
    }

    @Override
    public void updateEnvoyedBloodBagStatus() {

    }

    @Override
    public void addDonation(String username, Donation donation) {

    }

    @Override
    public void updateDonation(String username, Donation donation) {

    }

    @Override
    public List<User> getAllByType(UserType type) {
        return userRepository.getAllFiltered(x -> x.getType().equals(type));
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
}
