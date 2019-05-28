package feature;


import detailedview.IDetailedViewListener;
import foodcategorylistitem.FoodCategoryListItem;
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

public class Feature extends AnchorPane {

    IMatDataHandler handler = IMatDataHandler.getInstance();

    private static final Image featureImage = new Image("images/feature.png");

    @FXML AnchorPane exploreButton;
    @FXML ImageView featureImageView;

    private FoodCategoryListItem category;

    public Feature(FoodCategoryListItem item) {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("feature.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        category = item;
        featureImageView.setImage(featureImage);
        exploreButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> category.onClick());
    }
}
