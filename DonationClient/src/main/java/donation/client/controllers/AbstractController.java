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
    protected String username;
    private Stage stageView;
    private ControllerRoot controllerRoot;

    @FXML
    protected void handleLogoutEvent(ActionEvent e) {
        System.out.println(controllerRoot.getObservers().size());
        mainService.logout(username,null);
        controllerRoot.getObservers().clear();
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

    public void setUsername(String username) {
        this.username = username;
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
