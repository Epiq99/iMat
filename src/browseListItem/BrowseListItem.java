package browseListItem;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import se.chalmers.cse.dat216.project.*;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class BrowseListItem extends AnchorPane {

    IMatDataHandler handler = IMatDataHandler.getInstance();

    private Product product;

    @FXML Label itemNameLable;
    @FXML Label priceLable;
    @FXML Label unitLable;
    @FXML ImageView itemImage;
    @FXML ImageView plusImage;
    @FXML ImageView minusImage;
    @FXML Button addButton;
    @FXML TextField amountField;
    @FXML ImageView favoriteImage;

    public BrowseListItem(Product prod) {

        product = prod;

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

        plusImage.setImage(new Image("images/add.png"));
        minusImage.setImage(new Image("images/minus.png"));

        if(handler.isFavorite(product))
            favoriteImage.setImage(new Image("images/favorite_full.png"));
        else
            favoriteImage.setImage(new Image("images/favorite_empty.png"));

        favoriteImage.setVisible(false);

        //amountField.setTextFormatter(new TextFormatter<>(new DoubleStringConverter(), 1.0));
        amountField.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(), 1));

        addButton.addEventHandler(ActionEvent.ACTION, (event)->
                handler.getShoppingCart().addProduct(product, Double.parseDouble(amountField.getText()))
        );

        //handler.getShoppingCart().addProduct(product, Double.parseDouble(amountField.getText()));

        plusImage.addEventHandler(MouseEvent.MOUSE_CLICKED, event->
                amountField.setText(String.valueOf(Integer.parseInt(amountField.getText()) + 1))
        );

        minusImage.addEventHandler(MouseEvent.MOUSE_CLICKED, event->
                amountField.setText(String.valueOf(Integer.parseInt(amountField.getText()) - 1))
        );

        this.addEventHandler(MouseEvent.MOUSE_ENTERED, event->
                favoriteImage.setVisible(true)
                );

        this.addEventHandler(MouseEvent.MOUSE_EXITED, event->
                favoriteImage.setVisible(false)
                );

        favoriteImage.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> favoriteImage_click(event));
    }

    private void favoriteImage_click(MouseEvent event){
        if(handler.isFavorite(product)){
            handler.removeFavorite(product);
            favoriteImage.setImage(new Image("images/favorite_empty.png"));
        }
        else{
            handler.addFavorite(product);
            favoriteImage.setImage(new Image("images/favorite_full.png"));
        }
    }
}
