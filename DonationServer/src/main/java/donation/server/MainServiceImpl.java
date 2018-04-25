package donation.server;

import donation.model.*;
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

    //loginService
    //todo de inlocuit mock cu ale noastre

    private IRepository <User> userRepository;

    private Map<String,IObserver> loggedUsers = new ConcurrentHashMap<>();

    public MainServiceImpl(IRepository<User> userIRepository) {
        this.userRepository = userIRepository;
    }

    @Override
    public boolean login(String username, String password, IObserver observer) throws  Exception {

        User user = userRepository.find(x->x.getUsername().equals(username));

        if(user == null || !user.getPassHash().equals(password))return false;

        if(loggedUsers.get(username) != null)throw  new Exception("User is already connected :(");

        loggedUsers.put(username,observer);

        notifyAllObservers();

        return true;
    }

    @Override
    public void addNewUser(String username, String password, UserType userType) throws Exception {

        //note am pus random pt test,in baza de date va fi autoincrement
        User user = new User(new Random().nextInt(100),username,password,userType);

        if(userRepository.find(x->x.getUsername().equals(username)) != null)throw  new Exception("User already exists");

        userRepository.save(user);
    }

    @Override
    public void addNewUser(String username, UserType userType) throws  Exception {

    }

    @Override
    public void updatePassword(String username, String newPassword) {

    }

    @Override
    public void logout(String username, IObserver observer) {

        if(loggedUsers.get(username) == null)return;

        System.out.println("user logged out" +  username);
        loggedUsers.remove(username);

        //todo view all conncted
    }





    @Override
    public void sendBloodRequest(BloodRequest request) {

    }

    @Override
    public void addProfile(String firstName, String lastName, Date birthDate, String address, String nationality, String email, String phone) {

    }

    @Override
    public DonorProfile getProfile() {

        System.out.println("ba est");
        return null;
    }

    @Override
    public List<Donation> getHistory() {
        return null;
    }

    @Override
    public void updateProfile(String username, DonorProfile profile) {

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
        return userRepository.getAllFiltered(x->x.getType().equals(type));
    }

    private void  notifyAllObservers(){

        for(Map.Entry<String,IObserver> obs : loggedUsers.entrySet()){
            try {
                obs.getValue().testUpdate();
            } catch (RemoteException e) {
                System.out.println("Impl->notifyAllObs->" + e.getMessage());
            }
        }
    }
}
