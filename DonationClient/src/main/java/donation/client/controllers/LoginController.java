package donation.client.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

//    private LoginService loginService;

    @FXML
    private JFXTextField textFieldUsername;

    @FXML
    private JFXPasswordField textFieldPassword;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void loginHandler(ActionEvent ev) {
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
