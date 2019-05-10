package paymentWizard;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import se.chalmers.cse.dat216.project.*;

public class paymentWizard {

    @FXML private AnchorPane wizardPane;

    // Första sidan
    @FXML private AnchorPane pageOne;
    @FXML private Label deliveryTitle;
    @FXML private RadioButton deliveryNormal;
    @FXML private RadioButton deliveryExpress;
    @FXML private ImageView POCancel;
    @FXML private ImageView PONext;

    // Andra sidan
    @FXML private AnchorPane pageTwo;
    @FXML private Label payTitle;
    @FXML private RadioButton payCard;
    @FXML private RadioButton payInvoice;
    @FXML private ImageView PTPay;
    @FXML private ImageView PTPrevious;
    @FXML private Label priceLabel;

    // Tredje sidan
    @FXML private AnchorPane pageThree;
    @FXML private Label titleConfirm;
    @FXML private ImageView backToFront;


    // Sätter priset på label. TODO Fixa.

    /*
    int price = (int) priceLabel.getPrice();
            this.priceLabel.setText(price + " kr");

    */

    // Metoder för feedback

    @FXML
    public void cancelEnter(){
        POCancel.setImage(new Image(getClass().getClassLoader().getResourceAsStream(
                "paymentWizard/wizardPictures/CancelClick_00000.png")));
    }

    @FXML
    public void nextEnter(){
        PONext.setImage(new Image(getClass().getClassLoader().getResourceAsStream(
                "paymentWizard/wizardPictures/NextClick_00000.png")));
    }

    @FXML
    public void backEnter(){
        PTPrevious.setImage(new Image(getClass().getClassLoader().getResourceAsStream(
                "paymentWizard/wizardPictures/BackClick_00000.png")));
    }

    @FXML
    public void payEnter(){
        PTPay.setImage(new Image(getClass().getClassLoader().getResourceAsStream(
                "paymentWizard/wizardPictures/PayClick_00000.png")));
    }

    @FXML
    public void BTFEnter(){
        backToFront.setImage(new Image(getClass().getClassLoader().getResourceAsStream(
                "paymentWizard/wizardPictures/BTFClick_00000.png")));
    }


    //// ---------------------- ////

    @FXML
    public void cancelExited(){
        POCancel.setImage(new Image(getClass().getClassLoader().getResourceAsStream(
                "paymentWizard/wizardPictures/CancelNormal_00000.png")));
    }

    @FXML
    public void nextExited(){
        PONext.setImage(new Image(getClass().getClassLoader().getResourceAsStream(
                "paymentWizard/wizardPictures/NextNormal_00000.png")));
    }

    @FXML
    public void backExited(){
        PTPrevious.setImage(new Image(getClass().getClassLoader().getResourceAsStream(
                "paymentWizard/wizardPictures/BackNormal_00000.png")));
    }

    @FXML
    public void payExited(){
        PTPay.setImage(new Image(getClass().getClassLoader().getResourceAsStream(
                "paymentWizard/wizardPictures/PayNormal_00000.png")));
    }

    @FXML
    public void BTFExited(){
        backToFront.setImage(new Image(getClass().getClassLoader().getResourceAsStream(
                "paymentWizard/wizardPictures/BTFNormal_00000.png")));
    }

    // Metoder för knapptryck

    @FXML
    public void cancelClicked(){

        // TODO: Lägg till funktionalitet för att stänga fönstret.

    }

    @FXML
    public void nextClick(){
        pageTwo.toFront();
    }

    @FXML
    public void payClick(){
        pageThree.toFront();
    }

    @FXML
    public void backClick(){
        pageOne.toFront();
    }

    @FXML
    public void BTFClick(){

        // TODO: Lägg till funktionalitet för att återgå till startsidan.

    }

/*
    // Metoder för frakt

         private ToggleGroup deliveryToggleGroup;

        deliveryToggleGroup = new ToggleGroup();
        deliveryNormal.setToggleGroup(deliveryToggleGroup);
        deliveryExpress.setToggleGroup(deliveryToggleGroup);
        deliveryNormal.setSelected(true);
        deliveryToggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
        if (deliveryToggleGroup.getSelectedToggle() != null) {
            RadioButton selected = (RadioButton) deliveryToggleGroup.getSelectedToggle();
        }


    // Metoder för betalningsmetod

        ToggleGroup paymentToggleGroup;

        paymentToggleGroup = new ToggleGroup();
        payCard.setToggleGroup(paymentToggleGroup);
        payInvoice.setToggleGroup(paymentToggleGroup);
        payCard.setSelected(true);
        paymentToggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (paymentToggleGroup.getSelectedToggle() != null) {
                RadioButton selected = (RadioButton) paymentToggleGroup.getSelectedToggle();
            }



     //

    @FXML
    public void mouseTrap(Event event){
        event.consume();
    }

*/
}
