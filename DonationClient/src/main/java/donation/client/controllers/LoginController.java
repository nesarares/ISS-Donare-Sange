package donation.client.controllers;

import com.jfoenix.controls.*;
import donation.client.utils.GUIUtils;
import donation.client.utils.Timer;
import donation.client.utils.animations.BounceInLeftTransition;
import donation.client.utils.animations.BounceOutLeftTransition;
import donation.model.DonorProfile;
import donation.model.UserType;
import donation.services.IMainService;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.net.URL;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private StackPane stackPaneContent;

    @FXML
    private AnchorPane anchorPaneRegistration;

    @FXML
    private JFXCheckBox checkBoxResidence;

    @FXML
    private JFXDatePicker datePickerBirthDate;

    @FXML
    private JFXTextField textFieldUsername;
    @FXML
    private JFXTextField textFieldFirstName, textFieldLastName, textFieldCNP,
            textFieldEmail, textFieldPhone, textFieldWeight,
            textFieldHeight, textFieldNationality, textFieldRegisterUsername, textFieldRegisterPassword;

    @FXML
    private JFXTextArea textAreaHomeAddress, textAreaResidenceAddress;

    @FXML
    private JFXPasswordField textFieldPassword;

    @FXML
    private JFXButton buttonLogin;


    private Stage mainStage;
    private Parent mainWindowView;
    private ControllerRoot controllerRoot;
    private IMainService mainService;


    @FXML
    private void handleSignUp(ActionEvent actionEvent) {
        DonorProfile donorProfile = new DonorProfile();

        donorProfile.setFirstName(textFieldFirstName.getText());
        donorProfile.setLastName(textFieldLastName.getText());
        donorProfile.setCNP(textFieldCNP.getText());

        donorProfile.setBirthDate(null);
        if (datePickerBirthDate.getValue() != null)
            donorProfile.setBirthDate(Date.valueOf(datePickerBirthDate.getValue()));

        donorProfile.setEmail(textFieldEmail.getText());
        donorProfile.setPhone(textFieldPhone.getText());

        try {
            donorProfile.setWeight(Float.parseFloat(textFieldWeight.getText().equals("") ? "0.0" : textFieldWeight.getText()));
        } catch (NumberFormatException ex) {
            donorProfile.setWeight(0);
        }
        try {
            donorProfile.setHeight(Integer.parseInt(textFieldHeight.getText().equals("") ? "0" : textFieldHeight.getText()));
        } catch (NumberFormatException ex) {
            donorProfile.setHeight(0);
        }
        donorProfile.setNationality(textFieldNationality.getText());
        donorProfile.setAddress(textAreaHomeAddress.getText());
        donorProfile.setResidence(donorProfile.getAddress());
        if (checkBoxResidence.isSelected()) donorProfile.setResidence(textAreaHomeAddress.getText());

        try {
            mainService.addNewUser(textFieldRegisterUsername.getText(), textFieldRegisterPassword.getText(), UserType.Donor, donorProfile);
            GUIUtils.showDialogMessage(Alert.AlertType.INFORMATION, "Success", "Account registered successfully!", stackPaneContent);
            handleBack(null);
        } catch (Exception e) {
            GUIUtils.showDialogMessage(Alert.AlertType.ERROR, "Error", e.getMessage(), stackPaneContent);
        }
    }

    @FXML
    private void handleLogin(ActionEvent actionEvent) {
        String username = textFieldUsername.getText();
        String password = textFieldPassword.getText();


        buttonLogin.setDisable(true);
        textFieldUsername.setDisable(true);
        textFieldPassword.setDisable(true);
        new Thread(()->{
            try {
                if (mainService.login(username, password, controllerRoot)) {
                    loadMainView(mainService.getUserType(username));
                    return;
                }
                Platform.runLater(()->GUIUtils.showDialogMessage(Alert.AlertType.ERROR, "Unsuccessful", "Username or password are invalid", stackPaneContent));
            } catch (Exception e) {
                Platform.runLater(()->GUIUtils.showDialogMessage(Alert.AlertType.ERROR, "Unsuccessful", e.getMessage(), stackPaneContent));
            }
            finally {
                Platform.runLater(()->{
                    buttonLogin.setDisable(false);
                    textFieldUsername.setDisable(false);
                    textFieldPassword.setDisable(false);
                });
            }

        }).start();
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
        textFieldRegisterPassword.clear();
        textFieldRegisterUsername.clear();
        checkBoxResidence.setSelected(false);
    }

    private void loadMainView(UserType userType) {

        Platform.runLater(()-> {

            Parent mainView = null;
            Stage mainStage = new Stage();
            mainStage.setWidth(1024);
            mainStage.setHeight(768);
            mainStage.setTitle("Donare de sange");
            mainStage.getIcons().add(new Image("donation/client/icon.png"));

            try {
                FXMLLoader loader = new FXMLLoader();

                switch (userType) {
                    case Donor:
                        loader.setLocation(getClass().getResource("../views/DonorView.fxml"));
                        break;
                    case BloodTransfusionCenter:
                        loader.setLocation(getClass().getResource("../views/CenterView.fxml"));
                        break;
                    case Doctor:
                        loader.setLocation(getClass().getResource("../views/DoctorView.fxml"));
                        break;
                }

                mainView = loader.load();

                controllerRoot.removeObserver(null);
                AbstractController controller = loader.getController();
                controllerRoot.addObserver(controller);
                controller.setControllerRoot(controllerRoot);

                Scene scene = new Scene(mainView);
                mainStage.setMinWidth(810);
                mainStage.setMinHeight(620);
                mainStage.setScene(scene);

                this.mainStage.hide();
                mainStage.show();

                controller.setLoginController(this);
                controller.setMainService(mainService, textFieldUsername.getText(), mainStage);

            } catch (Exception e) {
                e.printStackTrace();
//                System.out.println("loadMainView -> " + e.getMessage());
            }

        });
    }

    void showLoginWindow() {
        mainStage.show();
    }


    public LoginController() {
    }

    public void setMainService(IMainService mainService) {
        this.mainService = mainService;
    }

    public void loadLoginWindow(Parent show, Stage primaryStage) {
        mainStage = primaryStage;
        mainWindowView = show;
        Scene scene = new Scene(mainWindowView);
        mainStage.setScene(scene);
        mainStage.show();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        anchorPaneRegistration.setVisible(false);
        checkBoxResidence.setOnAction(ev -> {
            if (checkBoxResidence.isSelected()) textAreaResidenceAddress.setDisable(true);
            else textAreaResidenceAddress.setDisable(false);
        });
        try {
            controllerRoot = new ControllerRoot();
        } catch (RemoteException e) {
            System.out.println("LoginControler->constructor" + e.getMessage());
        }
    }
}
