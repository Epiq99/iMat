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
import java.util.List;

public class paymentWizard extends AnchorPane {

    @FXML Circle indicatorCircle, indicatorCircle1, indicatorCircle2, indicatorCircle3;
    @FXML AnchorPane cancelButton, nextButton;
    @FXML StackPane contenPane;
    @FXML AnchorPane deliveryPane, payPane, recieptPane, donePane;
    @FXML FlowPane reciptList;
    @FXML Label finalPriceLabel;

    AnchorPane[] wizPanes;
    Circle[] circles;

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
        wizPanes = new AnchorPane[]{deliveryPane, payPane, recieptPane, donePane};
        circles = new Circle[]{indicatorCircle, indicatorCircle1, indicatorCircle2, indicatorCircle3};

        for(Circle c: circles)
            c.setRadius(5);

        for(ShoppingItem s: handler.getShoppingCart().getItems())
            reciptList.getChildren().add(new ReciptItem(s.getProduct().getName(), (int) s.getAmount(), s.getTotal()));

        finalPriceLabel.setText(String.valueOf(handler.getShoppingCart().getTotal()));

        circles[currentPaneIndex].setRadius(10);
        contenPane.getChildren().clear();
        contenPane.getChildren().add(wizPanes[currentPaneIndex]);

        nextButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> nextClicked());
    }

    private void nextClicked() {

        if(currentPaneIndex >= wizPanes.length) {
            successfulReturn();
            return;
        }
        circles[currentPaneIndex++].setRadius(5);
        circles[currentPaneIndex].setRadius(10);

        contenPane.getChildren().clear();
        contenPane.getChildren().add(wizPanes[currentPaneIndex]);
    }

    private void successfulReturn(){

    }

    private void returnByCansel(){

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
