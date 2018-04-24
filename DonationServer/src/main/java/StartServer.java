import donation.model.User;
import donation.model.UserType;
import donation.persistence.repository.IRepository;
import donation.persistence.repository.MockUserRepository;
import donation.server.MainServiceImpl;
import donation.services.IMainService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StartServer {

    public static void main(String[] args) {

        ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:spring-server.xml");

//        IRepository<Integer,User> repository = new MockUserRepository();
//
//        IMainService service = new MainServiceImpl(repository);
//
//        service.getAllByType(UserType.Admin).forEach(System.out::println);

    }
}