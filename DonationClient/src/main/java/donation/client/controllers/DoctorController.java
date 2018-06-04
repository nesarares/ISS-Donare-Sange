package donation.client.controllers;

import com.jfoenix.controls.*;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import donation.client.utils.GUIUtils;
import donation.client.utils.Timer;
import donation.client.utils.mailer.Mailer;
import donation.model.*;
import donation.services.IMainService;
import donation.utils.IObserver;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.springframework.remoting.RemoteInvocationFailureException;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class DoctorController extends AbstractController {

    @FXML
    private StackPane stackPaneContent;

    @FXML
    private AnchorPane drawerContent, bloodStatus, requestFormular, detailsPane;


    @FXML
    private Label labelBloodGroupDetails, labelRhDetails, labelLeukocytesQu, labelThrombocytesQu, labelPlasmaQu;
    @FXML
    private Label labelNumeDoctor;
    @FXML
    private Label labelLeukocytesQuant, labelTromboQuant, labelPlasmaQuant;

    @FXML
    private CheckBox checkLeukocytes, checkThrombocytes, checkPlasma;

    @FXML
    private JFXComboBox<ABOBloodGroup> comboBloodGroup;
    @FXML
    private JFXComboBox<RhBloodGroup> comboRh;

    @FXML
    private JFXDrawer drawer;

    @FXML
    private JFXHamburger menuHamburger;

    @FXML
    private JFXTextField textPatientName, textQuantityLeukocytes, textQuantityThrombocytes, textQuantityPlasma;

    @FXML
    private JFXButton buttonRequest, buttonAdditional,buttonSend;

    @FXML
    private TableView<BloodComponentQuantity> tableBloodStockRD;
    @FXML
    private TableColumn<BloodComponentQuantity, String> columnTypeRD, columnGroupRD, columnRhRD,
            columnQuantityRD, columnExpDateRD, columnStatusRD;

    @FXML
    private TableView<BloodRequest> tableBloodRequests;
    @FXML
    private TableColumn<BloodRequest, String> columnDate, columnPatient, columnCenter, columnStatus;
    @FXML
    private TableColumn<BloodRequest, Void> columnAction;

    private ObservableList<BloodRequest> model = FXCollections.observableArrayList();
    private ObservableList<BloodComponentQuantity> modelBloodStockRD = FXCollections.observableArrayList();

    private HamburgerBasicCloseTransition burgerTask;
    private  BloodRequest selectedRequest;
    private EmailController emailController = null;


    @FXML
    private void handleEmailButton(ActionEvent event){
        JFXDialog dialog = new JFXDialog(stackPaneContent,buildDialogContent(),JFXDialog.DialogTransition.CENTER);
        emailController.setOpenedDialog(dialog);
        dialog.show();
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

                Platform.runLater(()->{
                    buttonSend.setText("Sending...");
                    buttonSend.setDisable(true);
                });

                BloodRequest request = new BloodRequest();
                request.setBloodGroup(comboBloodGroup.getValue());
                request.setPatientName(textPatientName.getText());
                request.setRhBloodGroup(comboRh.getValue());

                int leukocytesQuantity = checkLeukocytes.isSelected() ? Integer.parseInt(textQuantityLeukocytes.getText()) : 0;
                request.setLeukocytesQuantity(leukocytesQuantity * mainService.getBloodComponentQuantity());

                int plasmaQuantity = checkPlasma.isSelected() ? Integer.parseInt(textQuantityPlasma.getText()) : 0;
                request.setPlasmaQuantity(plasmaQuantity * mainService.getBloodComponentQuantity());

                int thrombocytesQuantity = checkThrombocytes.isSelected() ? Integer.parseInt(textQuantityThrombocytes.getText()) : 0;
                request.setThrombocytesQuantity(thrombocytesQuantity * mainService.getBloodComponentQuantity());

                mainService.addBloodRequest(request, username);
                Platform.runLater(()->GUIUtils.showDialogMessage(Alert.AlertType.INFORMATION, "Success", "Request was sucessfully added!", stackPaneContent));


            } catch (NumberFormatException e) {
                Platform.runLater(()->GUIUtils.showDialogMessage(Alert.AlertType.ERROR, "Error", "Invalid quantities entered.", stackPaneContent));
            } catch (Exception e) {
                Platform.runLater(()->GUIUtils.showDialogMessage(Alert.AlertType.ERROR, "Error", e.getMessage(), stackPaneContent));
            }finally {
                Platform.runLater(()->{
                    buttonSend.setText("Send");
                    buttonSend.setDisable(false);
                });
            }

        }).start();
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
                detailsButton.setText("View");

                detailsButton.setOnAction(event -> {
                    BloodRequest request = (BloodRequest) getTableRow().getItem();
                    loadDetailsPane((selectedRequest = request));
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

        setModelBloodStockRD(request);
    }

    private void setModelBloodStockRD(BloodRequest request) {
        modelBloodStockRD.setAll(mainService.getBloodComponentsByRequest(request));
    }

    private void initTableDetails() {
        columnTypeRD.setCellValueFactory(new PropertyValueFactory<>("bloodComponent"));
        columnGroupRD.setCellValueFactory(new PropertyValueFactory<>("aboBloodGroup"));
        columnQuantityRD.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        columnRhRD.setCellValueFactory(new PropertyValueFactory<>("rhBloodGroup"));
        columnStatusRD.setCellValueFactory(new PropertyValueFactory<>("bloodStatus"));
        columnExpDateRD.setCellValueFactory(x-> new SimpleStringProperty(x.getValue().getExpirationDate().toString().split(" ")[0]));
        tableBloodStockRD.setItems(modelBloodStockRD);
    }

    private void loadRequests() {
        model.setAll(mainService.getBloodRequestsDoctor(username));
    }

    private void initChecks() {
        checkThrombocytes.setOnAction(ev -> {
            if (checkThrombocytes.isSelected()) {
                labelTromboQuant.setVisible(true);
                textQuantityThrombocytes.setVisible(true);
            }
            else {
                textQuantityThrombocytes.setVisible(false);
                labelTromboQuant.setVisible(false);
            }
        });

        checkLeukocytes.setOnAction(ev -> {
            if (checkLeukocytes.isSelected()) {
                textQuantityLeukocytes.setVisible(true);
                labelLeukocytesQuant.setVisible(true);
            }
            else {
                textQuantityLeukocytes.setVisible(false);
                labelLeukocytesQuant.setVisible(false);
            }
        });

        checkPlasma.setOnAction(ev -> {
            if (checkPlasma.isSelected()) {
                textQuantityPlasma.setVisible(true);
                labelPlasmaQuant.setVisible(true);
            }
            else {
                textQuantityPlasma.setVisible(false);
                labelPlasmaQuant.setVisible(false);
            }
        });
    }

    private void initCombos() {
        comboBloodGroup.getItems().setAll(ABOBloodGroup.values());
        comboRh.getItems().setAll(RhBloodGroup.values());
    }

    private void initLabels(){
        labelPlasmaQuant.setText(mainService.getBloodComponentQuantity() + "(ml) x ");
        labelTromboQuant.setText(mainService.getBloodComponentQuantity() + "(ml) x ");
        labelLeukocytesQuant.setText(mainService.getBloodComponentQuantity() + "(ml) x ");
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

    private Region buildDialogContent(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/Email.fxml"));

        Region region = null;

        try {
             region =  loader.load();
             emailController = loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return region;
    }


    @Override
    public void setMainService(IMainService mainService, String username, Stage stageLogin) {
        super.setMainService(mainService, username, stageLogin);
        DoctorProfile profile = mainService.getDoctorProfile(username);
        labelNumeDoctor.setText(profile.getFirstName() + " " + profile.getLastName());
        initLabels();
        loadRequests();
    }

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

    @Override
    public void reloadDoctorTables() throws RemoteException {

        new Thread(()-> {

            try {

                Platform.runLater(()->{
                    GUIUtils.showDialogMessage(Alert.AlertType.INFORMATION,"Information","Request has been updated!",stackPaneContent);
                });

                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Platform.runLater(()->{
                toggleView(bloodStatus, buttonAdditional);
                loadRequests();
            });

        }).start();

    }

}
