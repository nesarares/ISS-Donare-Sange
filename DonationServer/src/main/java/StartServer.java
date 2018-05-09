import donation.model.BloodRequest;
import donation.model.User;
import donation.model.UserType;
import donation.persistence.repository.IRepository;
import donation.persistence.repository.Repository;
import donation.persistence.repository.RepositoryException;
import donation.persistence.repository.UserRepository;
import donation.server.MainServiceImpl;
import donation.server.utils.DayCounter;
import donation.services.IMainService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.Date;
import java.util.Optional;
import java.util.function.Predicate;

public class StartServer {

    public static void main(String[] args) {
        ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:spring-server.xml");
    }
}
