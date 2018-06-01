package donation.services;

import donation.model.BloodComponentQuantity;
import donation.model.BloodRequest;
import donation.model.User;
import donation.model.UserType;

import java.sql.Date;
import java.time.LocalDate;

import java.util.List;

public interface IMainService extends IServiceAuthentication, IServiceDoctor, IServiceDonor, IServiceScientist {

    int getBloodComponentQuantity();

    int getDaysUntilNextDonationForDonor(String username, Date currentDate);

    int getAllDonationsForCenter(String username);

    int getNrNotifications(String username);

    void removeNotificationFromDonor(String username, String message);

    void sendRequestToAnotherCenter(BloodRequest bloodRequest, String fromCenter) throws Exception;

    User getUserById(int userId);

    BloodRequest addComponentToRequest(BloodRequest request, BloodComponentQuantity bloodComponent) throws Exception;

    List<BloodComponentQuantity> getBloodComponentsByRequest(BloodRequest request);

    List<User> getAllByType(UserType type);
}
