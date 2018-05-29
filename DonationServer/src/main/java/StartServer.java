import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StartServer {

    public static void main(String[] args) {

        new ClassPathXmlApplicationContext("classpath:spring-server.xml");
    }
}
