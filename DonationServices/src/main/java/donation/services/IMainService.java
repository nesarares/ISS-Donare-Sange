package donation.services;

import donation.model.User;
import donation.model.UserType;

import java.sql.Date;
import java.time.LocalDate;

import java.util.List;

public interface IMainService extends  IServiceAuthentication,IServiceDoctor,IServiceDonor,IServiceScientist  {

    List<User> getAllByType(UserType type);

    User getUserById(int userId);

    void removeNotificationFromDonor(String username,String message);

    int getDaysUntilNextDonationForDonor(String username, Date currentDate);

    int getAllDonationsForCenter(String username);

    int getNrNotifications(String username);
}
