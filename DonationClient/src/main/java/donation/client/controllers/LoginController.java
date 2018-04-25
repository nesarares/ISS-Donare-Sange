package donation.client.controllers;

import com.jfoenix.controls.*;
import donation.client.utils.Timer;
import donation.client.utils.animations.BounceInLeftTransition;
import donation.client.utils.animations.BounceOutLeftTransition;
import donation.services.IMainService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

//    private LoginService loginService;
    private ControllerRoot controllerRoot;
    private IMainService mainService;

    @FXML
    private JFXTextField textFieldUsername;
    @FXML
    private JFXPasswordField textFieldPassword;
    @FXML
    private AnchorPane anchorPaneRegistration;
    @FXML
    private JFXTextField textFieldFirstName, textFieldLastName, textFieldCNP,
            textFieldEmail, textFieldPhone, textFieldWeight,
            textFieldHeight, textFieldNationality;
    @FXML
    private JFXDatePicker datePickerBirthDate;
    @FXML
    private JFXTextArea textAreaHomeAddress, textAreaResidenceAddress;
    @FXML
    private JFXCheckBox checkBoxResidence;

    private Stage mainStage;

    private Parent mainWindowView;

    public LoginController() {
        //todo functia logincontroler va fi elimnata la urma
        System.out.println("se apleeaza");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        anchorPaneRegistration.setVisible(false);
        try {
            controllerRoot = new ControllerRoot();
        } catch (RemoteException e) {
            System.out.println("LoginControler->constructor" + e.getMessage());
        }
    }

    public void setMainService(IMainService mainService) {
        this.mainService = mainService;
    }

    private void clearFields() {
        textFieldFirstName.clear();
        textFieldLastName.clear();
        textFieldCNP.clear();
        textFieldEmail.clear();
        textFieldPhone.clear();
        textFieldWeight.clear();
        textFieldHeight.clear();
        textFieldNationality.clear();
        datePickerBirthDate.setValue(null);
        textAreaHomeAddress.clear();
        textAreaResidenceAddress.clear();
    }

    @FXML
    public void handleRegistration(ActionEvent actionEvent) {
        clearFields();
        Timer.setTimeout(() -> anchorPaneRegistration.setVisible(true), 100);
        new BounceInLeftTransition(anchorPaneRegistration).play();
    }

    @FXML
    public void handleBack(ActionEvent actionEvent) {
        BounceOutLeftTransition trans = new BounceOutLeftTransition(anchorPaneRegistration);
        trans.setOnFinished((ev) -> anchorPaneRegistration.setVisible(false));
        trans.play();
    }

    private void loadMainView() {
        Parent mainView = null;
        Stage mainStage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/DonorView.fxml"));
            mainView = loader.load();

//            controllerRoot.addObserver(controller);
            DonorController controller = loader.getController();
//            controllerRoot.addObserver(controller);

            Scene scene = new Scene(mainView);
            mainStage.setScene(scene);
            this.mainStage.hide();
            mainStage.show();
            controller.setLoginController(this);
            controller.setMainService(mainService, textFieldUsername.getText(), mainStage);
        } catch (Exception e) {
            System.out.println("loadMainView->" + e.getMessage());
        }
    }

    @FXML
    private void handleLogin(ActionEvent actionEvent) {
        String username = textFieldUsername.getText();
        String password = textFieldPassword.getText();

        try {
            if (mainService.login(username, password, controllerRoot)) {
                loadMainView();
                return;
            }
            Alert msg = new Alert(Alert.AlertType.INFORMATION, "Log in unsuccessfully");
            msg.showAndWait();
        } catch (Exception e) {
            Alert msg = new Alert(Alert.AlertType.INFORMATION, e.getMessage());
            msg.showAndWait();
        }
    }

    public void loadLoginWindow(Parent show, Stage primaryStage) {
        mainStage = primaryStage;
        mainWindowView = show;
        Scene scene = new Scene(mainWindowView);
        mainStage.setScene(scene);
        mainStage.show();
    }

    public void showLoginWindow() {
        mainStage.show();
    }
}
