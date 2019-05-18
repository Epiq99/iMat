package cartPage.cartListItem;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CartListItem extends AnchorPane {

    private static final Image addImage = new Image("images/add.png");
    private static final Image minusImageRes = new Image("images/minus.png");
    private static final Image trashImage = new Image("images/trash.png");

    IMatDataHandler handler = IMatDataHandler.getInstance();

    private static final List<ICartItemListener> listeners = new ArrayList<>();
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

        plusImage.addEventHandler(MouseEvent.MOUSE_CLICKED, event-> plusImageClicked());
        minusImage.addEventHandler(MouseEvent.MOUSE_CLICKED, event->minusImageClicked());
        trashImageView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> trashImageClicked());

    }

    private void trashImageClicked(){
        handler.getShoppingCart().removeItem(shoppingItem);
        shoppingItem.setAmount(0);
        notifyOnDelete();
    }

    private void minusImageClicked(){
        if(shoppingItem.getAmount()==0)
            return;

        shoppingItem.setAmount(shoppingItem.getAmount()-1);
        amountTextField.setText(String.valueOf((int)shoppingItem.getAmount()));

        if(shoppingItem.getAmount()==0){
            handler.getShoppingCart().removeItem(shoppingItem);
            notifyOnDelete();
        }

        updateSum();
        notifyOnChange();
    }

    private void plusImageClicked(){
        if(!handler.getShoppingCart().getItems().contains(shoppingItem))
            handler.getShoppingCart().addItem(shoppingItem);

        shoppingItem.setAmount(shoppingItem.getAmount() + 1);
        amountTextField.setText(String.valueOf((int)shoppingItem.getAmount()));

        updateSum();
        notifyOnChange();
    }

    private void updateSum(){
        sumLabel.setText(String.valueOf(shoppingItem.getTotal()));
    }

    public static void addListener(ICartItemListener listener){
        listeners.add(listener);
    }


    private void notifyOnChange(){
        for(ICartItemListener l: listeners)
            l.changeValues(this);
    }

    private void notifyOnDelete(){
        for(ICartItemListener l: listeners)
            l.removeFromList(this);
    }
}
