package donation.services;

import donation.model.User;
import donation.model.UserType;

import java.util.List;

public interface IMainService extends  IServiceAuthentication,IServiceDoctor,IServiceDonor,IServiceScientist  {

    List<User> getAllByType(UserType type);

    User getUserById(int userId);

    void removeNotificationFromDonor(String username,String message);
}
