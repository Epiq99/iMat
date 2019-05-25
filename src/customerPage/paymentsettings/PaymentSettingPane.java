package customerPage.paymentsettings;


import customerPage.SettingsPane;
import customerPage.personaldatapane.PersonalDataPane;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.MapChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;

import java.io.IOException;

public class PaymentSettingPane extends SettingsPane {

    IMatDataHandler handler = IMatDataHandler.getInstance();

    @FXML RadioButton cardPayRadioButton, deliveryRadioButton, billRadioButton;
    @FXML TextField entry1, entry2, entry3, entry4, entry5;
    @FXML Label billingAddress;
    private final TextField entries[];

    private static PaymentSettingPane self;

    private PaymentSettingPane() {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("paymentsettingpane.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        entries = new TextField[]{entry1, entry2, entry3, entry4, entry5};

        cardPayRadioButton.setSelected(true);

        cardPayRadioButton.getToggleGroup().selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if(cardPayRadioButton.isSelected()) {
                    for(TextField t: entries)
                        t.setDisable(false);
                }else
                {
                    for(TextField t: entries)
                        t.setDisable(true);
                }
            }
        });
    }

    public static PaymentSettingPane getInstance(){
        if(self == null)
            self= new PaymentSettingPane();

        return self;
    }

    @Override
    public void update() {
        billingAddress.setText(handler.getCustomer().getAddress());
    }
}
