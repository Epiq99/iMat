package cartPage.cartListItem;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;

public class CartListItem extends AnchorPane {

    private static final Image addImage = new Image("images/add.png");
    private static final Image minusImageRes = new Image("images/minus.png");
    private static final Image trashImage = new Image("images/trash.png");

    IMatDataHandler handler = IMatDataHandler.getInstance();

    private final ShoppingItem shoppingItem;

    @FXML Label itemNameLabel;
    @FXML Label itemPriceLabel;
    @FXML Label itemUnitLabel;
    @FXML Label sumLabel;
    @FXML TextField amountTextField;
    @FXML ImageView trashImageView, plusImage, minusImage;

    public CartListItem(ShoppingItem item) {

        shoppingItem = item;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("cartlistitem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        trashImageView.setImage(trashImage);
        plusImage.setImage(addImage);
        minusImage.setImage(minusImageRes);

        itemNameLabel.setText(shoppingItem.getProduct().getName());
        itemPriceLabel.setText(String.valueOf(shoppingItem.getProduct().getPrice()));
        itemUnitLabel.setText(shoppingItem.getProduct().getUnit());
        sumLabel.setText(String.valueOf(shoppingItem.getTotal()));
        amountTextField.setText(String.valueOf((int)shoppingItem.getAmount()));
    }
}
