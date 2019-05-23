package paymentWizard;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
    @FXML AnchorPane cancelButton, nextButton, backButton;
    @FXML StackPane contenPane;
    @FXML AnchorPane deliveryPane, payPane, donePane;
    @FXML FlowPane reciptList;
    @FXML Label finalPriceLabel, nextButtonLabel;

    AnchorPane[] wizPanes;
    Circle[] circles;

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
        wizPanes = new AnchorPane[]{deliveryPane, payPane, donePane};
        circles = new Circle[]{indicatorCircle, indicatorCircle1, indicatorCircle2};

        nextButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> nextClicked());
        cancelButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event->returnByCansel());
        backButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event->backClicked());

        update();
    }

    private void nextClicked() {

        if(++currentPaneIndex >= wizPanes.length) {
            successfulReturn();
            return;
        }

        update();

    }

    private void backClicked(){
        if(currentPaneIndex == 0){
            return;
        }

        currentPaneIndex--;
        update();
    }

    private void update(){
        for(Circle c: circles)
            c.setRadius(smallCircleRad);

        backButton.setVisible(true);
        circles[currentPaneIndex].setRadius(bigCircleRad);
        contenPane.getChildren().clear();
        contenPane.getChildren().add(wizPanes[currentPaneIndex]);

        switch(currentPaneIndex){
            case 0:
                backButton.setVisible(false);
                break;
            case 1:

                finalPriceLabel.setText(String.valueOf(handler.getShoppingCart().getTotal()));
                nextButtonLabel.setText("Betala");
                break;
            case 2:
                reciptList.getChildren().clear();
                for(ShoppingItem s: handler.getShoppingCart().getItems())
                    reciptList.getChildren().add(new ReciptItem(s.getProduct().getName(), (int) s.getAmount(), s.getTotal()));
                //TODO: Lägg till fraktkostnad i kvittot

                nextButtonLabel.setText("Avsluta");
                break;
        }
    }

    private void successfulReturn(){
        handler.getShoppingCart().clear();
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
