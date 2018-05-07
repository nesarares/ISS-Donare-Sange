package donation.client.controllers;

import com.jfoenix.controls.*;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;
import donation.client.utils.Timer;
import donation.model.DonorProfile;
import donation.services.IMainService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class CenterController extends AbstractController {
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
}
