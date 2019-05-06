package browseListItem;


import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.*;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class BrowseListItem extends AnchorPane {

    private final String imageFolderPath = "images/";

    @FXML Label itemNameLable;
    @FXML Label priceLable;
    @FXML Label unitLable;
    @FXML ImageView itemImage;

    public BrowseListItem(Product prod){

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
        itemImage.setImage(new Image(imageFolderPath + prod.getImageName()));
    }
}
