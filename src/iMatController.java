import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import se.chalmers.cse.dat216.project.*;
import browseListItem.BrowseListItem;

import java.net.URL;
import java.util.ResourceBundle;

public class iMatController implements Initializable {
    IMatDataHandler handler = IMatDataHandler.getInstance();

    @FXML TilePane browserPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        browserPane.setVgap(30);
        browserPane.setHgap(30);

        for(Product p: handler.getProducts())
            browserPane.getChildren().add(new BrowseListItem(p));
    }

    public void test() {

    }
}
