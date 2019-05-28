package customerPage.personaldatapane;


import customerPage.SettingsPane;
import detailedview.IDetailedViewListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.converter.IntegerStringConverter;
import se.chalmers.cse.dat216.project.Customer;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PersonalDataPane extends SettingsPane {

    IMatDataHandler handler = IMatDataHandler.getInstance();

    @FXML TextField userNameEntry, firstNameEntry, lastNameEntry, phoneNumberEntry,
            emailEntry, addressEntry, postalCodeEntry, mobilePhoneEntry;
    @FXML Button changeButton;

    private final TextField entries[];

    private boolean editModeOn = false;
    private static PersonalDataPane self;

    private PersonalDataPane() {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("personaldatapane.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        update();

        entries = new TextField[]{userNameEntry, firstNameEntry, lastNameEntry, phoneNumberEntry,
                emailEntry, addressEntry, postalCodeEntry, mobilePhoneEntry};

        for(TextField e: entries)
            e.setDisable(!editModeOn);

        changeButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event->onChangeButtonClicked());


    }


    private void onChangeButtonClicked(){
        if(editModeOn)
            changeButton.setText("Ändra");
        else
            changeButton.setText("Spara");

        editModeOn = !editModeOn;

        for(TextField e: entries)
            e.setDisable(!editModeOn);

        Customer c = handler.getCustomer();
        c.setFirstName(firstNameEntry.getText());
        c.setLastName(lastNameEntry.getText());
        c.setPhoneNumber(phoneNumberEntry.getText());
        c.setMobilePhoneNumber(mobilePhoneEntry.getText());
        c.setEmail(emailEntry.getText());
        c.setAddress(addressEntry.getText());
        c.setPostAddress(postalCodeEntry.getText());
    }

    public static PersonalDataPane getInstance(){
        if(self == null)
            self= new PersonalDataPane();

        return self;
    }

    public TextField[] getEtries(){return entries;}

    @Override
    public void update() {
        Customer c = handler.getCustomer();
        firstNameEntry.setText(c.getFirstName());
        lastNameEntry.setText(c.getLastName());
        phoneNumberEntry.setText(c.getPhoneNumber());
        mobilePhoneEntry.setText(c.getMobilePhoneNumber());
        addressEntry.setText(c.getAddress());
        postalCodeEntry.setText(c.getPostAddress());
        emailEntry.setText(c.getEmail());
    }
}
