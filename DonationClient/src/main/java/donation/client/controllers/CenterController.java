package donation.client.controllers;

import com.jfoenix.controls.*;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;
import donation.client.utils.GUIUtils;
import donation.client.utils.Timer;
import donation.model.Donation;
import donation.model.DonationStatus;
import donation.model.DonorProfile;
import donation.model.MedicalQuestionnaire;
import donation.services.IMainService;
import donation.utils.IObserver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import javax.xml.crypto.Data;
import java.net.URL;
import java.rmi.RemoteException;
import java.security.AllPermission;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;
import java.util.ResourceBundle;

    public class CenterController extends AbstractController {

    @FXML
    private StackPane stackPane;

    @FXML
    private Label labelCenterName;

    @FXML
    private JFXHamburger menuHamburger;
    private HamburgerBasicCloseTransition burgerTask;
    @FXML
    private JFXDrawer drawer;
    @FXML
    private AnchorPane drawerContent;

    @FXML
    private AnchorPane homePane, donationPane, questionnairePane, stockPane, requestsPane, notificationsPane;
    @FXML
    private JFXButton buttonHome, buttonDonation, buttonQuestionnaire, buttonStock, buttonRequests, buttonNotifications;

    @FXML
    private JFXListView<DonorProfile> listDonationDonors, listQuestionnaireDonors;
    private ObservableList<DonorProfile> modelListDonors = FXCollections.observableArrayList();

    @FXML
    private JFXTextField fieldSearchDonation, fieldSearchQuestionnaire;


    @FXML
    private JFXTextField textFieldNoSexualPart, textFieldForeignLocation, textFieldForeignTime,
            textFieldAlcohol, textFieldAlcoholQuant, textFieldBloodPreasure,textFieldLevelALT;

    @FXML
    private JFXDatePicker datePickerChildBirthday, datePickerLastMenstruation, datePickerLastAlcoholCons,datePickerDonationDate;


    @FXML
    private  JFXCheckBox checkBoxHealthy, checkBoxInexFever,checkBoxVaccines,checkBoxSexPartHiv,
            checkBoxSexPartWorker,checkBoxIntraDrugs,checkBoxSexPartChanged,checkBoxTatoos,checkBoxPregnancy,
            checkBoxDetention, checkBoxStroke,checkBoxConvultions,checkBoxSTD,checkBoxRefusedDelayed,
            checkBoxPostDonationAttention, checkBoxUnexpectedWeightLoss, checkBoxDentalTreat,
            checkBoxDrugTreat, checkBoxSexPartIntraDrug,checkBoxMultipleSexPart,checkBoxSexWorker,
            checkBoxSurgery, checkBoxRecievedBlood, checkBoxBLTForeign, checkBoxHepatitis,
            checkBoxITLMalaria, checkBoxHeartDisease, checkBoxAsthma, checkBoxDiabetisUlcerus,
            checkBoxSmoker;

    @FXML
    private JFXCheckBox checkBoxHIVOrAIDS,checkBoxHepatitisDonation,checkBoxSyphilis,checkBoxHTLV;

    @FXML
    JFXListView<String> listNotifications;
    private ObservableList<String> modelNotifications = FXCollections.observableArrayList(
            "2018-03-18 - A new blood request arrived.",
            "2018-02-26 - A blood request has been canceled.",
            "2018-02-03 - A new blood request arrived.");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        burgerTask = new HamburgerBasicCloseTransition(menuHamburger);
        burgerTask.setRate(-1);
        drawer.setSidePane(drawerContent);
        drawerContent.setVisible(true);

        initListDonors();
        initListNotifications();
        initSearchFields();
        toggleView(homePane, buttonHome);
    }

    private void initSearchFields() {
        fieldSearchDonation.textProperty().addListener(o -> handleSearchTextChanged(fieldSearchDonation));
        fieldSearchQuestionnaire.textProperty().addListener(o -> handleSearchTextChanged(fieldSearchQuestionnaire));
    }

    private void initListDonors() {
        listDonationDonors.setCellFactory(param -> new ListCell<DonorProfile>() {
            @Override
            public void updateItem(DonorProfile item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) setText(null);
                else {
                    setText(item.getFirstName() + " " + item.getLastName() + " - " + item.getCNP());
                }
            }
        });
        listQuestionnaireDonors.setCellFactory(listDonationDonors.getCellFactory());

        listDonationDonors.setItems(modelListDonors);
        listQuestionnaireDonors.setItems(modelListDonors);
    }

    @Override
    public void setMainService(IMainService mainService, String username, Stage stageLogin) {
        super.setMainService(mainService, username, stageLogin);

        labelCenterName.setText(username);
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

    private void initListNotifications() {
        listNotifications.setItems(modelNotifications);
    }

    private void initListDonors(String keyword) {
        modelListDonors.setAll(mainService.getDonorProfiles(keyword));
    }

    private void toggleView(AnchorPane viewToShow, JFXButton buttonClicked) {
        homePane.setVisible(false);
        donationPane.setVisible(false);
        questionnairePane.setVisible(false);
        requestsPane.setVisible(false);
        stockPane.setVisible(false);
        notificationsPane.setVisible(false);

        buttonDonation.setDisable(false);
        buttonHome.setDisable(false);
        buttonNotifications.setDisable(false);
        buttonQuestionnaire.setDisable(false);
        buttonRequests.setDisable(false);
        buttonStock.setDisable(false);

        viewToShow.setVisible(true);
        buttonClicked.setDisable(true);
    }

    private void handleSearchTextChanged(JFXTextField searchBar) {
        if (searchBar.getText() != null || Objects.equals(searchBar.getText(), "")) initListDonors(searchBar.getText());
        else initListDonors("");
    }



    @FXML
    private void handleButtonHome(ActionEvent actionEvent) {
        toggleView(homePane, buttonHome);
        handleDrawer(null);
    }

    @FXML
    private void handleButtonDonation(ActionEvent actionEvent) {
        toggleView(donationPane, buttonDonation);
        handleDrawer(null);
        initListDonors("");
    }

    @FXML
    private void handleButtonQuestionnaire(ActionEvent actionEvent) {
        toggleView(questionnairePane, buttonQuestionnaire);
        handleDrawer(null);
        initListDonors("");
    }

    @FXML
    private void handleButtonStock(ActionEvent actionEvent) {
        toggleView(stockPane, buttonStock);
        handleDrawer(null);
    }

    @FXML
    private void handleButtonRequests(ActionEvent actionEvent) {
        toggleView(requestsPane, buttonRequests);
        handleDrawer(null);
    }

    @FXML
    private void handleButtonNotifications(ActionEvent actionEvent) {
        toggleView(notificationsPane, buttonNotifications);
        handleDrawer(null);
    }


    private void buildMedicalQuestionnaire(MedicalQuestionnaire questionnaire,DonorProfile donorProfile) throws  Exception{

        // First column
        questionnaire.setHealthy(checkBoxHealthy.isSelected());
        questionnaire.setInexplicableFever(checkBoxInexFever.isSelected());
        questionnaire.setVaccines(checkBoxVaccines.isSelected());
        questionnaire.setSexualPartnerHIVHepatitis(checkBoxSexPartHiv.isSelected());
        questionnaire.setSexualPartnerSexWorker(checkBoxSexPartWorker.isSelected());
        questionnaire.setIntravenousDrugs(checkBoxIntraDrugs.isSelected());
        questionnaire.setSexualPartnerChanged(checkBoxSexPartChanged.isSelected());
        questionnaire.setTattoos(checkBoxTatoos.isSelected());
        questionnaire.setPregnancy(checkBoxPregnancy.isSelected());
        questionnaire.setDetention(checkBoxDetention.isSelected());
        questionnaire.setStroke(checkBoxStroke.isSelected());
        questionnaire.setConvulsionsNervousDisease(checkBoxConvultions.isSelected());
        questionnaire.setSTD(checkBoxSTD.isSelected());
        questionnaire.setBeenRefusedDelayed(checkBoxRefusedDelayed.isSelected());
        questionnaire.setPostDonationAttention(checkBoxPostDonationAttention.isSelected());

        questionnaire.setNumberOfSexualPartners(
                textFieldNoSexualPart.getText().equals("") ? 0 : Integer.parseInt(textFieldNoSexualPart.getText())
        );

        questionnaire.setbLTFWhere(textFieldForeignLocation.getText());

        questionnaire.setbLTFWhen(textFieldForeignTime.getText());

        questionnaire.setAlcohol(textFieldAlcohol.getText());

        questionnaire.setAlcoholQuantity(
                textFieldAlcoholQuant.getText().equals("") ? 0 : Integer.parseInt(textFieldAlcoholQuant.getText())
        );


        //Second column
        questionnaire.setUnexpectedLossWeight(checkBoxUnexpectedWeightLoss.isSelected());
        questionnaire.setDentalTreatment(checkBoxDentalTreat.isSelected());
        questionnaire.setDrugTreatment(checkBoxDrugTreat.isSelected());
        questionnaire.setSexualPartnerIntravenousDrug(checkBoxSexPartIntraDrug.isSelected());
        questionnaire.setMultipleSexualPartners(checkBoxMultipleSexPart.isSelected());
        questionnaire.setSexWorker(checkBoxSexWorker.isSelected());
        questionnaire.setSurgery(checkBoxSurgery.isSelected());
        questionnaire.setReceivedBlood(checkBoxRecievedBlood.isSelected());
        questionnaire.setBornLivedTravelledForeign(checkBoxBLTForeign.isSelected());
        questionnaire.setHepatitis(checkBoxHepatitis.isSelected());
        questionnaire.setIcterusTuberculosisReumaticFeverMalaria(checkBoxITLMalaria.isSelected());
        questionnaire.setHeartDiseaseHLBloodPressure(checkBoxHeartDisease.isSelected());
        questionnaire.setAsthma(checkBoxAsthma.isSelected());
        questionnaire.setDiabetisUlcerCancer(checkBoxDiabetisUlcerus.isSelected());
        questionnaire.setSmoker(checkBoxSmoker.isSelected());

        if (datePickerChildBirthday.getValue() != null)
            questionnaire.setBirthdayOfChild(Date.valueOf(datePickerChildBirthday.getValue()));
        if (datePickerLastMenstruation.getValue() != null)
            questionnaire.setLastMenstruation(Date.valueOf(datePickerLastMenstruation.getValue()));
        if (datePickerLastAlcoholCons.getValue() != null)
            questionnaire.setLastAlcoholConsume(Date.valueOf(datePickerLastAlcoholCons.getValue()));

        if (textFieldBloodPreasure.getText().equals("")) throw  new Exception("You must enter the blood pressure!");

        questionnaire.setBloodPressure(textFieldBloodPreasure.getText());
        questionnaire.setIdUser(donorProfile.getIdUser());
        questionnaire.setDate(Date.valueOf(LocalDate.now()));
    }

    private void buildDonation(Donation donation) throws  Exception{

        if(datePickerDonationDate.getValue() == null)throw new Exception("You must enter a donation date!");

        if(textFieldLevelALT.getText().equals(""))throw new Exception("You must specify the ALT level!");

        if(textFieldLevelALT.getText().equals(""))throw new Exception("The ALT level must be specified!");

        donation.setHepatitis(checkBoxHepatitisDonation.isSelected());
        donation.setHIVorAIDS(checkBoxHIVOrAIDS.isSelected());
        donation.setSyphilis(checkBoxSyphilis.isSelected());
        donation.setHTLV(checkBoxHTLV.isSelected());

        System.out.println(Date.valueOf(datePickerDonationDate.getValue()));
        donation.setDonationDate(Date.valueOf(datePickerDonationDate.getValue()));

        try {
            donation.setLevelALT(Integer.parseInt(textFieldLevelALT.getText()));
        }catch (Exception e){
            throw new Exception("You must enter an name in ALT field");
        }

        donation.setDonationStatus(DonationStatus.Taking);

        if(listDonationDonors.getSelectionModel().getSelectedItem() == null)throw new Exception("You must select a donor in order to continue!");

        donation.setDonor(mainService.getUserById(listDonationDonors.getSelectionModel().getSelectedItem().getIdUser()));
    }

    @FXML
    private void handleButtonSubmitDonation(ActionEvent event){

        Donation donation = new Donation();

        try {
            buildDonation(donation);
            mainService.addDonation(getUsername(),donation);
            GUIUtils.showDialogMessage(Alert.AlertType.INFORMATION,"Success","Donation was added successfully!",stackPane);
        }catch (Exception e){
            GUIUtils.showDialogMessage(Alert.AlertType.INFORMATION,"Error",e.getMessage(),stackPane);
        }

    }

    @FXML
    private void handleButtonSubmitQuestionnaire(ActionEvent actionEvent){

        MedicalQuestionnaire questionnaire = new MedicalQuestionnaire();

        DonorProfile donorProfile = listQuestionnaireDonors.getSelectionModel().getSelectedItem();

        if(donorProfile == null){
            GUIUtils.showDialogMessage(Alert.AlertType.INFORMATION,"Error","You must select a donor!",stackPane);
            return;
        }


        try {
            buildMedicalQuestionnaire(questionnaire,donorProfile);
            mainService.addMedicalQuestionnaire(questionnaire);
            GUIUtils.showDialogMessage(Alert.AlertType.INFORMATION,"Success","Questionnaire was added successfully",stackPane);
        }catch (Exception e){
            GUIUtils.showDialogMessage(Alert.AlertType.INFORMATION,"Error",e.getMessage(),stackPane);
        }
    }

    @Override
    public void addObserver(IObserver observer) throws RemoteException {
        throw new  RemoteException("Not available");
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
        throw new RemoteException("Not available!");
    }

    @Override

    public void notifyDonorUpdateHistory(String username) throws RemoteException {
        throw new RemoteException("Not available");
    }
    }
