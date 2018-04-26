package donation.client.controllers;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class DoctorController extends AbstractController {
    @FXML
    private Label labelNumeDoctor;
    @FXML
    private AnchorPane drawerContent, bloodStatus,requestFormular;
    @FXML
    private JFXDrawer drawer;
    @FXML
    private JFXHamburger menuHamburger;
    private HamburgerBasicCloseTransition burgerTask;

    @FXML
    private TableColumn columnPatient,columnQuantity,columnStatus;
    @FXML
    private TableView bloodStatusView;

    @FXML
    private JFXTextField textPatientName,textPatientSurname,textQuantityLeukocytes,textQuantityThrombocytes,textQuantityPlasma;
    @FXML
    private JFXCheckBox checkLeukocytes,checkThrombocytes,checkPlasma;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        burgerTask = new HamburgerBasicCloseTransition(menuHamburger);
        burgerTask.setRate(-1);
        drawer.setSidePane(drawerContent);
        drawerContent.setVisible(true);
    }
}
