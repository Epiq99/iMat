package paymentWizard;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import paymentWizard.reciptItem.ReciptItem;
import se.chalmers.cse.dat216.project.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class paymentWizard extends AnchorPane {

    private static Image cvcHelpImage = new Image("images/CVCHelp.jpg");
    private static Image closeImage = new Image("images/close.png");

    @FXML Circle indicatorCircle, indicatorCircle1, indicatorCircle2;
    @FXML Button cancelButton, nextButton, backButton;
    @FXML StackPane contenPane;
    @FXML AnchorPane deliveryPane, payPane, donePane;
    @FXML FlowPane reciptList;
    @FXML Label finalPriceLabel;
    @FXML Label indicatorLabel, indicatorLabel1, indicatorLabel2;
    @FXML RadioButton expressRadioButton, cardPayRadioButton;
    @FXML Label billingAddress, lastFoutLabel;
    @FXML TextField cvcEntry;
    @FXML Button questionButton;
    @FXML AnchorPane cvcHelp;
    @FXML ImageView cvcHelpImageView, cvcHelpCloseImageView;

    Label[] indicatorLabels;
    Circle[] circles;

    private EventHandler nextButtonEvent, backButtonEvent;

    private static List<IPaymentWizardListener> listeners = new ArrayList<>();

    private static final int bigCircleRad = 20;
    private static final int smallCircleRad = 10;

    private int currentPaneIndex;
    private IMatDataHandler handler = IMatDataHandler.getInstance();

    public paymentWizard() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("paymentWizard.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (
                IOException exception) {
            throw new RuntimeException(exception);
        }

        currentPaneIndex = 0;
        indicatorLabels = new Label[]{indicatorLabel, indicatorLabel1, indicatorLabel2};
        circles = new Circle[]{indicatorCircle, indicatorCircle1, indicatorCircle2};
        cvcEntry.textProperty().addListener(((observable, oldValue, newValue) -> cvcCodeChange(observable,oldValue,newValue)));
        cardPayRadioButton.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

                if(!newValue){
                    cvcEntry.setDisable(true);
                    editNavButtons(event -> setUpDeliveryPage(),event->setUpDonePage(), 1);
                }
                else {
                    cvcEntry.setDisable(false);
                    cvcCodeChange(null, cvcEntry.getText(), cvcEntry.getText());
                }

            }
        });

        cvcHelp.setVisible(false);
        cvcHelpImageView.setImage(cvcHelpImage);
        cvcHelpCloseImageView.setImage(closeImage);
        cvcHelpCloseImageView.addEventHandler(MouseEvent.MOUSE_CLICKED, event->cvcHelp.setVisible(false));
        questionButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> cvcHelp.setVisible(true));

        cancelButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> returnByCancel());
        setUpDeliveryPage();
    }

    private void setUpDeliveryPage(){
        editNavButtons(null, event -> setUpPaymentPage(), 0);
        backButton.setVisible(false);
        contenPane.getChildren().clear();
        contenPane.getChildren().add(deliveryPane);
    }

    private void setUpPaymentPage(){
        editNavButtons(event-> setUpDeliveryPage(), null, 1);
        contenPane.getChildren().clear();
        contenPane.getChildren().add(payPane);
        cvcEntry.setText("");
        if(!handler.getCreditCard().getCardNumber().isEmpty())
            lastFoutLabel.setText(handler.getCreditCard().getCardNumber().substring(11));

        finalPriceLabel.setText(String.valueOf(handler.getShoppingCart().getTotal() + (expressRadioButton.isSelected()?50:0)));
        billingAddress.setText(handler.getCustomer().getAddress());
        nextButton.setText("Betala");
    }

    private void setUpDonePage(){
        editNavButtons(null , event ->successfulReturn(), 2);
        contenPane.getChildren().clear();
        contenPane.getChildren().add(donePane);
        reciptList.getChildren().clear();
        for(ShoppingItem s: handler.getShoppingCart().getItems())
            reciptList.getChildren().add(new ReciptItem(s.getProduct().getName(), (int) s.getAmount(), s.getTotal()));

        cancelButton.setVisible(false);
        nextButton.setText("Avsluta");
    }

    private void cvcCodeChange(Object a, String oldValue, String newValue){
        if(newValue.length() < 4) {
            editNavButtons(event -> setUpDeliveryPage(),null, 1);
            return;
        }

        cvcEntry.setText(newValue.substring(0,4));
        editNavButtons(event -> setUpDeliveryPage(),event->setUpDonePage(), 1);
    }

    private void editNavButtons(EventHandler backEvent, EventHandler nextEvent, int pageIndex){

        if(backButtonEvent != null)
            backButton.removeEventHandler(MouseEvent.MOUSE_CLICKED, backButtonEvent);

        if(nextButtonEvent != null)
            nextButton.removeEventHandler(MouseEvent.MOUSE_CLICKED, nextButtonEvent);

        if(backEvent != null) {
            backButton.addEventHandler(MouseEvent.MOUSE_CLICKED, backEvent);
            changeStyleClass(backButton,"button");
        }
        else
            changeStyleClass(backButton, "inactive-button");

        if(nextEvent != null) {
            nextButton.addEventHandler(MouseEvent.MOUSE_CLICKED, nextEvent);
            changeStyleClass(nextButton,"button");
        }
        else
            changeStyleClass(nextButton,"inactive-button");

        backButtonEvent = backEvent;
        nextButtonEvent = nextEvent;

        activateGuide(pageIndex);
    }

    private void activateGuide(int i) {
        for(int j=0; j < circles.length; j++){
            circles[j].setRadius(smallCircleRad);
            changeStyleClass(circles[j],"inactive-indicator");
            changeStyleClass(indicatorLabels[j],"inactive-indicator");
        }

        circles[i].setRadius(bigCircleRad);
        changeStyleClass(circles[i],"active-indicator");
        changeStyleClass(indicatorLabels[i],"active-indicator");
    }

    private void successfulReturn(){
        handler.placeOrder(true);
        notifyToReturn();
    }

    private void returnByCancel(){
        notifyToReturn();
    }

    private void notifyToReturn(){
        for(IPaymentWizardListener l: listeners)
            l.notifyOnReturn(this);
    }

    public static void addListener(IPaymentWizardListener listener){
        listeners.add(listener);
    }

    private static void changeStyleClass(Node obj, String className){
        obj.getStyleClass().clear();
        obj.getStyleClass().add(className);
    }
}
