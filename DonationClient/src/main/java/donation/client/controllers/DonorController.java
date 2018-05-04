package donation.client.controllers;

import com.jfoenix.controls.*;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.octicons.OctIcon;
import donation.client.utils.Timer;
import donation.model.Donation;
import donation.model.DonorProfile;
import donation.services.IMainService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class DonorController extends AbstractController {
    @FXML
    private Label labelNumeDonator;

    @FXML
    private JFXHamburger menuHamburger;
    private HamburgerBasicCloseTransition burgerTask;
    @FXML
    private JFXDrawer drawer;
    @FXML
    private AnchorPane drawerContent;

    @FXML
    private JFXTextField textFieldFirstName, textFieldLastName, textFieldCNP,
            textFieldEmail, textFieldPhone, textFieldWeight,
            textFieldHeight, textFieldNationality;

    @FXML
    private  JFXDatePicker datePickerBirthDate;

    @FXML
    private JFXTextArea textAreaResidenceAddress,textAreaHomeAddress;

    @FXML
    private JFXCheckBox checkBoxResidenceAddress;

    @FXML
    private AnchorPane homePane, donationHistoryPane, personalProfilePane, notificationsPane;
    @FXML
    private JFXButton buttonHome, buttonDonation, buttonProfile, buttonNotifications;

    @FXML
    private TableView<Donation> tableDonation;
    private ObservableList<Donation> modelDonation = FXCollections.observableArrayList();
    @FXML
    private TableColumn<Donation, String> columnDate, columnHivoraids, columnHepatitis,
            columnSyphilis, columnHTLV, columnLevelalt;

    @FXML
    JFXListView<String> listNotifications;
    private ObservableList<String> modelNotifications = FXCollections.observableArrayList(
            "2018-03-18 - The new analysis have arrived.",
            "2018-02-26 - There is a blood alert!",
            "2018-02-03 - The new analysis have arrived.");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        burgerTask = new HamburgerBasicCloseTransition(menuHamburger);
        burgerTask.setRate(-1);
        drawer.setSidePane(drawerContent);
        drawerContent.setVisible(true);

        initTable();
        initList();
        toggleView(homePane, buttonHome);
    }

    private void initTable() {
        columnDate.setCellValueFactory(new PropertyValueFactory<>("donationDate"));
        columnHepatitis.setCellValueFactory(new PropertyValueFactory<>("HIVorAIDS"));
        columnHivoraids.setCellValueFactory(new PropertyValueFactory<>("hepatitis"));
        columnHTLV.setCellValueFactory(new PropertyValueFactory<>("syphilis"));
        columnLevelalt.setCellValueFactory(new PropertyValueFactory<>("HTLV"));
        columnSyphilis.setCellValueFactory(new PropertyValueFactory<>("levelALT"));

        tableDonation.setItems(modelDonation);
    }

    private void initList() {
        listNotifications.setItems(modelNotifications);
    }

    @Override
    public void setMainService(IMainService mainService, String username, Stage stageLogin) {

        super.setMainService(mainService, username, stageLogin);

        // TO DO: De inlocuit cu datele din profil (Nume Prenume)
        labelNumeDonator.setText(username);
    }

    @FXML
    private void handleDrawer(MouseEvent actionEvent) {
        burgerTask.setRate(burgerTask.getRate() * -1);
        burgerTask.play();
        if (drawer.isClosed()) {
            drawer.setVisible(true);
            drawer.toggle();
            menuHamburger.getStyleClass().add("hamburger-white");
        } else {
            drawer.toggle();
            Timer.setTimeout(() -> drawer.setVisible(false), 400);
            menuHamburger.getStyleClass().remove("hamburger-white");
        }
    }

    @FXML
    private void handleUpdateDonorProfile(ActionEvent event){

        DonorProfile donorProfile = new DonorProfile();

        donorProfile.setFirstName(textFieldFirstName.getText());
        donorProfile.setLastName(textFieldLastName.getText());
        donorProfile.setCNP(textFieldCNP.getText());

        donorProfile.setBirthDate(null);
        if (datePickerBirthDate.getValue() != null) donorProfile.setBirthDate(Date.valueOf(datePickerBirthDate.getValue()));

        donorProfile.setEmail(textFieldEmail.getText());
        donorProfile.setPhone(textFieldPhone.getText());
        donorProfile.setWeight(Float.parseFloat(textFieldWeight.getText().equals("") ? "0.0" : textFieldWeight.getText()));
        donorProfile.setHeight(Integer.parseInt(textFieldHeight.getText().equals("") ? "0" : textFieldHeight.getText()));
        donorProfile.setNationality(textFieldNationality.getText());
        donorProfile.setAddress(textAreaHomeAddress.getText());
        donorProfile.setResidence(textAreaHomeAddress.getText());

        if(!checkBoxResidenceAddress.isSelected())donorProfile.setResidence(textAreaResidenceAddress.getText());



        try {
            getMainService().updateProfile(getUsername(),donorProfile);
            //todo notificare observeri
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION,e.getMessage());
            alert.showAndWait();
        }
    }

    private void toggleView(AnchorPane viewToShow, JFXButton buttonClicked) {
        donationHistoryPane.setVisible(false);
        homePane.setVisible(false);
        notificationsPane.setVisible(false);
        personalProfilePane.setVisible(false);

        buttonDonation.setDisable(false);
        buttonHome.setDisable(false);
        buttonNotifications.setDisable(false);
        buttonProfile.setDisable(false);

        viewToShow.setVisible(true);
        buttonClicked.setDisable(true);
    }

    @FXML
    private void handleButtonHome(ActionEvent actionEvent) {
        toggleView(homePane, buttonHome);
        handleDrawer(null);
    }

    @FXML
    private void handleButtonDonation(ActionEvent actionEvent) {
        toggleView(donationHistoryPane, buttonDonation);
        handleDrawer(null);
    }

    @FXML
    private void handleButtonProfile(ActionEvent actionEvent) {
        toggleView(personalProfilePane, buttonProfile);
        handleDrawer(null);
    }

    @FXML
    private void handleButtonNotifications(ActionEvent actionEvent) {
        toggleView(notificationsPane, buttonNotifications);
        handleDrawer(null);
    }
}
