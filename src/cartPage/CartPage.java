package cartPage;


import cartPage.cartListItem.CartListItem;
import cartPage.cartListItem.ICartItemListener;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
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
import javafx.scene.layout.FlowPane;
import javafx.util.converter.IntegerStringConverter;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CartPage extends AnchorPane implements ICartItemListener {

    IMatDataHandler handler = IMatDataHandler.getInstance();

    private static final List<ICartPageListener> listeners = new ArrayList<>();
    private static CartPage self;
    EventHandler payEvent;

    @FXML FlowPane itemFlowPane;
    @FXML Label totalSumLabel;
    @FXML Button payButton;

    private CartPage() {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("cartpage.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        CartListItem.addListener(this);

        payEvent = event->notifyOnCheckout();
        updateItemList();
    }

    public void updateItemList(){
        itemFlowPane.getChildren().clear();
        for(ShoppingItem s: handler.getShoppingCart().getItems())
            itemFlowPane.getChildren().add(new CartListItem(s));
        
        totalSumLabel.setText(String.format("%.2f", handler.getShoppingCart().getTotal()));

        if(handler.getShoppingCart().getItems().isEmpty() && !payButton.getStyleClass().contains("inactive-button")){
            payButton.getStyleClass().add("inactive-button");
            payButton.removeEventHandler(MouseEvent.MOUSE_CLICKED, payEvent);
        } else {
            payButton.getStyleClass().remove("inactive-button");
            payButton.addEventHandler(MouseEvent.MOUSE_CLICKED, payEvent);
        }
    }

    public static CartPage getInstance(){
        if(self == null)
            self = new CartPage();

        return self;
    }

    @Override
    public void removeFromList(CartListItem item) {
        updateItemList();
        notifyOnCartChange();
    }

    @Override
    public void changeValues(CartListItem item) {
        totalSumLabel.setText(String.valueOf(handler.getShoppingCart().getTotal()));
    }

    public static void addListener(ICartPageListener listener) {
        listeners.add(listener);
    }

    private void notifyOnCartChange(){
        for(ICartPageListener l: listeners)
            l.onCartChanged(this);
    }

    private void notifyOnCheckout(){
        for(ICartPageListener l: listeners)
            l.onCheckoutClick(this);
    }
}
