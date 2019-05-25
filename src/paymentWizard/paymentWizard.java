package paymentWizard;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

    @FXML Circle indicatorCircle, indicatorCircle1, indicatorCircle2;
    @FXML Button cancelButton, nextButton, backButton;
    @FXML StackPane contenPane;
    @FXML AnchorPane deliveryPane, payPane, donePane;
    @FXML FlowPane reciptList;
    @FXML Label finalPriceLabel;
    @FXML Label indicatorLabel, indicatorLabel1, indicatorLabel2;

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

        setUpDeliveryPage();
    }

    private void setUpDeliveryPage(){
        editNavButtons(null, event -> setUpPaymentPage(), 0);
        backButton.setVisible(false);
        contenPane.getChildren().clear();
        contenPane.getChildren().add(deliveryPane);
    }

    private void setUpPaymentPage(){
        editNavButtons(event-> setUpDeliveryPage(), event ->setUpDonePage(), 1);
        contenPane.getChildren().clear();
        contenPane.getChildren().add(payPane);
        finalPriceLabel.setText(String.valueOf(handler.getShoppingCart().getTotal()));
        nextButton.setText("Betala");
    }

    private void setUpDonePage(){
        editNavButtons(null , event ->successfulReturn(), 2);
        contenPane.getChildren().clear();
        contenPane.getChildren().add(donePane);
        reciptList.getChildren().clear();
        for(ShoppingItem s: handler.getShoppingCart().getItems())
            reciptList.getChildren().add(new ReciptItem(s.getProduct().getName(), (int) s.getAmount(), s.getTotal()));
    }

    private void editNavButtons(EventHandler backEvent, EventHandler nextEvent, int pageIndex){

        if(backButtonEvent != null)
            backButton.removeEventHandler(MouseEvent.MOUSE_CLICKED, backButtonEvent);

        if(nextButtonEvent != null)
            nextButton.removeEventHandler(MouseEvent.MOUSE_CLICKED, nextButtonEvent);

        if(backEvent != null)
            backButton.addEventHandler(MouseEvent.MOUSE_CLICKED, backEvent);

        if(nextEvent != null)
            nextButton.addEventHandler(MouseEvent.MOUSE_CLICKED, nextEvent);

        backButtonEvent = backEvent;
        nextButtonEvent = nextEvent;

        activateGuide(pageIndex);
    }

    private void activateGuide(int i) {
        for(Circle c: circles)
            c.setRadius(smallCircleRad);

        for(Label l: indicatorLabels)
        {
            //TODO: set standard
        }
    }

    private void successfulReturn(){
        handler.placeOrder(true);
        notifyToReturn();
    }

    private void returnByCansel(){
        notifyToReturn();
    }

    private void notifyToReturn(){
        for(IPaymentWizardListener l: listeners)
            l.notifyOnReturn(this);
    }

    public static void addListener(IPaymentWizardListener listener){
        listeners.add(listener);
    }

    /*
     private  void updateCardInfo () {


        cardLabel.setText(iMatDataHandler.getCreditCard().getCardNumber());

    }

    private void updateRecipe(ShoppingCart shoppingCart){

        StringBuilder sb = new StringBuilder();
        List<ShoppingItem> itemsList = shoppingCart.getItems();

        for ( ShoppingItem product : itemsList) {
            sb.append(product).append(" - ").append(product.getProduct().getPrice()).append("\n");
        }
        sb.append("\n").append("Totalpris: ").append(shoppingCart.getTotal());
        recipeList.setText(sb.toString());

    }

    */
}
