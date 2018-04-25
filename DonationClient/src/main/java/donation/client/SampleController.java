package donation.client;

import com.jfoenix.controls.JFXButton;
import donation.client.controllers.LoginController;
import donation.services.IMainService;
import donation.utils.IObserver;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class SampleController implements Initializable, IObserver {

    @FXML
    private Label labelUsers;

    @FXML
    private Label labelUsername;

    @FXML
    private JFXButton buttonLogout;

    private LoginController loginController;

    private IMainService mainService;

    private String userName;

    private Stage stage;

    public void setMainService(IMainService mainService, String userName, Stage stage) {
        this.mainService = mainService;
        labelUsername.setText("Username:" + userName);
        this.userName = userName;
        this.stage = stage;

        stage.setOnCloseRequest(ev -> handleLogoutEvent(null));
    }

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }


    private void handleLogoutEvent(ActionEvent e) {
        System.out.println("Logging out...");
        mainService.logout(userName, null);
        loginController.showLoginWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buttonLogout.setOnAction(this::handleLogoutEvent);
    }

    @Override
    public void addObserver(IObserver observer) throws RemoteException {

    }

    @Override
    public void testUpdate() throws RemoteException {
        Platform.runLater(() -> labelUsers.setText(labelUsers.getText() + " " + "+new user"));
    }
}
