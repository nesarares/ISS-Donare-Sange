package donation.client.controllers;

import donation.services.IMainService;
import donation.utils.IObserver;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

public abstract class AbstractController implements Initializable,IObserver {
    private LoginController loginController;
    protected IMainService mainService;
    private String username;
    private Stage stageView;
    private ControllerRoot controllerRoot;

    @FXML
    protected void handleLogoutEvent(ActionEvent e) {
        System.out.println("Logging out...");


        System.out.println("Observers before logout->" + controllerRoot.getObservers().size());

        mainService.logout(username,null);

        controllerRoot.getObservers().clear();
        System.out.println("Observers after logout->" + controllerRoot.getObservers().size());
        loginController.showLoginWindow();

        stageView.close();


    }

    public void setMainService(IMainService mainService, String username, Stage stageView) {
        this.mainService = mainService;
        this.username = username;
        this.stageView = stageView;

        stageView.setOnCloseRequest(ev -> {
            mainService.logout(username, null);
            System.exit(0);
        });
    }

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

    public IMainService getMainService() {
        return mainService;
    }

    public String getUsername() {
        return username;
    }

    public void setControllerRoot(ControllerRoot controllerRoot){
        this.controllerRoot = controllerRoot;
    }
}
