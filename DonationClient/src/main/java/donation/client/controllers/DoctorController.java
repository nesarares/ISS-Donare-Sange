package donation.client.controllers;

import com.jfoenix.controls.*;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;
import donation.client.utils.Timer;
import donation.services.IMainService;
import donation.utils.IObserver;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class DoctorController extends AbstractController {

    @FXML
    private Label labelNumeDoctor;

    @FXML
    private AnchorPane drawerContent, bloodStatus, requestFormular;

    @FXML
    private JFXDrawer drawer;
    @FXML
    private JFXHamburger menuHamburger;
    private HamburgerBasicCloseTransition burgerTask;

    @FXML
    private TableColumn columnPacient, columnQuantity, columnStatus;
    @FXML
    private TableView bloodStatusView;

    @FXML
    private JFXTextField textPatientName, textPatientSurname, textQuantityLeukocytes, textQuantityThrombocytes, textQuantityPlasma;
    @FXML
    private CheckBox checkLeukocytes, checkThrombocytes, checkPlasma;
    @FXML
    private JFXButton buttonRequest, buttonAdditional;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        burgerTask = new HamburgerBasicCloseTransition(menuHamburger);
        burgerTask.setRate(-1);
        drawer.setSidePane(drawerContent);
        drawerContent.setVisible(true);

        toggleView(bloodStatus, buttonAdditional);
    }

    @Override
    public void setMainService(IMainService mainService, String username, Stage stageLogin) {
        super.setMainService(mainService, username, stageLogin);

        // TO DO: De inlocuit cu datele din profil (Nume Prenume)
        labelNumeDoctor.setText(username);
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
        bloodStatus.setVisible(false);
        requestFormular.setVisible(false);

        buttonAdditional.setDisable(false);
        buttonRequest.setDisable(false);

        viewToShow.setVisible(true);
        buttonClicked.setDisable(true);
    }

    @FXML
    private void handleButtonRequest(ActionEvent actionEvent) {
        toggleView(requestFormular, buttonRequest);
        handleDrawer(null);
    }

    @FXML
    private void handleButtonAdditional(ActionEvent actionEvent) {
        toggleView(bloodStatus, buttonAdditional);
        handleDrawer(null);
    }

    @Override
    public void addObserver(IObserver observer) throws RemoteException {

    }

    @Override
    public void removeObserver(IObserver observer) throws RemoteException {
        throw  new RemoteException("Not available!");
    }

    @Override
    public void testUpdate() throws RemoteException {

    }

    @Override
    public void notifyDonorAnalyseFinished(String username, String message) throws RemoteException {
        System.out.println("Doctor controler->" + username + " " + message);
    }

    @Override
    public void notifyDonorUpdateHistory(String username) throws RemoteException {
        throw new  RemoteException("Not available");
    }
}
