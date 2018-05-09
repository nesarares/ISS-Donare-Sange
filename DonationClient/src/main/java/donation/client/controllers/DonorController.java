package donation.client.controllers;

import com.jfoenix.controls.*;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.octicons.OctIcon;
import donation.client.utils.GUIUtils;
import donation.client.utils.Timer;
import donation.model.Donation;
import donation.model.DonorProfile;
import donation.services.IMainService;
import donation.utils.IObserver;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.net.URL;
import java.rmi.RemoteException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ResourceBundle;

public class DonorController extends AbstractController {

    private DonorProfile profile;

    @FXML
    private StackPane stackPaneContent;

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
    private Label labelDonationsSoFar, labelDaysLeftUntilNextDonation;

    @FXML
    private JFXDatePicker datePickerBirthDate;

    @FXML
    private JFXTextArea textAreaResidenceAddress, textAreaHomeAddress;

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
            "2018-03-18 - The new analysis have arrived. (Test)",
            "2018-02-26 - There is a blood alert! (Test)",
            "2018-02-03 - The new analysis have arrived. (Test)");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        burgerTask = new HamburgerBasicCloseTransition(menuHamburger);
        burgerTask.setRate(-1);
        drawer.setSidePane(drawerContent);
        drawerContent.setVisible(true);

        labelDonationsSoFar.setText("");
        labelDaysLeftUntilNextDonation.setText("");

        initTable();
        initList();

        toggleView(homePane, buttonHome);
    }

    private void initTable() {
        columnDate.setCellValueFactory(date->new SimpleStringProperty(date.getValue().getDonationDate().toString().split(" ")[0]));
        columnHepatitis.setCellValueFactory(new PropertyValueFactory<>("hepatitis"));
        columnHivoraids.setCellValueFactory(new PropertyValueFactory<>("HIVorAIDS"));
        columnHTLV.setCellValueFactory(new PropertyValueFactory<>("HTLV"));
        columnLevelalt.setCellValueFactory(new PropertyValueFactory<>("levelALT"));
        columnSyphilis.setCellValueFactory(new PropertyValueFactory<>("syphilis"));

        tableDonation.setItems(modelDonation);
    }

    private void initList() {

        listNotifications.setOnMouseClicked(ev-> {
            mainService.removeNotificationFromDonor(getUsername(), listNotifications.getSelectionModel().getSelectedItem());
            modelNotifications.remove(listNotifications.getSelectionModel().getSelectedItem());
        });

        listNotifications.setItems(modelNotifications);
    }

    private void initProfile() {
        checkBoxResidenceAddress.setOnAction(ev -> {
            if (checkBoxResidenceAddress.isSelected()) textAreaResidenceAddress.setDisable(true);
            else textAreaResidenceAddress.setDisable(false);
        });

        textAreaHomeAddress.setText(profile.getAddress());
        textAreaResidenceAddress.setText(profile.getResidence());
        textFieldCNP.setText(profile.getCNP());
        textFieldEmail.setText(profile.getEmail());
        textFieldFirstName.setText(profile.getFirstName());
        textFieldHeight.setText(String.valueOf(profile.getHeight()));
        textFieldLastName.setText(profile.getLastName());
        textFieldNationality.setText(profile.getNationality());
        textFieldPhone.setText(profile.getPhone());
        textFieldWeight.setText(String.valueOf(profile.getWeight()));
        datePickerBirthDate.setValue(profile.getBirthDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

        if (profile.getAddress().equals(profile.getResidence())) {
            checkBoxResidenceAddress.setSelected(true);
            textAreaResidenceAddress.setDisable(true);
        }
    }

    private void updateLabels(){

        new Thread(()->{
            setDonationTableData();
            Platform.runLater(  ()->{
                labelDonationsSoFar.setText("" + modelDonation.size());
                labelDaysLeftUntilNextDonation.setText(mainService.getDaysUntilNextDonationForDonor(getUsername(), Date.valueOf(LocalDate.now())) + "");
            });
        }).start();

    }

    @Override
    public void setMainService(IMainService mainService, String username, Stage stageLogin) {

        super.setMainService(mainService, username, stageLogin);

        profile = mainService.getProfile(username);
        labelNumeDonator.setText(profile.getFirstName() + " " + profile.getLastName());
        initProfile();
        updateLabels();
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
    private void handleUpdateDonorProfile(ActionEvent event) {

        DonorProfile donorProfile = new DonorProfile();

        donorProfile.setFirstName(textFieldFirstName.getText());
        donorProfile.setLastName(textFieldLastName.getText());
        donorProfile.setCNP(textFieldCNP.getText());

        donorProfile.setBirthDate(null);
        if (datePickerBirthDate.getValue() != null)
            donorProfile.setBirthDate(Date.valueOf(datePickerBirthDate.getValue()));

        donorProfile.setEmail(textFieldEmail.getText());
        donorProfile.setPhone(textFieldPhone.getText());
        donorProfile.setWeight(Float.parseFloat(textFieldWeight.getText().equals("") ? "0.0" : textFieldWeight.getText()));
        donorProfile.setHeight(Integer.parseInt(textFieldHeight.getText().equals("") ? "0" : textFieldHeight.getText()));
        donorProfile.setNationality(textFieldNationality.getText());
        donorProfile.setAddress(textAreaHomeAddress.getText());
        donorProfile.setResidence(textAreaHomeAddress.getText());

        if (!checkBoxResidenceAddress.isSelected()) donorProfile.setResidence(textAreaResidenceAddress.getText());

        try {
            getMainService().updateProfile(getUsername(), donorProfile);
            profile = donorProfile;
            labelNumeDonator.setText(profile.getFirstName() + " " + profile.getLastName());
            //todo notificare observeri
            GUIUtils.showDialogMessage(Alert.AlertType.INFORMATION, "Success", "Account profile modified successfully!", stackPaneContent);
        } catch (Exception e) {
            GUIUtils.showDialogMessage(Alert.AlertType.ERROR, "Error", e.getMessage(), stackPaneContent);
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


    private void setDonationTableData(){
        modelDonation.setAll(
                mainService.getHistory(getUsername())
        );
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
        setDonationTableData();
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

    @Override
    public void addObserver(IObserver observer) throws RemoteException {
        throw new RemoteException("Not available");
    }

    @Override
    public void removeObserver(IObserver observer) throws RemoteException {
        throw  new RemoteException("Not available");
    }

    @Override
    public void testUpdate() throws RemoteException {

    }

    @Override
    public void notifyDonorAnalyseFinished(String username, String message) throws RemoteException {

        System.out.println("Notified->" +  message);

        Platform.runLater(()->{//todo @RaresNesa trebuie cumva formatat mesajul nu am mai avut timp
            GUIUtils.showDialogMessage(Alert.AlertType.INFORMATION,"Message","You have new notifications",stackPaneContent);
            modelNotifications.add(message);
        });
    }

    @Override
    public void notifyDonorUpdateHistory(String username) throws RemoteException {
        //todo sa punem notificarile pe thread-uri separate in MainImpl @OnuEdy,@GeorgeMihali
        Platform.runLater(()->{
            setDonationTableData();
            updateLabels();
        });
    }
}
