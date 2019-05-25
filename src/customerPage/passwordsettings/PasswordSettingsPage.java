package customerPage.passwordsettings;


import customerPage.SettingsPane;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;

import java.io.IOException;

public class PasswordSettingsPage extends SettingsPane {

    private static final Image errorImage = new Image("images/error.png");
    private static final Image confirmImage = new Image("images/confirmed.png");

    IMatDataHandler handler = IMatDataHandler.getInstance();

    @FXML PasswordField currentPass, newPass, confirmPass;
    @FXML Button submitButton;
    @FXML AnchorPane errorMessagePane1, errorMessagePane2,successFullChangeMessagePane;
    @FXML ImageView confirmImageView,errorImageView1,errorImageView2,confirmedChangeImageView;

    private static PasswordSettingsPage self;

    private PasswordSettingsPage() {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("passwordsettingspage.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        errorImageView1.setImage(errorImage);
        errorImageView2.setImage(errorImage);
        confirmedChangeImageView.setImage(confirmImage);

        successFullChangeMessagePane.setVisible(false);
        errorMessagePane1.setVisible(false);
        errorMessagePane2.setVisible(false);
        confirmImageView.setVisible(false);

        System.out.println(handler.getUser().getPassword());

        submitButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event->changPass());

        confirmPass.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue.equals(""))
                    confirmImageView.setVisible(false);
                else{
                    confirmImageView.setVisible(true);
                    if(!newValue.equals(newPass.getText()))
                        confirmImageView.setImage(errorImage);
                    else
                        confirmImageView.setImage(confirmImage);
                }
                successFullChangeMessagePane.setVisible(false);
            }
        });

    }

    private void changPass(){

        successFullChangeMessagePane.setVisible(false);

        if(!currentPass.getText().equals(handler.getUser().getPassword())){
            errorMessagePane1.setVisible(true);
            return;
        }
        errorMessagePane1.setVisible(false);

        if(!newPass.getText().equals(confirmPass.getText())) {
            errorMessagePane2.setVisible(true);
            return;
        }
        errorMessagePane2.setVisible(false);
        handler.getUser().setPassword(newPass.getText());

        currentPass.clear();
        confirmPass.clear();
        newPass.clear();

        successFullChangeMessagePane.setVisible(true);
    }

    public static PasswordSettingsPage getInstance(){
        if(self == null)
            self= new PasswordSettingsPage();

        return self;
    }

    @Override
    public void update() {
        confirmPass.setText("");
        newPass.setText("");
        currentPass.setText("");
    }
}
