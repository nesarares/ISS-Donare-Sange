import donation.model.User;
import donation.model.UserType;
import donation.persistence.repository.IRepository;
import donation.persistence.repository.MockUserRepository;
import donation.persistence.repository.Repository;
import donation.server.MainServiceImpl;
import donation.services.IMainService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Optional;

public class StartServer {

    public static void main(String[] args) {

        //ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:spring-server.xml");
        try {
            Repository.add(User.class, new User(1, "ariadna", "passHash", UserType.Admin));
            Optional<User> user = Repository.get(User.class, 1);
            System.out.println(user);
            if (user.isPresent()) {
                Repository.update(User.class, user.get(), new User(1, "ariadna roman", "passHash", UserType.Admin));
                System.out.println(Repository.getAll(User.class).size());
                Repository.delete(User.class, user.get());
                System.out.println(Repository.getAll(User.class).size());
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }
}
