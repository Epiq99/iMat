package customerPage.receiptHistoryPage;


import customerPage.SettingsPane;
import customerPage.receiptHistoryPage.receiptListItem.ReceiptListItem;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.Customer;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Order;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;

public class ReceiptHistoryPage extends SettingsPane {

    IMatDataHandler handler = IMatDataHandler.getInstance();
    private static ReceiptHistoryPage self;
    private static int id = 0;

    @FXML FlowPane receiptLsit;

    private ReceiptHistoryPage() {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("receipthistorypage.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }


        update();
    }

    @Override
    public void update(){
            receiptLsit.getChildren().clear();
            for(Order o: handler.getOrders())
                receiptLsit.getChildren().add(new ReceiptListItem(o));
    }

    public static ReceiptHistoryPage getInstance(){
        if(self == null)
            self= new ReceiptHistoryPage();

        return self;
    }
}
