package donation.client.controllers;

import donation.services.IMainService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

public abstract class AbstractController implements Initializable {
    private LoginController loginController;
    private IMainService mainService;
    private String username;
    private Stage stageView;

    @FXML
    protected void handleLogoutEvent(ActionEvent e) {
        System.out.println("Logging out...");
        mainService.logout(username, null);
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
}
