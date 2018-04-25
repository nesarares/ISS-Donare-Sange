package donation.client.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.octicons.OctIcon;
import donation.client.utils.Timer;
import donation.model.Donation;
import donation.services.IMainService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
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
    private ObservableList<String> modelNotifications = FXCollections.observableArrayList("2018-03-18 - Au sosit noile analize.", "2018-02-26 - Este nevoie de sange!", "2018-02-03 - Au sosit noile analize.");

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
