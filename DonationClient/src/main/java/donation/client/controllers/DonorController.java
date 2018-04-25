package donation.client.controllers;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.octicons.OctIcon;
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

public class DonorController implements Initializable {
    private LoginController loginController;
    private IMainService mainService;
    private String username;
    private Stage stageLogin;

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
    private AnchorPane donationHistoryPane, personalProfilePane, notificationsPane;

    @FXML
    private TableView<Donation> tableDonation;
    private ObservableList<Donation> modelDonation = FXCollections.observableArrayList();
    @FXML
    private TableColumn<Donation, String> columnDate, columnHivoraids, columnHepatitis,
            columnSyphilis, columnHTLV, columnLevelalt;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        burgerTask = new HamburgerBasicCloseTransition(menuHamburger);
        burgerTask.setRate(-1);
        drawer.setSidePane(drawerContent);
        drawerContent.setVisible(true);

        initTable();
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

    public void setMainService(IMainService mainService, String username, Stage stageLogin) {
        this.mainService = mainService;
        this.username = username;
        this.stageLogin = stageLogin;

        // TO DO: De inlocuit cu datele din profil (Nume Prenume)
        labelNumeDonator.setText(username);
        stageLogin.setOnCloseRequest(ev -> handleLogoutEvent(null));
    }

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

    @FXML
    private void handleLogoutEvent(ActionEvent e) {
        System.out.println("Logging out...");
        mainService.logout(username, null);
        loginController.showLoginWindow();
        stageLogin.close();
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
            drawer.setVisible(false);
            menuHamburger.getStyleClass().remove("hamburger-white");
        }
    }
}
