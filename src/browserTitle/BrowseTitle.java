package browserTitle;


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
import javafx.util.converter.IntegerStringConverter;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BrowseTitle extends AnchorPane {

    IMatDataHandler handler = IMatDataHandler.getInstance();

    private static final Image addImage = new Image("images/add.png");
    private static final Image minusImageRes = new Image("images/minus.png");
    private static final Image favoriteFullImage = new Image("images/favorite_full.png");
    private static final Image getFavoriteEmptyImage = new Image("images/favorite_empty.png");

    public static final List<IBrowseListItemListener> listeners = new ArrayList<>();

    private Product product;

    @FXML Label titleLable;

    public BrowseTitle(String title) {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("browseTitle.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        titleLable.setText(title);

    }
}
