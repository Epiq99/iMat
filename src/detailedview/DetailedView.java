package detailedview;


import browseListItem.IBrowseListItemListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.converter.IntegerStringConverter;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ProductCategory;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DetailedView extends AnchorPane {

    IMatDataHandler handler = IMatDataHandler.getInstance();

    private static final Image addImage = new Image("images/add.png");
    private static final Image minusImageRes = new Image("images/minus.png");
    private static final Image favoriteFullImage = new Image("images/favorite_full.png");
    private static final Image favoriteEmptyImage = new Image("images/favorite_empty.png");
    private static final Image closeImage = new Image("images/close.png");
    private static final Image ecoImageRes = new Image("images/ekologisk.png");

    private static final List<IDetailedViewListener> listeners = new ArrayList<>();

    private Product product;
    private ShoppingItem shoppingItem;

    @FXML Label priceLabel;
    @FXML Label unitLabel;
    @FXML Label titleLabel;
    @FXML ImageView productImageView;
    @FXML ImageView ecoImage;
    @FXML ImageView favoriteImage;
    @FXML TextField amountField;
    @FXML ImageView plusImage;
    @FXML ImageView minusImage;
    @FXML Label favoriteLable;
    @FXML ImageView closeButton;
    @FXML AnchorPane mainPane;

    public DetailedView(ShoppingItem item) {

        shoppingItem = item;
        product = item.getProduct();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("detailedview.fxml"));
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

        if(shoppingItem.getAmount()>0)
            mainPane.setStyle("-fx-background-color: #E0D565");

        ecoImage.setImage(ecoImageRes);
        titleLabel.setText(product.getName());
        priceLabel.setText(String.valueOf(product.getPrice()));
        unitLabel.setText(product.getUnit());
        productImageView.setImage(handler.getFXImage(product));
        ecoImage.setVisible(product.isEcological());
        favoriteImage.setImage(handler.isFavorite(product)?favoriteFullImage:favoriteEmptyImage);

        plusImage.setImage(addImage);
        minusImage.setImage(minusImageRes);

        amountField.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(), 0));
        amountField.setText(String.valueOf((int) shoppingItem.getAmount()));

        plusImage.addEventHandler(MouseEvent.MOUSE_CLICKED, event-> plusButtonClicked());

        minusImage.addEventHandler(MouseEvent.MOUSE_CLICKED, event-> minusImageClicked());

        favoriteLable.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> onFavoriteClick());
        favoriteImage.addEventHandler(MouseEvent.MOUSE_CLICKED, event->onFavoriteClick());
        closeButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> notifyOnClose());
        closeButton.setImage(closeImage);
        amountField.addEventHandler(ActionEvent.ACTION, event-> amountFieldChanged());
    }

    private void plusButtonClicked(){
        amountField.setText(String.valueOf(Integer.parseInt(amountField.getText()) + 1));
        amountFieldChanged();
    }

    private void minusImageClicked(){
        if(Double.parseDouble(amountField.getText()) <= 0)
            return;

        amountField.setText(String.valueOf(Integer.parseInt(amountField.getText()) - 1));
        amountFieldChanged();
    }

    private void amountFieldChanged(){
        if(Integer.parseInt(amountField.getText()) == shoppingItem.getAmount())
            return;

        shoppingItem.setAmount(Double.parseDouble(amountField.getText()));

        if(shoppingItem.getAmount() <= 0) {
            handler.getShoppingCart().removeItem(shoppingItem);
            mainPane.setStyle("-fx-background-color: white");
        }

        if(shoppingItem.getAmount() > 0 && !handler.getShoppingCart().getItems().contains(shoppingItem)) {
            handler.getShoppingCart().addItem(shoppingItem);
            mainPane.setStyle("-fx-background-color: #E0D565");
        }

        notifyOnCartAdd();
    }

    private void onFavoriteClick(){
        if(handler.isFavorite(product)) {
            handler.removeFavorite(product);
            favoriteImage.setImage(favoriteEmptyImage);
            return;
        }

        handler.addFavorite(product);
        favoriteImage.setImage(favoriteFullImage);
    }

    public static void addListener(IDetailedViewListener listener){
        listeners.add(listener);
    }

    public void notifyOnCartAdd(){
        for(IDetailedViewListener l: listeners)
            l.addToCartNotification(this);
    }

    public void notifyOnClose(){
        for(IDetailedViewListener l: listeners)
            l.closeDetailedView(this);
    }

    public Product getProduct() {return product;}
}
