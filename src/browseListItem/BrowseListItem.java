package browseListItem;


import javafx.event.ActionEvent;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
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

    @FXML Text itemNameLabel;
    @FXML Label priceLable;
    @FXML Label unitLable;
    @FXML ImageView itemImage;
    @FXML ImageView plusImage;
    @FXML ImageView minusImage;
    @FXML TextField amountField;
    @FXML ImageView favoriteImage;
    @FXML AnchorPane mainPane, favIcon;

    public BrowseListItem(ShoppingItem prod) {

        product = prod.getProduct();
        shoppingItem = prod;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("browselistitem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        DropShadow dropShadow = new DropShadow();

        dropShadow.setColor(Color.BLACK);
        dropShadow.setOffsetX(3);
        dropShadow.setOffsetY(3);

        mainPane.setEffect(dropShadow);

        itemNameLabel.setText(product.getName());
        priceLable.setText(String.valueOf(product.getPrice()));
        unitLable.setText(product.getUnit());
        itemImage.setImage(handler.getFXImage(product));

        plusImage.setImage(addImage);
        minusImage.setImage(minusImageRes);


        if(handler.isFavorite(product)) {
            favoriteImage.setImage(favoriteFullImage);
            favIcon.setVisible(true);
        }
        else {
            favoriteImage.setImage(favoriteEmptyImage);
            favIcon.setVisible(false);
        }

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
                favIcon.setVisible(true)
                );

        this.addEventHandler(MouseEvent.MOUSE_EXITED, event-> mouseExit());

        itemNameLabel.addEventHandler(MouseEvent.MOUSE_CLICKED, event->notifyOnDetailedView());
        favIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> favoriteImage_click(event));
        amountField.addEventHandler(ActionEvent.ACTION, event-> onAmountFieldChange());

        update();
    }

    private void mouseExit(){
        if(handler.isFavorite(product))
            return;

        favIcon.setVisible(false);
    }

    public void update(){

        if(!handler.getShoppingCart().getItems().contains(shoppingItem))
            shoppingItem.setAmount(0);

        if(handler.isFavorite(product)) {
            favoriteImage.setImage(favoriteFullImage);
            favIcon.setVisible(true);
        }
        else {
            favoriteImage.setImage(favoriteEmptyImage);
            favIcon.setVisible(false);
        }

        if(shoppingItem.getAmount() !=0)
            mainPane.setStyle("-fx-background-color: #E0D565");
        else {
            mainPane.setStyle("-fx-background-color: white");
            handler.getShoppingCart().removeItem(shoppingItem);
        }

        amountField.setText(String.valueOf((int) shoppingItem.getAmount()));
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

        update();
        notifyOnCartChange();
    }

    private void plusImageClicked(){
        if(!handler.getShoppingCart().getItems().contains(shoppingItem))
            handler.getShoppingCart().addItem(shoppingItem);

        shoppingItem.setAmount(shoppingItem.getAmount() + 1);

        update();
        notifyOnCartChange();
    }

    private void notifyOnCartChange(){
        for(IBrowseListItemListener l: listeners)
            l.cartChange(this);
    }

    private void notifyOnDetailedView(){
        for(IBrowseListItemListener l: listeners)
            l.detailedViewShow(this);
    }

    public static void addListener(IBrowseListItemListener listener){
        listeners.add(listener);
    }

    public Product getProduct(){return product;}

    public ShoppingItem getShoppingItem(){return shoppingItem;}

    private void onAmountFieldChange(){
        if(Integer.parseInt(amountField.getText()) == shoppingItem.getAmount())
            return;

        shoppingItem.setAmount(Double.parseDouble(amountField.getText()));

        if(shoppingItem.getAmount() > 0 && !handler.getShoppingCart().getItems().contains(shoppingItem))
            handler.getShoppingCart().addItem(shoppingItem);

        update();
        notifyOnCartChange();
    }
}
