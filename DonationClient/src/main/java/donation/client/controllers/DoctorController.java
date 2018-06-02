package donation.client.controllers;

import com.jfoenix.controls.*;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import donation.client.utils.GUIUtils;
import donation.client.utils.Timer;
import donation.model.*;
import donation.services.IMainService;
import donation.utils.IObserver;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class DoctorController extends AbstractController {

    @FXML
    private Label labelNumeDoctor;

    @FXML
    private AnchorPane drawerContent, bloodStatus, requestFormular, detailsPane;

    @FXML
    private JFXDrawer drawer;
    @FXML
    private JFXHamburger menuHamburger;
    private HamburgerBasicCloseTransition burgerTask;

    @FXML
    private TableColumn<BloodRequest, String> columnDate, columnPatient, columnCenter, columnStatus;
    @FXML
    private TableColumn<BloodRequest, Void> columnAction;
    @FXML
    private TableView<BloodRequest> tableBloodRequests;
    ObservableList<BloodRequest> model = FXCollections.observableArrayList();


    @FXML
    private TableView<BloodComponentQuantity> tableBloodStockRD;
    private ObservableList<BloodComponentQuantity> modelBloodStockRD = FXCollections.observableArrayList();
    @FXML
    private TableColumn<BloodComponentQuantity, String> columnTypeRD, columnGroupRD, columnRhRD,
            columnQuantityRD, columnExpDateRD, columnStatusRD;


    @FXML
    private JFXTextField textPatientName, textQuantityLeukocytes, textQuantityThrombocytes, textQuantityPlasma;
    @FXML
    private CheckBox checkLeukocytes, checkThrombocytes, checkPlasma;
    @FXML
    private JFXComboBox<ABOBloodGroup> comboBloodGroup;
    @FXML
    private JFXComboBox<RhBloodGroup> comboRh;
    @FXML
    private StackPane stackPaneContent;

    @FXML
    private JFXButton buttonRequest, buttonAdditional;

    @FXML
    Label labelBloodGroupDetails, labelRhDetails, labelLeukocytesQu, labelThrombocytesQu, labelPlasmaQu;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        burgerTask = new HamburgerBasicCloseTransition(menuHamburger);
        burgerTask.setRate(-1);
        drawer.setSidePane(drawerContent);
        drawerContent.setVisible(true);

        initTable();
        initTableDetails();
        initCombos();
        initChecks();
        toggleView(bloodStatus, buttonAdditional);
    }

    private void initTable() {

        columnDate.setCellValueFactory(request -> new SimpleStringProperty(request.getValue().getDateRequested().toString().split(" ")[0]));
        columnPatient.setCellValueFactory(new PropertyValueFactory<>("patientName"));
        // TODO De pus nume in loc de username la reciever
        columnCenter.setCellValueFactory(request ->
                new SimpleStringProperty(
                        request.getValue().getReceiver() != null ? request.getValue().getReceiver().getUsername() : "ALL"
                )
        );
        columnStatus.setCellValueFactory(new PropertyValueFactory<>("bloodRequestStatus"));

        columnAction.setCellFactory(param -> new TableCell<BloodRequest, Void>() {
            private JFXButton detailsButton = new JFXButton();
            HBox pane = new HBox(detailsButton);
            {
                FontAwesomeIconView iconDetails = new FontAwesomeIconView();
                iconDetails.setIcon(FontAwesomeIcon.EYE);
                iconDetails.setFont(new Font(20));

                pane.setAlignment(Pos.CENTER);
                pane.setSpacing(10d);
                detailsButton.setId("ActionButton");
                detailsButton.getStyleClass().add("btn");
                detailsButton.setTooltip(new Tooltip("View request"));
//                detailsButton.setGraphic(iconDetails);
                detailsButton.setText("View");

                detailsButton.setOnAction(event -> {
                    BloodRequest request = (BloodRequest) getTableRow().getItem();
                    loadDetailsPane(request);
                    toggleView(detailsPane, null);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : detailsButton);
            }
        });

        tableBloodRequests.setItems(model);
    }

    private void loadDetailsPane(BloodRequest request) {
        labelLeukocytesQu.setText(String.valueOf(request.getLeukocytesQuantity()));
        labelPlasmaQu.setText(String.valueOf(request.getPlasmaQuantity()));
        labelThrombocytesQu.setText(String.valueOf(request.getThrombocytesQuantity()));
        labelRhDetails.setText(request.getRhBloodGroup() == RhBloodGroup.Positive ? "+" : "-");
        labelBloodGroupDetails.setText(request.getBloodGroup().toString());
    }

    private void setModelBloodStockRD(BloodRequest request) {
//        modelBloodStockRD.setAll();
    }

    private void initTableDetails() {
        columnTypeRD.setCellValueFactory(new PropertyValueFactory<>("bloodComponent"));
        columnGroupRD.setCellValueFactory(new PropertyValueFactory<>("aboBloodGroup"));
        columnQuantityRD.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        columnRhRD.setCellValueFactory(new PropertyValueFactory<>("rhBloodGroup"));
        columnStatusRD.setCellValueFactory(new PropertyValueFactory<>("bloodStatus"));
        columnExpDateRD.setCellValueFactory(new PropertyValueFactory<>("expirationDate"));
        tableBloodStockRD.setItems(modelBloodStockRD);
    }

    private void loadRequests() {
        model.setAll(mainService.getBloodRequestsDoctor(username));
    }

    private void initChecks() {
        checkThrombocytes.setOnAction(ev -> {
            if (checkThrombocytes.isSelected()) textQuantityThrombocytes.setVisible(true);
            else {
                textQuantityThrombocytes.setVisible(false);
            }
        });

        checkLeukocytes.setOnAction(ev -> {
            if (checkLeukocytes.isSelected()) textQuantityLeukocytes.setVisible(true);
            else {
                textQuantityLeukocytes.setVisible(false);
            }
        });

        checkPlasma.setOnAction(ev -> {
            if (checkPlasma.isSelected()) textQuantityPlasma.setVisible(true);
            else {
                textQuantityPlasma.setVisible(false);
            }
        });
    }

    private void initCombos() {
        comboBloodGroup.getItems().setAll(ABOBloodGroup.values());
        comboRh.getItems().setAll(RhBloodGroup.values());
    }

    @Override
    public void setMainService(IMainService mainService, String username, Stage stageLogin) {
        super.setMainService(mainService, username, stageLogin);
        DoctorProfile profile = mainService.getDoctorProfile(username);
        // TO DO: De inlocuit cu datele din profil (Nume Prenume)
        labelNumeDoctor.setText(profile.getFirstName() + " " + profile.getLastName());
        loadRequests();
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
        detailsPane.setVisible(false);

        buttonAdditional.setDisable(false);
        buttonRequest.setDisable(false);

        viewToShow.setVisible(true);
        if (buttonClicked != null) buttonClicked.setDisable(true);
    }

    @FXML
    private void handleButtonRequest(ActionEvent actionEvent) {
        toggleView(requestFormular, buttonRequest);
        handleDrawer(null);
    }

    @FXML
    private void handleButtonAdditional(ActionEvent actionEvent) {
        toggleView(bloodStatus, buttonAdditional);
        loadRequests();
        handleDrawer(null);
    }

    @FXML
    private void handleSendBloodRequest(ActionEvent actionEvent) {

        new Thread(()-> {

            try {

                //todo rares da disable la butonul de send dupa ce da un request si enable in finally

                BloodRequest request = new BloodRequest();
                request.setBloodGroup(comboBloodGroup.getValue());
                request.setPatientName(textPatientName.getText());
                request.setRhBloodGroup(comboRh.getValue());

                int leukocytesQuantity = checkLeukocytes.isSelected() ? Integer.parseInt(textQuantityLeukocytes.getText()) : 0;
                request.setLeukocytesQuantity(leukocytesQuantity);

                int plasmaQuantity = checkPlasma.isSelected() ? Integer.parseInt(textQuantityPlasma.getText()) : 0;
                request.setPlasmaQuantity(plasmaQuantity);

                int thrombocytesQuantity = checkThrombocytes.isSelected() ? Integer.parseInt(textQuantityThrombocytes.getText()) : 0;
                request.setThrombocytesQuantity(thrombocytesQuantity);

                mainService.addBloodRequest(request, username);
                Platform.runLater(()->GUIUtils.showDialogMessage(Alert.AlertType.INFORMATION, "Success", "Request was sucessfully added!", stackPaneContent));

            } catch (NumberFormatException e) {
                Platform.runLater(()->GUIUtils.showDialogMessage(Alert.AlertType.ERROR, "Error", "Invalid quantities entered.", stackPaneContent));
            } catch (Exception e) {
                Platform.runLater(()->GUIUtils.showDialogMessage(Alert.AlertType.ERROR, "Error", e.getMessage(), stackPaneContent));
            }

        }).start();
    }

    @Override
    public void addObserver(IObserver observer) throws RemoteException {

    }

    @Override
    public void removeObserver(IObserver observer) throws RemoteException {
        throw new RemoteException("Not available!");
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
        throw new RemoteException("Not available");
    }

    @Override
    public void notifyNewRequestAdded(String username,String message) throws RemoteException {
        throw new  RemoteException("Not available!");
    }
}
