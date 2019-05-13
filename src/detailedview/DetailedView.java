package detailedview;


import browseListItem.IBrowseListItemListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
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
    private static final Image getFavoriteEmptyImage = new Image("images/favorite_empty.png");

    public static final List<IBrowseListItemListener> listeners = new ArrayList<>();

    private Product product;

    @FXML Label priceLabel;
    @FXML Label unitLabel;
    @FXML Label titleLabel;
    @FXML ImageView productImageView;

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
    }
}
