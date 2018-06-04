import donation.model.BloodComponentQuantity;
import donation.persistence.repository.BloodComponentQuantityRepository;
import donation.persistence.repository.IRepository;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.logging.Level;
import java.util.logging.Logger;

public class StartServer {

    public static void main(String[] args) throws Exception {
        Logger.getLogger("org.hibernate").setLevel(Level.OFF);
        new ClassPathXmlApplicationContext("classpath:spring-server.xml");
    }
}
