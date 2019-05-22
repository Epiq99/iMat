package browseListItem;


import javafx.scene.input.MouseEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.util.converter.IntegerStringConverter;
import se.chalmers.cse.dat216.project.*;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BrowseListItem extends AnchorPane {

    IMatDataHandler handler = IMatDataHandler.getInstance();

    private static final Image addImage = new Image("images/add.png");
    private static final Image minusImageRes = new Image("images/minus.png");
    private static final Image favoriteFullImage = new Image("images/favorite_full.png");
    private static final Image favoriteEmptyImage = new Image("images/favorite_empty.png");

    public static final List<IBrowseListItemListener> listeners = new ArrayList<>();

    private final ShoppingItem shoppingItem;
    private final Product product;
    private int amount = 0;

    @FXML Label itemNameLable;
    @FXML Label priceLable;
    @FXML Label unitLable;
    @FXML ImageView itemImage;
    @FXML ImageView plusImage;
    @FXML ImageView minusImage;
    @FXML TextField amountField;
    @FXML ImageView favoriteImage;
    @FXML AnchorPane mainPane;

    public BrowseListItem(Product prod) {

        product = prod;
        shoppingItem = new ShoppingItem(product, 0);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("browselistitem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        itemNameLable.setText(prod.getName());
        priceLable.setText(String.valueOf(prod.getPrice()));
        unitLable.setText(prod.getUnit());
        itemImage.setImage(handler.getFXImage(prod));

        plusImage.setImage(addImage);
        minusImage.setImage(minusImageRes);

        if(handler.isFavorite(product))
            favoriteImage.setImage(favoriteFullImage);
        else
            favoriteImage.setImage(favoriteEmptyImage);

        favoriteImage.setVisible(false);

        //amountField.setTextFormatter(new TextFormatter<>(new DoubleStringConverter(), 1.0));
        amountField.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(), 0));

        //handler.getShoppingCart().addProduct(product, Double.parseDouble(amountField.getText()));

        plusImage.addEventHandler(MouseEvent.MOUSE_CLICKED, event->
                plusImageClicked()
        );

        minusImage.addEventHandler(MouseEvent.MOUSE_CLICKED, event->
                minusImageClicked()
        );

        this.addEventHandler(MouseEvent.MOUSE_ENTERED, event->
                favoriteImage.setVisible(true)
                );

        this.addEventHandler(MouseEvent.MOUSE_EXITED, event->
                favoriteImage.setVisible(false)
                );

        itemNameLable.addEventHandler(MouseEvent.MOUSE_CLICKED, event->notifyOnDetailedView());

        favoriteImage.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> favoriteImage_click(event));
    }

    private void favoriteImage_click(MouseEvent event){
        if(handler.isFavorite(product)){
            handler.removeFavorite(product);
            favoriteImage.setImage(favoriteEmptyImage);
        }
        else{
            handler.addFavorite(product);
            favoriteImage.setImage(favoriteFullImage);
        }
    }

    private void minusImageClicked(){
        if(shoppingItem.getAmount()==0)
            return;

        shoppingItem.setAmount(shoppingItem.getAmount()-1);
        amountField.setText(String.valueOf((int)shoppingItem.getAmount()));

        if(shoppingItem.getAmount()==0) {
            handler.getShoppingCart().removeItem(shoppingItem);
            mainPane.setStyle("-fx-background-color: white");
        }
        notifyOnCartChange();
    }

    private void plusImageClicked(){
        if(!handler.getShoppingCart().getItems().contains(shoppingItem))
            handler.getShoppingCart().addItem(shoppingItem);

        shoppingItem.setAmount(shoppingItem.getAmount() + 1);
        amountField.setText(String.valueOf((int)shoppingItem.getAmount()));
        mainPane.setStyle("-fx-background-color: #E0D565");
        notifyOnCartChange();
    }

    private void notifyOnCartChange(){
        for(IBrowseListItemListener l: listeners)
            l.cartChange(this);
    }

    private void notifyOnDetailedView(){
        for(IBrowseListItemListener l: listeners)
            l.detailedViewNotify(this);
    }

    public static void addListener(IBrowseListItemListener listener){
        listeners.add(listener);
    }

    public Product getProduct(){return product;}
}
