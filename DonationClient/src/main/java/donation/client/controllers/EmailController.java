package donation.client.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDialog;
import donation.client.utils.mailer.Mailer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class EmailController implements Initializable{

    @FXML
    private JFXCheckBox checkBoxGmail,checkBoxYahoo;

    @FXML
    private JFXButton buttonDone;

    private JFXDialog openedDialog;

    private void handleButtonDone(ActionEvent event){

        if(checkBoxGmail.isSelected() ) {
            Mailer.openGmail();
            openedDialog.close();
            return;
        }

        Mailer.openYahoo();
        openedDialog.close();
    }

    void setOpenedDialog(JFXDialog openedDialog) {
        this.openedDialog = openedDialog;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buttonDone.setOnAction(this::handleButtonDone);

        checkBoxGmail.setSelected(true);

        checkBoxGmail.setOnAction(ev->{
            checkBoxYahoo.setSelected(false);
            checkBoxGmail.setSelected(true);
        });

        checkBoxYahoo.setOnAction(ev->{
            checkBoxGmail.setSelected(false);
            checkBoxYahoo.setSelected(true);
        });
    }

}
