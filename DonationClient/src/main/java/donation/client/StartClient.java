package donation.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StartClient extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
//        ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:spring-client.xml");

        primaryStage.setTitle("Donare de sange");
        primaryStage.getIcons().add(new Image("donation/client/icon.png"));

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("./views/LoginView.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
