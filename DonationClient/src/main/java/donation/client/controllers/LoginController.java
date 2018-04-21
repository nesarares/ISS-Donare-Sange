package donation.client.controllers;

import com.jfoenix.controls.*;
import donation.client.utils.Timer;
import donation.client.utils.animations.BounceInLeftTransition;
import donation.client.utils.animations.BounceOutLeftTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

//    private LoginService loginService;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        anchorPaneRegistration.setVisible(false);
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

    @FXML
    private void handleLogin(ActionEvent actionEvent) {
//        String username = this.textFieldUsername.getText();
//        String password = this.textFieldPassword.getText();
//        System.out.println(username);
//        System.out.println(password);
//        if (username.equals("") || password.equals("")) {
//            Alert msg = new Alert(Alert.AlertType.WARNING);
//            msg.setTitle("Something went wrong...");
//            msg.setHeaderText("Both username and password fields must be filled");
//            msg.showAndWait();
//            return;
//        }
//        try {
//            if (this.loginService.login(username, password), )) {
//                Alert msg = new Alert(Alert.AlertType.WARNING);
//                msg.setTitle("Success");
//                msg.setHeaderText("Success");
//                msg.showAndWait();
//            }
//        } catch (Exception ex) {
//            Alert msg = new Alert(Alert.AlertType.WARNING);
//            msg.setTitle("Something went wrong...");
//            msg.setHeaderText(ex.getMessage());
//            msg.setContentText(ex.getMessage());
//            msg.showAndWait();
//        }
    }
}
