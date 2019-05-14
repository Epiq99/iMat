package detailedview;


import browseListItem.IBrowseListItemListener;
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
import javafx.scene.text.Text;
import javafx.util.converter.IntegerStringConverter;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ProductCategory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DetailedView extends AnchorPane {

    IMatDataHandler handler = IMatDataHandler.getInstance();

    private static final Image addImage = new Image("images/add.png");
    private static final Image minusImageRes = new Image("images/minus.png");
    private static final Image favoriteFullImage = new Image("images/favorite_full.png");
    private static final Image favoriteEmptyImage = new Image("images/favorite_empty.png");

    public static final List<IDetailedViewListener> listeners = new ArrayList<>();

    private Product product;

    @FXML Label priceLabel;
    @FXML Label unitLabel;
    @FXML Label titleLabel;
    @FXML ImageView productImageView;
    @FXML ImageView ecoImage;
    @FXML ImageView favoriteImage;
    @FXML TextField amountField;
    @FXML Button addButton;
    @FXML ImageView plusImage;
    @FXML ImageView minusImage;
    @FXML Label favoriteLable;
    public DetailedView(Product prod) {

        product = prod;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("detailedview.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        titleLabel.setText(product.getName());
        priceLabel.setText(String.valueOf(product.getPrice()));
        unitLabel.setText(product.getUnit());
        productImageView.setImage(handler.getFXImage(product));
        ecoImage.setVisible(product.isEcological());
        favoriteImage.setImage(handler.isFavorite(product)?favoriteFullImage:favoriteEmptyImage);

        plusImage.setImage(addImage);
        minusImage.setImage(minusImageRes);

        amountField.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(), 1));

        addButton.addEventHandler(ActionEvent.ACTION, (event)->
                addToCart()
        );

        //handler.getShoppingCart().addProduct(product, Double.parseDouble(amountField.getText()));

        plusImage.addEventHandler(MouseEvent.MOUSE_CLICKED, event->
                amountField.setText(String.valueOf(Integer.parseInt(amountField.getText()) + 1))
        );

        minusImage.addEventHandler(MouseEvent.MOUSE_CLICKED, event->
                amountField.setText(String.valueOf(Integer.parseInt(amountField.getText()) - 1))
        );

        favoriteLable.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> onFavoriteClick());
        favoriteImage.addEventHandler(MouseEvent.MOUSE_CLICKED, event->onFavoriteClick());
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

    private void addToCart(){
        handler.getShoppingCart().addProduct(product, Double.parseDouble(amountField.getText()));
        notifyOnCartAdd();
    }

    public static void addListener(IDetailedViewListener listener){
        listeners.add(listener);
    }

    public void notifyOnCartAdd(){
        for(IDetailedViewListener l: listeners)
            l.addToCartNotification(this);
    }

    public Product getProduct() {return product;}
}
