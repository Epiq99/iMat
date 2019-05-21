package paymentWizard.reciptItem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;

public class ReciptItem extends AnchorPane {

    @FXML Label itemLabel, amountLabel, priceLabel;

    public ReciptItem(String itemName, int amount, double totalprice) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("reciptItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (
                IOException exception) {
            throw new RuntimeException(exception);
        }

        itemLabel.setText(itemName);
        amountLabel.setText(String.valueOf(amount));
        priceLabel.setText(String.valueOf(totalprice));
    }
}
