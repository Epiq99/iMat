package cartPage;


import cartPage.cartListItem.CartListItem;
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
import javafx.scene.layout.FlowPane;
import javafx.util.converter.IntegerStringConverter;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CartPage extends AnchorPane {

    IMatDataHandler handler = IMatDataHandler.getInstance();

    private static CartPage self;

    @FXML FlowPane itemFlowPane;

    private CartPage() {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("cartpage.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        updateItemList();
    }

    public void updateItemList(){
        itemFlowPane.getChildren().clear();
        for(ShoppingItem s: handler.getShoppingCart().getItems())
            itemFlowPane.getChildren().add(new CartListItem(s));
    }

    public static CartPage getInstance(){
        if(self == null)
            self = new CartPage();

        return self;
    }
}
